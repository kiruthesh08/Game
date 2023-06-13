/**
 * A class to represent action tiles and their usage in the game.
 * @author Elliott Lucas
 * @version 1.0
 */
public class ActionTile extends Tile {
	private static Game game;
	private int tileType;

	public ActionTile(int tileType) {
		this.tileType = tileType;
	}
	
	/**
	 * Sets certain conditions on depending on the tile type.
	 * @param x The x coordinate to place the tile on.
	 * @param y The y coordinate to place the tile on.
	 */
	public void playActionTile(int x, int y) {
		switch(tileType) {
			case 0: // Ice Tile
				for (int i = x-1; i <= x+1; i++) {
					for (int j = y-1; j <= y+1; j++) {
						game.getBoard().getTile(i, j).setNumTurnsTilUnfrozen(game.getNumPlayers());
					}
				}
				// Loop between x-1 and x+1
					// Loop between y-1 and x+1
						// On each tile, set numTurnsTilUnfrozen to 1 (1 turn of the player who used it)
				break;
			case 1: // Fire Tile
				for (int i = x-1; i <= x+1; i++) {
					for (int j = y - 1; j <= y + 1; j++) {
						game.getBoard().getTile(i, j).setNumTurnsTilExtinguished(game.getNumPlayers()*2);
					}
				}
				// Loop between x-1 and x+1
					// Loop between y-1 and x+1
						// On each tile, set numTurnsTilExtinguished to 2 (2 turns of the player who used it)
				break;
			case 2: // Double Move Tile
				// Move player to front of player queue
				game.givePlayerAnotherMove();
				break;
			case 3: // Backtrack Tile
				// Choose Player
				// Get tile to backtrack to
				// If no one already on tile
					// Move to tile
				break;
		}
	}
	
	/**
	 * Passes the game object to the ActionTile.
	 * @param givenGame The game object.
	 */
	public void setGame(Game givenGame) {
		game = givenGame;
	}
}
