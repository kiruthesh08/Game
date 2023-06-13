import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author kiru9
 * @version 1.8
 */
public class LeaderBoard {

    private int gamesPlayed;
    private Set<Profile> playerProfiles;
    private int level;

    public LeaderBoard(int gamesPlayed, Set<Profile> playerProfiles, int level) {
        this.gamesPlayed = gamesPlayed;
        this.playerProfiles = playerProfiles;
        this.level = level;
    }

    public Set<Profile> getPlayerProfiles() {
        return this.playerProfiles;
    }

    public List<String> getPlayerNames() {
        List<String> names = new ArrayList<>();
        for (Profile profile : this.getPlayerProfiles()) {
            names.add(profile.getName());
        }
        Collections.sort(names);
        return names;
    }

    public void add(Profile p) {
        this.playerProfiles.add(p);
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getLevel() {
        return this.getLevel();
    }
}
