/**
 * This class represents a players profile within the game
 * @author Alex Wills
 * @version 1.0
 */
public class Profile {
	private String name;
	private int gamesPlayed;
	private int wins;
	
	/**
	 * 
	 * @param name The name associated with the profile.
	 */
	public Profile (String name) {
		this.name = name;
		this.gamesPlayed = 0;
		this.wins = 0;
	}

	public Profile (String name, int gamesPlayed, int wins) {
		this.name = name;
		this.gamesPlayed = gamesPlayed;
		this.wins = wins;
	}
	
	/**
	 * Calculates the ratio of wins to losses.
	 * @return The win to loss ratio.
	 */
	private double calculateWinLoss() {
		return wins/(gamesPlayed-wins);
	}
	
	/**
	 * Increments number of wins by 1
	 */
	public void incrementWins() {
		wins += 1;
	}
	
	/**
	 * Increments number of games played by 1
	 */
	public void incrementGamesPlayed() {
		gamesPlayed += 1;
	}
	
	/**
	 * @return name associated with the profile.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The number of games this profile has played.
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	
	/**
	 * @return The number games this profile has won.
	 */
	public int getWins() {
		return wins;
	}
	
	/**
	 * @return The number games this profile has lost.
	 */
	public int getLosses() {
		return gamesPlayed - wins;
	}
	
	/**
	 * @return The win to loss ratio.
	 */
	public double getWinLossRatio() {
		return calculateWinLoss();
	}

}
