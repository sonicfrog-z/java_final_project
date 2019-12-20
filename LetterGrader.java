import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class LetterGrader {
    private final String outputPath;
    private final String inputPath;
    private ArrayList<String[]> content;
    private ArrayList<String[]> letterGrade;

    LetterGrader(String input, String output) {
        this.inputPath = input;
        this.outputPath = output;
        this.content = new ArrayList<>();
        this.letterGrade = new ArrayList<>();
    }

    public static void main(String[] args) {
        LetterGrader lg = new LetterGrader("input.txt", "output.txt");
        lg.readScore();
        for (String[] arr : lg.content) {
            for (String str : arr) {
                System.out.print('+');
                System.out.print(str);
            }
            System.out.println();
        }
        lg.calcLetterGrade();
        for (String[] arr : lg.letterGrade) {
            for (String str : arr) {
                System.out.print(str);
                System.out.print('=');
            }
            System.out.println();
        }
//        lg.printGrade();

        lg.displayAverages();

    }

    //reads	score	and	stores	the	data	in	member	variables
    void readScore() {
        File filename = new File(inputPath);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] sArray = line.split(",");
                for (int i = 0; i < sArray.length; i++) {
                    sArray[i] = sArray[i].trim();
                }
                content.add(sArray);
            }
        } catch (IOException e) {
            System.out.println("Input file not found.");
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //determines	letter	grade	and	stores	information
    void calcLetterGrade() {
        for (String[] sArray : content) {
            ArrayList<Integer> scores = new ArrayList<>();
            for (int i = 1; i < sArray.length; i++) {
                try {
                    scores.add(Integer.parseInt(sArray[i]));

                } catch (NumberFormatException e) {
                    System.out.println("There is score in file with wrong format.");
                    System.exit(1);
//                    e.printStackTrace();
                }
            }
            double finalScore = 0;
            try {
                finalScore = (scores.get(0) + scores.get(1) + scores.get(2) + scores.get(3)) * 0.10 +
                        scores.get(4) * 0.20 + scores.get(5) * 0.15 + scores.get(6) * 0.25;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("There is student lack of score, please check you input file.");
                e.printStackTrace();
                System.exit(1);
            }
            String grade = null;
            if (finalScore >= 90) {
                grade = "A";
            } else if (finalScore >= 80 && finalScore < 90) {
                grade = "B";
            } else if (finalScore >= 70 && finalScore < 80) {
                grade = "C";
            } else if (finalScore >= 60 && finalScore < 70) {
                grade = "D";
            } else if (finalScore < 60) {
                grade = "F";
            }
            letterGrade.add(new String[]{sArray[0], grade});
        }
        Comparator<String[]> sArrayComparator = new Comparator<>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o1[0].compareTo(o2[0]);
            }
        };
        letterGrade.sort(sArrayComparator);
    }

    //writes	the	grade	in	output	file
    void printGrade() {
        if (letterGrade.size() == 0) {
            return;
        }
        BufferedWriter bw = null;
        try {
            File writePath = new File(outputPath);
            writePath.createNewFile();
            bw = new BufferedWriter(new FileWriter(writePath));
            String firstLine = String.format("Letter grade for %d students given in %s file is:\n\n",
                    letterGrade.size(), inputPath);
            bw.write(firstLine);
            for (String[] sArray : letterGrade) {
                String newLn = String.format("%-30s%s", sArray[0] + ':', sArray[1]);
                bw.write(newLn);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.printf("Letter grade has been calculated for students listed in input file " +
                "input_data.txt and written to output file %s\n\n", outputPath);
    }

    //displays	the	averages	in	console
    void displayAverages() {
        float[] average = new float[7];
        int[] min = new int[7];
        for (int i = 0; i < 7; i++) {
            min[i] = Integer.MAX_VALUE;
        }
        int[] max = new int[7];
        for (int i = 0; i < 7; i++) {
            max[i] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < 7; i++) {
            int sum = 0;
            for (String[] sArray : content) {
                int score = Integer.parseInt(sArray[i + 1]);
                sum += score;
                min[i] = Math.min(min[i], score);
                max[i] = Math.max(max[i], score);
            }
            average[i] = sum / content.size();
        }
        System.out.println("Here is the class averages:\n");
        System.out.println("            Q1     Q2     Q3     Q4     MidI   MidII  Final");
        System.out.printf("Average:    %-7.2f%-7.2f%-7.2f%-7.2f%-7.2f%-7.2f%-7.2f\n",
                average[0], average[1], average[2], average[3], average[4], average[5], average[6]);
        System.out.printf("Minimum:    %-7d%-7d%-7d%-7d%-7d%-7d%-7d\n",
                min[0], min[1], min[2], min[3], min[4], min[5], min[6]);
        System.out.printf("Maximum:    %-7d%-7d%-7d%-7d%-7d%-7d%-7d\n",
                max[0], max[1], max[2], max[3], max[4], max[5], max[6]);
    }

    //All streams are properly closed in above methods, no need to do extra cleanup.
    void doCleanup() {
    }
}
