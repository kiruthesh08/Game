import java.util.ArrayList;
import java.util.List;

/**
 * A subclass of FloorTile that represents the goal tile on the board.
 * @author Glen Baker
 * @version 1.0
 */
public class GoalTile extends FloorTile {
    public GoalTile() {
        super(Direction.CROSSROADS, true);
    }
}
