import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A class to handle the running of the game
 * @author Glen Baker
 * @version 1.0
 */
public class Game {
    private Player curPlayer;
    private Board board;
    private Bag bag;
    private Queue<Player> players;
    private LeaderBoard leaderBoard;

    public Game(Queue<Player> players, Board board, Bag bag, LeaderBoard leaderBoard) {
        this.players = players;
        this.board = board;
        this.bag = bag;
        this.leaderBoard = leaderBoard;
    }

    /**
     * Moves the game on to the next turn
     */
    public void nextTurn() {
        this.board.updateTiles();
        nextPlayer();
    }

    /**
     * Gives the next player in order their turn
     */
    public void nextPlayer() {
        this.players.add(this.curPlayer);
        this.curPlayer = this.players.poll();
    }

    /**
     * Gives a player another move
     */
    public void givePlayerAnotherMove() {
        for (int i = 0; i < this.players.size(); i++) {
            nextPlayer();
        }
    }

    /**
     * @return The game board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * @return The bag holding all tiles not on the board
     */
    public Bag getBag() {
        return this.bag;
    }
	
	public int getNumPlayers() {
		return players.size();
	}

	public Queue<Player> getPlayers() {
        return (this.players);
    }
}
