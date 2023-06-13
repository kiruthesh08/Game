import java.util.*;

/**
 * A class representing the bag that holds all tiles that aren't on the board.
 * @author Elliott Lucas
 * @version 1.0
 */
public class Bag {
	private List<Tile> contents = new ArrayList<Tile>();

	/**
	 * Generates the contents of the bag.
	 * @param amount The number of each tile to be added to the bag.
	 */
	public void generate(int amount) {
		contents.add(new GoalTile());
		for (int i = 0; i < amount; i++) {
			contents.add(new FloorTile(Direction.STRAIGHT, false));
			contents.add(new FloorTile(Direction.CORNER, false));
			contents.add(new FloorTile(Direction.TSHAPE, false));
			contents.add(new FloorTile(new HashSet<Direction>(Arrays.asList(Direction.WEST, Direction.NORTH)), false));
			contents.add(new FloorTile(new HashSet<Direction>(Arrays.asList(Direction.SOUTH, Direction.NORTH)), false));

			for (int j = 0; j < 3; j++) {
				contents.add(new ActionTile(i));
			}
		}

		Collections.shuffle(contents);
	}

	/**
	 * Shuffles the order of tiles in the bag.
	 */
	public void shuffle() {
		Collections.shuffle(contents);
	}

	/**
	 * Mimics a player taking a tile from the bag.
	 * @return A tile from the bag.
	 */
	public Tile drawTile() throws IllegalStateException {
		return contents.remove(0);
	}
	
	/**
	 * Adds a tile to the bag.
	 * @param tile The tile to be added.
	 */
	public void returnTile(Tile tile) {
		contents.add(tile);
	}
	
	/**
	 * Draws the first FloorTile from the bag.
	 * @return The first FloorTile from the bag.
	 */
	public FloorTile drawFloorTile() throws IllegalStateException {
		for (Tile tile : contents) {
			if (tile instanceof FloorTile) {
				contents.remove(tile);
				return (FloorTile) tile;
			}
		}
		throw new IllegalStateException();
	}

	public boolean isEmpty() {
		return contents.isEmpty();
	}
}
