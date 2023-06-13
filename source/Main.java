import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.FileNotFoundException;

/**
 * The point of entry into the program.
 * @author Glen Baker
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LeaderBoard leaderBoard = null;
        try {
            leaderBoard = SaveDataManager.loadLeaderBoard("LeaderBoard.sav");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to load leaderboard.");
            e.printStackTrace();
            System.exit(1);
        }
        Menu.setLeaderBoard(leaderBoard);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MenuLayout.fxml"));
        } catch (Exception e) {
            System.out.println("Failed to load menu.");
            e.printStackTrace();
            System.exit(1);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
