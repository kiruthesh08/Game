import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the people playing the game
 * @author Glen Baker
 * @version 1.0
 */
public class Player {

    private Profile profile;
    private int[] pos;
    private int[] prevPos;
    private int[] prevPrevPos;
    private boolean canBeBacktracked;
    private List<ActionTile> availableActionTiles;

    /**
     *
     * @param pos Player's position on the board
     * @param prevPos Player's position one turn ago
     * @param prevPrevPos Player's position two turns ago
     * @param profile The profile associated with this player
     * @param actionTiles The action tiles that this player is 'holding'
     */
    public Player(int[] pos, int[] prevPos, int[] prevPrevPos, Profile profile, List<ActionTile> actionTiles) {
        this.pos = pos;
        this.updatePrevPositions();
        this.profile = profile;
        this.availableActionTiles = actionTiles;
    }

    /**
     *
     * @param pos Player's position on the board
     * @param prevPos Player's position one turn ago
     * @param prevPrevPos Player's position two turns ago
     * @param profile The profile associated with this player
     */
    public Player(int[] pos, int[] prevPos, int[] prevPrevPos, Profile profile) {
        this.pos = pos;
        this.prevPos = prevPos;
        this.prevPrevPos = prevPrevPos;
    }

    /**
     * Moves the player to a certain position on the board
     * @param x The x coordinate of the new position
     * @param y The y coordinate of the new position
     */
    public void move(int x, int y) {
        this.pos[0] = x;
        this.pos[1] = y;
    }

    /**
     * Stops a player from being backtracked
     */
    public void disableBacktrack() {
        this.canBeBacktracked = false;
    }

    /**
     * Updates a player's positions after moving
     */
    private void updatePrevPositions() {
        this.prevPrevPos = this.prevPos;
        this.prevPos = this.pos;
    }

    /**
     * @param setting True or false depending on if player can be backtracked
     */
    public void setCanBeBacktracked(boolean setting) {
        this.canBeBacktracked = setting;
    }

    /**
     * @return Player's current position
     */
    public int[] getPos() {
        return this.pos;
    }

    /**
     * @return Player's position one turn ago
     */
    public int[] getPrevPos() {
        return this.prevPos;
    }

    /**
     * @return Player's position two turns ago
     */
    public int[] getPrevPrevPos() {
        return this.prevPrevPos;
    }
	
	public void addToHand(ActionTile tile) {
		this.availableActionTiles.add(tile);
	}

	public Profile getProfile(){
        return profile;
    }

    public boolean getCanBeBacktracked(){
        return canBeBacktracked;
    }

}
