import java.io.File;
import java.io.FileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

//FILE CURRENTLY NEEDS ACTION TILE CLASS AND BOARD CLASS READY

/** A class to handle save file saving/loading and level file loading
 * @author  Marcus Berryman 1910719
 * @version  1.0
 * */
public class SaveDataManager {

	private static int currLine;
	private static int x;
	private static int y;
	private static int noPlayers;
	private static Queue<Player> players = new LinkedList<>();
	private static Board board;
	private static Bag bag;
	private static LeaderBoard leaderBoard;

	/**
	 * 	 * Writes a game's data to a text file
	 * 	 *
	 * 	 * @param x         How tall the game board is (in tiles)
	 * 	 * @param y         How wide the game board is (in tiles)
	 * 	 * @param noPlayers How many players are in this save
	 * 	 * @param players   The data for each player, including their backtrack position, action tiles etc
	 * 	 * @param board     The data for the board, containing all the game's tiles
	 * 	 * @param saveName  The name of the new save file excluding .sav
	 */

	/*None of this works ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
	Just here for maybe some good boy points

	public static void saveGame(int x, int y, int noPlayers, Queue<Player> players, Board board, Bag bag, String saveName) { //string arrays will be replaced with arrays for Player classes and Tile classes

		try {
			FileWriter writer = new FileWriter(saveName + ".sav"); //add way for different saves to have different file names
			writer.write(x + "x" + y + "\n");//writes board dimensions
			writer.write("(" + players.size() + ")\n");//writes no of players
			for (int i = 0; i < players.size(); i++) {
				Player currPlayer = players.poll();
				int iPlus = i+1;
				writer.write(iPlus + "," + currPlayer.getProfile().getName() + ",(" + currPlayer.getPos()[0] + "," + currPlayer.getPos()[1] + "),("
						+ currPlayer.getPrevPos()[0] + "," + currPlayer.getPrevPos()[1] + ")," + currPlayer.getCanBeBacktracked() + "," + "\n");
			}//iterates through each player and writes their data on a new line
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					writer.write(board[i][j]);
				}
			}//iterates through the board and writes the data for each tile on a new line, may need to add bag clause
			writer.close();
		} catch (IOException e) {
			System.out.println("Cannot create file");
			e.printStackTrace();
		}
	}*/

	/**
	 * Loads a games data from a save or level file
	 *
	 * @param loadName The name of the save file being loaded excluding .sav (may need to be changed to a file object)
	 * @return returns a Game object and Board object
	 */

	public static Game loadGame(String loadName) throws FileNotFoundException {
		LeaderBoard leaderBoard = null;
		leaderBoard = loadLeaderBoard("LeaderBoard.sav");
		Scanner reader = makeScanner(loadName);
		/* STRUCTURE OUTLINE (Yes it is mad confusing sorry lmao)
		3 Stages: The IF, the ELSE IF and the ELSE
		IF: Reads the first line and takes the board dimensions from it, then loops to the start of the while and reads next line from there
		ELSE IF: Reads the Number of Players in the brackets and stores it, then loops to the start of the while and reads the next line from there
		ELSE: Almost separate as this is the last stage, and the while will not loop after, so the next lines in this are self contained
			  Reads data for the board up to the correct number of tiles based on the dimensions,
					then creates a board ready to go (must not contain action tiles or a Sprite not found /Sprites/Null will be thrown)
			  Then reads the rest of the tiles for the bag (can include action tiles here) and produces a bag object ready to go
		 */
		while (reader.hasNextLine()) {
			String data = reader.nextLine();
			if (currLine == 0) {
				loadCoords(data);
				FloorTile[][] tiles = new FloorTile[x][y];
				board = new Board(tiles);
				currLine++; //reads the board coordinates and creates an empty board, loops back to while
			} else if (currLine == 1) {
				loadNoPlayers(data);
				currLine++;//loads the player count and stores it, loops back to while
			} else {
				for (int i = 0; i < noPlayers; i++) {
					loadPlayer(data, leaderBoard);
					data = reader.nextLine();//loops for the number of players, creating a player for each
				}
				int tileIndex = 0;//used to track the index of the tile we're on
				boolean isFixed;
				for (int i = 0; i < x; i++) {
					for (int j = 0; j < y; j++) {
						if ((tileIndex % 2 == 0) && ((tileIndex / x) % 2 == 0)) {
							isFixed = true;
						} else {
							isFixed = false;
						}//calculates if a tile at a given index will be fixed or movable a that time
						board.setTile(i, j, loadTileToBoard(data, isFixed));//creates a tile and loads it to the board
						data = reader.nextLine();
						tileIndex++;
					}
				}
				while (reader.hasNextLine()) { //after the board has been filled, the rest of the tiles in the save file go to the bag
					loadBag(data);
					data = reader.nextLine();
				}//bag acquired lmao
			}
		}
		reader.close();

		return new Game(players, board, bag, leaderBoard); //returns a ready to use player array, bag, board and leaderboard
	}

	private static void loadCoords(String data) {
		x = Integer.parseInt(Character.toString(data.charAt(0)));
		y = Integer.parseInt(Character.toString(data.charAt(2)));
	}

	private static void loadNoPlayers(String data) {
		noPlayers = Integer.parseInt(Character.toString(data.charAt(1)));
	}

