import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A class representing the game board.
 * @author Glen Baker, Alex Wills
 * @version 1.0
 */
public class Board {
    private FloorTile[][] board;

    public Board(FloorTile[][] board) {
        this.board = board;
    }

    public void movePlayer(Player player, int x, int y) {

    }

    /**
     * Slides the tiles in a row to the right by one.
     * @param rowNum The number/y coordinate of the row to slide.
     */
    public void slideRowRight(int rowNum) {
        FloorTile pushedOffTile = board[getWidth()-1][rowNum];
        for (int i = getWidth()-2; i >= 0; i--) {
            // Set tile to the right equal to this one
            board[i+1][rowNum] = board[i][rowNum];
        }
        board[0][rowNum] = pushedOffTile;
    }

    /**
     * Slides the tiles in a row to the left by one.
     * @param rowNum The number/y coordinate of the row to slide.
     */
    public void slideRowLeft(int rowNum) {
        FloorTile pushedOffTile = board[0][rowNum];
        for (int i = 0; i < getWidth()-1; i++) {
            board[i][rowNum] = board[i+1][rowNum];
        }
        board[getWidth()-1][rowNum] = pushedOffTile;
    }

    /**
     * Slides the tiles in a column down by one.
     * @param columnNum The number/x coordinate of the column to slide.
     */
    public void slideColumnDown(int columnNum) {
        FloorTile pushedOffTile = board[columnNum][getHeight()-1];
        for (int i = getHeight()-2; i >= 0; i--) {
            board[columnNum][i+1] = board[columnNum][i];
        }
        board[columnNum][0] = pushedOffTile;
    }

    /**
     * Slides the tiles in a column up by one.
     * @param columnNum The number/x coordinate of the column to slide.
     */
    public void slideColumnUp(int columnNum) {
        FloorTile pushedOffTile = board[columnNum][0];
        for (int i = 0; i < getHeight()-1; i++) {
            board[columnNum][i] = board[columnNum][i+1];
        }
        board[columnNum][getHeight()-1] = pushedOffTile;
    }

    /**
     * Checks if the tiles in a row can be moved.
     * @param rowNum The number/y coordinate of the row to slide.
     * @return True if the row can be moved, false if not.
     */
    public boolean rowIsMovable(int rowNum) {
        boolean result = true;
        for (int i = 0; i < getWidth(); i++) {
            if (board[i][rowNum].isFixed()) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Checks if the tiles in a column can be moved.
     * @param columnNum The number/x coordinate of the column to slide.
     * @return True if the column can be moved, false if not.
     */
    public boolean ColumnIsMovable(int columnNum) {
        boolean result = true;
        for (int i = 0; i < getHeight(); i++) {
            if (board[columnNum][i].isFixed()) {
                result = false;
            }
        }
        return result;
    }

    /**
     *
     */
    public void updateTiles() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                board[i][j].update();
            }
        }
    }

    /**
     * Gets a tile at given coordinates.
     * @param x x coordinate of the the tile.
     * @param y y coordinate of the the tile.
     * @return A tile with coordinates (x, y).
     */
    public FloorTile getTile(int x, int y) {
        return board[x][y];
    }

    /**
     * Sets a given tile to the given coordinates
     * @param x x coordinate of the the tile
     * @param y y coordinate of the the tile
     * @param tile The tile to be added to the given position
     */
    public void setTile(int x, int y, FloorTile tile) {
        board[x][y] = tile;
    }

    /**
     * Gets the height of the board.
     * @return The height of the board.
     */
    public int getHeight() {
        return this.board[0].length;
    }
	
	/**
     * Gets the width of the board.
     * @return The width of the board.
     */
	public int getWidth() {
		return this.board.length;
	}

    /**
     * Checks if there is a player at the given coordinates.
     * @param x the x coordinate to check.
     * @param y the y coordinate to check.
     * @return True if there is a player on the coordinates, false if not.
     */
    public boolean checkForPlayer(int x, int y, ArrayList<Player> players) {
        boolean result = false;
        //for every player in the players queue, returns true if the player is on the tile
		// false if they are not
        for(Player player : players) {
	        if (player.getPos() == new int[] {x, y}) {
                result = true;
            }
        }
        return result;
    }
}
