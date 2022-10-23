package application;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    private static AnchorPane pane = Main.pane;
    private static Scene scene = Main.scene;
    private static final int SPACE = Main.SPACE;
    private static final int X_NUM = Main.X_NUM;
    private static final int Y_NUM = Main.Y_NUM;
    private static Rectangle[][] data = Main.data;
    private Form cur;
    private Form result = new Form();
    private Form prev;
    private KeyEventHandler keyHandler = new KeyEventHandler(this);
    private Timer fall = new Timer();

    public void play() {
        scene.setOnKeyPressed(keyHandler);
        result.addToPane(pane);
        result.resultSetting();

        prev = new Form();
        prev.addToPane(pane);
        newBlock();

        TimerTask task = new TimerTask() {
            public void run() {
            Platform.runLater(new Runnable() {
                public void run() {
                    if (!cur.moveDown()) {
                        addToData(cur);
                        checkLine();
                        newBlock();
                    }
                }
            });
            }
        };

        fall.schedule(task, 0, 500);
    }

    private void checkLine() {
        for (int y = 0; y < Y_NUM; y++) {
            int x;
            for (x = 0; x < X_NUM; x++) {
                if (data[x][y] == null) {
                    break;
                }
            }
            if (x == X_NUM) {
                for (int i = 0; i < X_NUM; i++)
                    pane.getChildren().remove(data[i][y]);
                for (int i = y - 1; i >= 0; i--)
                    for (int j = 0; j < X_NUM; j++) {
                        if (data[j][i] != null)
                            data[j][i].setY(data[j][i].getY() + SPACE);
                        data[j][i + 1] = data[j][i];
                    }
            }
        }
    }

    public void setResult() {
        result.copy(cur);
        while (result.moveDown());
    }

    public void newBlock() {
        cur = prev;
        prev = new Form();
        prev.addToPane(pane);
        cur.newBlockSet();
        if (!cur.isEmpty())
            finish();
        keyHandler.setForm(cur);
        setResult();
    }

    public void addToData(Form form) {
        for (int i = 0; i < 4; i++)
            data[form.getCoorX(i)][form.getCoorY(i)] = form.getRect(i);
    }

    public void finish() {
        Text over = new Text("GAME OVER");
        over.setFill(Color.RED);
        over.setStyle("-fx-font: 50 arial;");
        over.setY(270);
        over.setX(50);
        pane.getChildren().add(over);

        fall.cancel();
        pane.removeEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
    }
}

class KeyEventHandler implements EventHandler<KeyEvent> {
    Form cur;
    Controller control;

    public KeyEventHandler(Controller c) {
        control = c;
    }
    public void setForm(Form f) {
        cur = f;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case DOWN:
                cur.moveDown();
                break;
            case RIGHT:
                cur.moveRight();
                control.setResult();
                break;
            case LEFT:
                cur.moveLeft();
                control.setResult();
                break;
            case UP:
                cur.rotate();
                control.setResult();
                break;
            case SPACE:
                while (cur.moveDown());
                break;
        }
    }
}
