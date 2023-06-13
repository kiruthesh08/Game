import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * An enum that represents the four cardinal directions which is used to determine what directions can be moved on a tile.
 * @author Glen Baker
 * @version 1.0
 */
public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Set<Direction> CROSSROADS = new HashSet<>(Arrays.asList(NORTH, EAST, SOUTH, WEST));

    public static Set<Direction> STRAIGHT = new HashSet<>(Arrays.asList(EAST, WEST));

    public static Set<Direction> CORNER = new HashSet<>(Arrays.asList(SOUTH, WEST));

    public static Set<Direction> TSHAPE = new HashSet<>(Arrays.asList(EAST, SOUTH, WEST));
	
	/**
	 * Gets the next direction when rotating clockwise from the current direction.
	 * @return The next direction when rotating clockwise.
	 */
    public Direction next() {
        int index = this.ordinal();
        if (index == 3) {
            return NORTH;
        } else {
            return Direction.values()[index+1];
        }
    }
	
	/**
	 * Rotates a set of directions clockwise 90 degrees.
	 * @return The rotated set of directions.
	 */
    public static Set<Direction> rotate(Set<Direction> tileDirections) {
        Set<Direction> result = new HashSet<>();
        for (Direction d : tileDirections) {
            result.add(d.next());
        }
        return result;
    }
}
