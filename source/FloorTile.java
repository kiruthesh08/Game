import java.util.Set;

/**
 * Class representing tiles on the board that can be moved by players.
 * @author Glen Baker, Elliott Lucas
 * @version 1.0
 */
public class FloorTile extends Tile {
	private boolean isFixed;
	private int numTurnsTilUnfrozen = 0;
	private int numTurnsTilExtinguished = 0;
	protected Set<Direction> directions;
	private Player playerOnTop;

	public FloorTile(Set<Direction> directions, boolean isFixed) {
		this.directions = directions;
		this.isFixed = isFixed;
	}

	/**
	 * Updates the duration of effects on tiles.
	 */
	public void update() {
		if (this.numTurnsTilUnfrozen > 0) {
			this.numTurnsTilUnfrozen -= 1;
		}
		if (this.numTurnsTilExtinguished > 0) {
			this.numTurnsTilExtinguished -= 1;
		}
	}
	
	/**
	 * Gets the directions of a tile.
	 * @return A list of the tile's directions.
	 */
	public Set<Direction> getDirections() {
		return this.directions;
	}

	/**
	 * Checks if a tile is frozen
	 * @return True if tile is frozen, false if not.
	 */
	public boolean isFrozen() {
		return (numTurnsTilUnfrozen > 0);
	}

	/**
	 * Checks if a tile is on fire
	 * @return True if the tile is on fire, false if not.
	 */
	public boolean isOnFire() {
		return (numTurnsTilExtinguished > 0);
	}

	/**
	 * Tells you if a tile can be moved
	 * @return True if the tile cannot be moved, false if not.
	 */
	public boolean isFixed() {
		return isFixed;
	}

	/**
	 * Gets the player that is on the tile
	 * @return The player on the tile.
	 */
	public Player getPlayerOnTop() {
		return playerOnTop;
	}

	/**
	 * Checks if there is a player on the tile
	 * @return True if there is a player on the tile, false if not.
	 */
	public boolean isPlayerOnTop() {
		return (getPlayerOnTop() == null);
	}

	/**
	 * Freezes a tile for a certain number of turns.
	 * @param num The number of turns until the tile will be unfrozen.
	 */
	public void setNumTurnsTilUnfrozen(int num) {
		this.numTurnsTilUnfrozen = num;
	}

	/**
	 * Sets a tile on fire for a certain number of turns.
	 * @param num The number of turns until the tile is no longer on fire.
	 */
	public void setNumTurnsTilExtinguished(int num) {
		this.numTurnsTilExtinguished = num;
	}
	
	/** 
	 * Sets whether or not a tile is fixed.
	 * @param value True or False depending on if the tile should be fixed or not.
	 */
	public void setFixed(boolean value) {
		this.isFixed = value;
	}
}