	//
	private static void loadPlayer(String data, LeaderBoard leaderBoard) {
		String splitData[] = data.split(",");
		int[] pos = {charToInt(splitData[2].charAt(1)), charToInt(splitData[3].charAt(0))};
		int[] prevPos = {charToInt(splitData[4].charAt(1)), charToInt(splitData[5].charAt(0))};
		//this position is at the end of the data string and counts from the end (since the no of action tiles can vary). This was to stop bugs in other areas
		int[] prevPrevPos = {charToInt(data.charAt((data.length()-4))), charToInt(data.charAt((data.length()-2)))};
		Profile profile = null;
		for (Profile p : leaderBoard.getPlayerProfiles()) {
			if (p.getName().equals(splitData[1])) {
				profile = p;
			}
		}

		//add way to set if it's this players turn or not
		ArrayList<ActionTile> actionTiles = new ArrayList<>();
		if (Integer.parseInt(splitData[7]) > 0) {
			for (int i = 8; i < 8 + Integer.parseInt(splitData[7]); i++) {
				ActionTile tile = new ActionTile(Integer.parseInt(splitData[i]));
				actionTiles.add(tile);
			}
		}//adds action tiles to hand
		Player player = new Player(pos, prevPos, prevPrevPos, profile, actionTiles); //charAt functions are used to exclude the brackets from coords
		player.setCanBeBacktracked(Boolean.parseBoolean(splitData[6]));
		players.add(player);
	}

	private static FloorTile loadTileToBoard(String data, boolean isFixed) {
		String[] splitData = data.split(",");
		Set<Direction> directions = new HashSet<>();
		if (Boolean.parseBoolean(splitData[0]))//these add the direciton to the list of possible if the bool is true
			directions.add(Direction.NORTH);
		if (Boolean.parseBoolean(splitData[1]))
			directions.add(Direction.EAST);
		if (Boolean.parseBoolean(splitData[2]))
			directions.add(Direction.SOUTH);
		if (Boolean.parseBoolean(splitData[3]))
			directions.add(Direction.WEST);
		FloorTile tile = new FloorTile(directions, isFixed);
		tile.setNumTurnsTilUnfrozen(Integer.parseInt(splitData[4]));
		tile.setNumTurnsTilExtinguished(Integer.parseInt(splitData[5]));
		return tile;
	}

	public static Bag loadBag(String data) {
		String[] splitData = data.split(",");
		bag = new Bag();
		int actionType = Integer.parseInt(splitData[6]);
		if (actionType == 0) { //checks the action type of each tile, if its an action tile (ie not 0) then it adds an action tile, if not, adds a board tile
			String floorTileData = String.join(",", splitData);
			bag.returnTile(loadTileToBoard(floorTileData, false));
		} else {
			ActionTile actionTile = new ActionTile(actionType);
			bag.returnTile(actionTile);
		}
		return bag;
	}

	public static LeaderBoard loadLeaderBoard(String loadName) throws FileNotFoundException, NumberFormatException {
		int gamesPlayed = 0;
		Set<Profile> playerProfiles = new HashSet<>();
		int level = 0;

		Scanner reader = makeScanner(loadName);
		int lineNum = 0;
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			Scanner lineReader = new Scanner(line);
			if (lineNum == 0) {
				if (lineReader.hasNext()) {
					level = Integer.parseInt(lineReader.next());
				}
			} else if (lineNum == 1) {
				if (lineReader.hasNext()) {
					gamesPlayed = Integer.parseInt(lineReader.next());
				}
			} else {
				playerProfiles.add(loadProfile(line));
			}
			lineNum++;
		}
		return new LeaderBoard(gamesPlayed, playerProfiles, level);
	}

	private static Profile loadProfile(String line) {
		String name = null;
		int gamesPlayed = 0;
		int wins = 0;

		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter(",");
		int x = 0;
		while (lineReader.hasNext()) {
			String data = lineReader.next();
			if (x == 0) {
				name = data;
			} else if (x == 1) {
				gamesPlayed = Integer.parseInt(data);
			} else if (x == 2) {
				wins = Integer.parseInt(data);
			}
			x++;
		}
		return new Profile(name);
	}

	private static Scanner makeScanner(String loadName) throws FileNotFoundException {
		File file = new File(loadName);
		Scanner reader = new Scanner(file);
		return reader;
	}

	private static int charToInt(char a) {
		return Integer.parseInt(Character.toString(a));
	}

	private static Direction stringToDirection(String srtDir, Direction directionNeeded) {
		if (Boolean.parseBoolean(srtDir)) {
			return directionNeeded;
		} else {
			return null;
		}
	}

	public static void saveLeaderboard(LeaderBoard leaderboard) {
		FileWriter writer = null;
		try {
			writer = new FileWriter("leaderboard.txt");
			writer.write(leaderboard.getLevel());
			writer.write(leaderBoard.getGamesPlayed());
			for (Profile p : leaderboard.getPlayerProfiles()) {
				writer.write(String.join(
						",",
						p.getName(),
						Integer.toString(p.getWins()),
						Integer.toString(p.getGamesPlayed())
				));
			}
			writer.close();

		} catch (IOException e) {
			System.out.println("Couldn't save leaderboard.");
			e.printStackTrace();
		}
	}


}



