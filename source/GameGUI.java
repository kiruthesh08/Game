import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.util.Set;

/**
 * A class to create the user interface
 * @author Glen Baker
 * @version 1.0
 */
public class GameGUI {
    private static Game game;

    // Links to an id in the FXML
    @FXML
    public Pane centerPane;

    public static GridPane drawBoard() {
        GridPane boardPane = new GridPane();
        Board board = game.getBoard();
        int width = board.getWidth();
        int height = board.getHeight();

        for (int i = 0; i < width+2; i++) {
            for (int j = 0; j < height+2; j++) {
                // size+2 because we need buttons along each edge to slide rows/columns with

                System.out.println("-------------");

                // If a button to slide row/column should go here, make it
                if (j % 2 == 0 && j > 0 && j < height+1) { // if even row
                    if (i == 0) { // if leftmost column
                        Button slideRowRightButton = new Button(">");
                        int finalJ = j; // gets rid of an error to do with lambda expressions
                        slideRowRightButton.setOnAction(event -> {
                            slideRowRight(boardPane, finalJ);
                        });
                        boardPane.add(slideRowRightButton, i, j);
                        // Aligns button centrally
                        GridPane.setHalignment(slideRowRightButton, HPos.CENTER); //size
                    } else if (i == width+1) { // if rightmost column
                        Button slideRowLeftButton = new Button("<");
                        int finalJ = j;
                        slideRowLeftButton.setOnAction(event -> {
                            slideRowLeft(boardPane, finalJ);
                        });
                        boardPane.add(slideRowLeftButton, i, j);
                        GridPane.setHalignment(slideRowLeftButton, HPos.CENTER);
                    }
                } else if (i % 2 == 0 && i > 0 && i < width+1) { // if even column
                    if (j == 0) { // if top row
                        Button slideColumnDownButton = new Button("\\/"); // extra backslash so it isn't an escape character
                        int finalI = i; // gets rid of an error to do with lambda expressions
                        slideColumnDownButton.setOnAction(event -> {
                            slideColumnDown(boardPane, finalI);
                        });
                        boardPane.add(slideColumnDownButton, i, j);
                        // Aligns button centrally
                        GridPane.setHalignment(slideColumnDownButton, HPos.CENTER);
                    } else if (j == height+1) { // if bottom row
                        Button slideColumnUpButton = new Button("/\\");
                        int finalI = i; // gets rid of an error to do with lambda expressions
                        slideColumnUpButton.setOnAction(event -> {
                            slideColumnUp(boardPane, finalI);
                        });
                        boardPane.add(slideColumnUpButton, i, j);
                        GridPane.setHalignment(slideColumnUpButton, HPos.CENTER);
                    }
                }

                // If a tile should go here, make a tile
                if (!(i == 0 || j == 0 || i == width+1 || j == height+1)) { // if not edge row/column so if tile space
                    StackPane stackPane = new StackPane();

                    FloorTile floorTile = null;
                    try {
                        floorTile = board.getTile(i-1, j-1);
                    } catch (IllegalStateException e) {
                        System.out.println("Ran out of floor tiles.");
                        e.printStackTrace();
                        System.exit(1);
                    }

                    String imageNeeded = null;
                    Set<Direction> directions = floorTile.getDirections();
                    // Compare each tile to the different kinds of image, making sure to try it all 4 ways up.
                    int rotations;
                    for (rotations = 0; rotations < 4; rotations++) {
                        System.out.println(directions);
                        if (directions.equals(Direction.CROSSROADS)) {
                            imageNeeded = "crossroads_tile";
                            break;
                        } else if (directions.equals(Direction.STRAIGHT)) {
                            imageNeeded = "straight_tile";
                            break;
                        } else if (directions.equals(Direction.CORNER)) {
                            imageNeeded = "corner_tile";
                            break;
                        } else if (directions.equals(Direction.TSHAPE)) {
                            imageNeeded = "t_shape_tile";
                            break;
                        }
                        directions = Direction.rotate(directions);
                    }
                    System.out.println(imageNeeded);
                    System.out.println(rotations);

                    FileInputStream file = null;
                    try {
                        file = new FileInputStream("Sprites/" + imageNeeded + ".png");
                    } catch (Exception e) {
                        System.out.println("Could not find tile image.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    Image image = new Image(file);
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    imageView.setRotate(-90 * rotations);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(80);
                    stackPane.getChildren().add(imageView);
                    boardPane.add(stackPane, i, j);
                }
            }
        }

        // Aligns board to the middle of its container
        boardPane.setAlignment(Pos.CENTER);
        System.out.println(boardPane.getChildren());
        return boardPane;
    }

    public static void slideRowRight(GridPane boardPane, int rowNum) {
        game.getBoard().slideRowRight(rowNum-1);
        Node pushedOffTile = getFromGrid(boardPane, game.getBoard().getWidth(), rowNum);
        boardPane.getChildren().remove(pushedOffTile);
        for (int i = game.getBoard().getWidth()-1; i >= 1; i--) {
            moveNode(boardPane, i, rowNum, i+1, rowNum);
        }
        boardPane.add(pushedOffTile, 1, rowNum);
    }

    public static void slideRowLeft(GridPane boardPane, int rowNum) {
        game.getBoard().slideRowLeft(rowNum-1);
        Node pushedOffTile = getFromGrid(boardPane, 1, rowNum);
        boardPane.getChildren().remove(pushedOffTile);
        for (int i = 2; i <= game.getBoard().getWidth(); i++) {
            moveNode(boardPane, i, rowNum, i-1, rowNum);
        }
        boardPane.add(pushedOffTile, game.getBoard().getWidth(), rowNum);
    }

    public static void slideColumnDown(GridPane boardPane, int columnNum) {
        game.getBoard().slideColumnDown(columnNum-1);
        Node pushedOffTile = getFromGrid(boardPane, columnNum, game.getBoard().getHeight());
        boardPane.getChildren().remove(pushedOffTile);
        for (int i = game.getBoard().getHeight()-1; i >= 1; i--) {
            moveNode(boardPane, columnNum, i, columnNum, i+1);
        }
        boardPane.add(pushedOffTile, columnNum, 1);
    }

    public static void slideColumnUp(GridPane boardPane, int columnNum) {
        game.getBoard().slideColumnUp(columnNum-1);
        Node pushedOffTile = getFromGrid(boardPane, columnNum, 1);
        boardPane.getChildren().remove(pushedOffTile);
        for (int i = 2; i <= game.getBoard().getHeight(); i++) {
            moveNode(boardPane, columnNum, i, columnNum, i-1);
        }
        boardPane.add(pushedOffTile, columnNum, game.getBoard().getHeight());
    }

    private static Node getFromGrid(GridPane gridPane, int x, int y) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if(gridPane.getRowIndex(node) == y && gridPane.getColumnIndex(node) == x) {
                result = node;
                break;
            }
        }

        return result;
    }

    private static void moveNode(GridPane gridPane, int oldX, int oldY, int newX, int newY) {
        Node thisNode = getFromGrid(gridPane, oldX, oldY);
        gridPane.getChildren().remove(thisNode);
        gridPane.add(thisNode, newX, newY);
    }

    public static void setGame(Game givenGame) {
        game = givenGame;
    }


    // This gets automatically called because it's called initialize and it's in the class set as Controller in the FXML
    public void initialize() {
        centerPane.getChildren().add(drawBoard());
    }
}
