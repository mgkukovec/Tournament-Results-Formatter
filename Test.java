import java.util.Hashtable;
import java.io.FileNotFoundException;

public class Test {

	public static void main(String[] args) {
		
		final String inputFileName = "Google_Sheet_Results.csv";
		final String outputFileName = "Formatted_Results.csv";
		
		Hashtable<String, Player> playerList = new Hashtable<>();
		
		System.out.println("Tournament Results Formatter");
		System.out.println("Mike Kukovec");
		System.out.println("mgkukovec@gmail.com");
		System.out.println("2021/01/27\n");
		
		try {
			playerList = Formatter.getDataFromCSV(inputFileName);
		} catch (FileNotFoundException e) {
			System.out.println(inputFileName + " not found.");
		}
		
		if(Formatter.writeFormattedData(playerList, outputFileName)) {
			System.out.println("Data written to " + outputFileName + " successfully.");
		}
		
	}
}
