import java.util.Hashtable;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

public class Formatter {

	public static Hashtable<String, Player> getDataFromCSV(String fileName) throws FileNotFoundException {
		File readFile = new File(fileName);
		Scanner scan = new Scanner(readFile);
		String line = scan.nextLine();

		Hashtable<String, Player> playerList = new Hashtable<>();

		// Skips header
		if (line.substring(0, "Timestamp".length()).equals("Timestamp")) {
			line = scan.nextLine();
		}

		while (scan.hasNextLine()) {
			// Timestamp, p1, score1, p2, score2, optional comment
			ArrayList<String> matchData = new ArrayList<String>();

			String lineData = "";
			for (int i = 0; i < line.length(); i++) {
				// if comma is detected, add previous data to ArrayList
				// commas in double quotes are ignored
				if (line.charAt(i) == ',' && !commaInDoubleQuotes(i, line)) {
					matchData.add(lineData);
					lineData = "";
				} else if (line.charAt(i) != '"') {
					lineData += line.charAt(i);
				}
			}

			// in order of CSV
			String p1 = matchData.get(1);
			int score1 = Integer.parseInt(matchData.get(2));
			String p2 = matchData.get(3);
			int score2 = Integer.parseInt(matchData.get(4));

			// if Player 1 doesn't exist, create them
			if (!playerList.containsKey(p1)) {
				playerList.put(p1, new Player(p1));
			}

			// if Player 2 doesn't exist, create them
			if (!playerList.containsKey(p2)) {
				playerList.put(p2, new Player(p2));
			}

			// Add the scores to each player's matchesPlayed
			playerList.get(p1).addMatch(p2, score1, score2);
			playerList.get(p2).addMatch(p1, score2, score1);

			line = scan.nextLine();
		}
		scan.close();
		return playerList;
	}

	/**
	 * Writes formatted CSV data to outputFileName.
	 * 
	 * @param playerList non-empty playerList created by getDataFromCSV()
	 */
	public static boolean writeFormattedData(Hashtable<String, Player> playerList, String outputFileName) {
		try {
			
			// Check if outputFileName already exists
			File outputFile = new File(outputFileName);
			if (outputFile.createNewFile()) {
				System.out.println("Created " + outputFileName);
			} else {
				System.out.println(outputFileName + " already exists.");
				System.out.print("Would you like to overwrite this file? y/n: ");
				
				Scanner scan = new Scanner(System.in);
				String overwrite = scan.nextLine();
				scan.close();
				System.out.println("");
				
				if(overwrite.toLowerCase().charAt(0) != 'y') {
					return false;
				}
			}
			
			FileWriter writer = new FileWriter(outputFileName);

			// Write formatted data for each Player in playerList to outputFileName
			playerList.forEach((k, v) -> {
				try {
					System.out.print(v.getMatchCSV() + "\n");
					writer.write(v.getMatchCSV() + "\n");
				} catch (IOException e) {
					System.out.println("An error occurred writing to " + outputFileName + ".");
					e.printStackTrace();
				}
			});
			System.out.println("");
			writer.close();

		} catch (IOException e) {
			System.out.println("An error occurred writing to " + outputFileName + ".");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean commaInDoubleQuotes(int indexOfComma, String line) {
		int leftQuotes = 0;

		for (int i = 0; i < indexOfComma; i++) {
			if (line.charAt(i) == '"') {
				leftQuotes++;
			}
		}

		// returns true if total commas left of char is odd
		return (leftQuotes % 2 == 1) ? true : false;
	}
}
