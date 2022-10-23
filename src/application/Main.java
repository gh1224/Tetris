package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int LEN = 30;
    public static final int GAP = 3;
    public static final int SPACE = LEN + GAP;
    public static final int X_NUM = 10;
    public static final int Y_NUM = 20;
    public static final int X_LEN = (LEN + GAP) * X_NUM - GAP;
    public static final int Y_LEN = (LEN + GAP) * Y_NUM - GAP;

    public static Rectangle[][] data = new Rectangle[X_NUM][Y_NUM];
    public static AnchorPane pane = new AnchorPane();
    public static Scene scene = new Scene(pane, X_LEN + 300, Y_LEN);
    public static Controller control = new Controller();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(scene);
        primaryStage.setTitle("TETRIS");
        primaryStage.show();

        Line border = new Line(X_LEN + 1, 0, X_LEN + 1, Y_LEN);
        pane.getChildren().add(border);

        control.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
