import com.sun.prism.shader.Texture_ImagePattern_AlphaTest_Loader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class to create the menus
 * @author Glen Baker, Lucas Kelly
 * @version 1.0
 */
public class Menu {

    private static LeaderBoard leaderBoard;

    //Choosing which player to play as
    @FXML
    private ChoiceBox player1ChoiceBox;
    @FXML
    private ChoiceBox player2ChoiceBox;
    @FXML
    private ChoiceBox player3ChoiceBox;
    @FXML
    private ChoiceBox player4ChoiceBox;
    @FXML
    private ChoiceBox levelChoiceBox;
    @FXML
    private Button startGameButton;
    @FXML
    private TextField addPlayerTextField;
    @FXML
    private Button addPlayerButton;
    @FXML
    private Label motdLabel;

    private ObservableList<String> getProfileChoices() {
        ObservableList<String> profileChoices = FXCollections.observableArrayList();
        for (String name : leaderBoard.getPlayerNames()) {
            profileChoices.add(name);
        }
        return profileChoices;
    }


    private ObservableList<String> levelChoices
            = FXCollections.observableArrayList("Level 1", "Level 2", "Level 3");

    public static void setLeaderBoard(LeaderBoard leaderBoard) {
        Menu.leaderBoard = leaderBoard;
    }

    @FXML
    public void initialize() {
        levelChoiceBox.setValue(levelChoices.get(0));
        levelChoiceBox.setItems(levelChoices);
        player1ChoiceBox.setValue(getProfileChoices().get(0));
        player1ChoiceBox.setItems(getProfileChoices());
        player2ChoiceBox.setValue(getProfileChoices().get(0));
        player2ChoiceBox.setItems(getProfileChoices());
        player3ChoiceBox.setValue(getProfileChoices().get(0));
        player3ChoiceBox.setItems(getProfileChoices());
        player4ChoiceBox.setValue(getProfileChoices().get(0));
        player4ChoiceBox.setItems(getProfileChoices());

        motdLabel.setText(MOTDFetcher.getMOTD());

        Alert noTextError = new Alert(Alert.AlertType.INFORMATION);
        noTextError.setTitle("ERROR");
        noTextError.setHeaderText(null);
        noTextError.setContentText("Please enter a name to add...");

        addPlayerButton.setOnAction(event -> {
            if (addPlayerTextField.getText().equals("")) {
                noTextError.showAndWait();
            } else {
                Menu.leaderBoard.add(new Profile(addPlayerTextField.getText()));
            }
        });
        /**
         * Changes the scene to the game scene
         */
        startGameButton.setOnAction(event -> {
            try {
                GameGUI.setGame(SaveDataManager.loadGame(levelChoiceBox.getValue().toString() + ".sav"));
            } catch (FileNotFoundException e) {
                System.out.println("Couldn't find level file.");
                e.printStackTrace();
                System.exit(1);
            }

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("GameLayout.fxml"));
            } catch (Exception e) {
                System.out.println();
                e.printStackTrace();
                System.exit(1);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
}
