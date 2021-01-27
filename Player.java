import java.util.Hashtable;
import java.util.Set;

public class Player {

	private String playerName;
	private Hashtable<String, Match> matchesPlayed;

	/**
	 * @param playerName Player's full name
	 */
	public Player(String playerName) {
		this.playerName = playerName;
		this.matchesPlayed = new Hashtable<>();
	}

	public class Match {
		private String opponentName;
		private String playerGameWins;
		private String playerGameLosses;

		/**
		 * Stores individual match data
		 * 
		 * @param opponentName Opponent's full name
		 * @param playerGameWins Typically 0, 1, or 2
		 * @param opponentGameWins Typically 0, 1, or 2
		 */
		public Match(String opponentName, int playerGameWins, int playerGameLosses) {
			this.opponentName = opponentName;
			this.playerGameWins = String.valueOf(playerGameWins);
			this.playerGameLosses = String.valueOf(playerGameLosses);
		}

		/**
		 * @return a Match opponent's initials based on spaces
		 */
		public String getOpponentInitials() {
			// First letter of name
			String initials = Character.toString(opponentName.charAt(0));

			for (int i = 0; i < opponentName.length(); i++) {
				// Check for spaces, add next character if not ws or out of bounds
				if (opponentName.charAt(i) == ' ' && (i + 1) < opponentName.length() && opponentName.charAt(i + 1) != ' ') {
					initials += opponentName.charAt(i + 1);
				}
			}
			return initials.toUpperCase();
		}
	}

	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Format "playerName","1-2 JFK","2-0 FDR" ...
	 * 
	 * @return matches played as a CSV formatted String
	 */
	public String getMatchCSV() {

		String MatchCSV = "\"" + playerName + "\",";

		Set<String> keys = matchesPlayed.keySet();
		for (String k : keys) {
			MatchCSV += "\"" + matchesPlayed.get(k).playerGameWins + "-"
							 + matchesPlayed.get(k).playerGameLosses + " "
							 + matchesPlayed.get(k).getOpponentInitials() + "\",";
		}

		// remove trailing comma
		return MatchCSV.substring(0, MatchCSV.length() - 1);
	}

	public void addMatch(Match m) {
		matchesPlayed.put(m.opponentName, m);
	}

	public void addMatch(String opponentName, int playerGameWins, int opponentGameWins) {
		matchesPlayed.put(opponentName, new Match(opponentName, playerGameWins, opponentGameWins));
	}
}
