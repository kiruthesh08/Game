/**
 * An abstract class to represent a tile that can be stored in a bag.
 * @author Elliott Lucas
 * @version 1.0
 */
abstract class Tile {
	/**
	 * Gets the type of the tile (FloorTile, GoalTile or ActionTile).
	 * @return The type of the tile as a string.
	 */
	public String getTileType() {
		return this.getClass().getName();
	}
}