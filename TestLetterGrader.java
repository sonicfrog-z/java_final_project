public class TestLetterGrader {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Lack of arguments.");
            System.out.println("Usage: java TestLetterGrader inputFile outputFile");
            return;
        }
        LetterGrader letterGrader = new LetterGrader(args[0], args[1]);
        letterGrader.readScore();       //reads	score	and	stores	the	data	in	member	variables
        letterGrader.calcLetterGrade(); //determines	letter	grade	and	stores	information
        letterGrader.printGrade();  //writes	the	grade	in	output	file
        letterGrader.displayAverages(); //displays	the	averages	in	console
        letterGrader.doCleanup();   //use	it	to	close	files	and	other	resources
    }
}
