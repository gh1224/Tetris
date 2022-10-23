package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Form {
    private static final int LEN = Main.LEN;
    private static final int SPACE = Main.SPACE;
    private static final int X_NUM = Main.X_NUM;
    private static final int Y_NUM = Main.Y_NUM;
    private static final int X_START = 4;
    private static final int Y_START = 1;
    private static final int X_PREV = Main.X_LEN + 100;
    private static final int Y_PREV = 80;
    private static final int STROKE_WIDTH = 2;
    private static final int[][][][] TYPE_POS = {
            {{{-1, 0}, {0, 0}, {1, 0}, {0, -1}}, {{0, -1}, {0, 0}, {0, 1}, {1, 0}}, {{-1, 0}, {0, 0}, {1, 0}, {0, 1}}, {{-1, 0}, {0, -1}, {0, 0}, {0, 1}}},
            {{{0, 0}, {0, -1}, {1, -1}, {1, 0}}, {{0, 0}, {0, -1}, {1, -1}, {1, 0}}, {{0, 0}, {0, -1}, {1, -1}, {1, 0}}, {{0, 0}, {0, -1}, {1, -1}, {1, 0}}},
            {{{-1, 0}, {0, 0}, {1, 0}, {1, -1}}, {{0, -1}, {0, 0}, {0, 1}, {1, 1}}, {{-1, 1}, {-1, 0}, {0, 0}, {1, 0}}, {{-1, -1}, {0, -1}, {0, 0}, {0, 1}}},
            {{{-1, -1}, {-1, 0}, {0, 0}, {1, 0}}, {{1, -1}, {0, -1}, {0, 0}, {0, 1}}, {{-1, 0}, {0, 0}, {1, 0}, {1, 1}}, {{-1, 1}, {0, -1}, {0, 0}, {0, 1}}},
            {{{-1, 0}, {0, 0}, {1, 0}, {2, 0}}, {{0, -1}, {0, 0}, {0, 1}, {0, 2}}, {{-1, 0}, {0, 0}, {1, 0}, {2, 0}}, {{0, -1}, {0, 0}, {0, 1}, {0, 2}}},
            {{{-1, 0}, {0, 0}, {0, -1}, {1, -1}}, {{0, -1}, {0, 0}, {1, 0}, {1, 1}}, {{-1, 0}, {0, 0}, {0, -1}, {1, -1}}, {{0, -1}, {0, 0}, {1, 0}, {1, 1}}},
            {{{-1, -1}, {0, -1}, {0, 0}, {1, 0}}, {{1, -1}, {1, 0}, {0, 0}, {0, 1}}, {{-1, -1}, {0, -1}, {0, 0}, {1, 0}}, {{1, -1}, {1, 0}, {0, 0}, {0, 1}}}
    };
    private static final Color[] TYPE_COLOR = {
            Color.rgb(160, 0, 240),
            Color.rgb(240, 240, 0),
            Color.rgb(240, 160, 0),
            Color.rgb(0, 0, 240),
            Color.rgb(0, 240, 240),
            Color.rgb(0, 240, 0),
            Color.rgb(240, 0, 0)
    };
    private static Rectangle[][] data = Main.data;

    private int posX;
    private int posY;
    private int shape;
    private Color color;
    private int rotation;
    private Rectangle[] block = new Rectangle[4];

    public Form() {
        shape = (int)(Math.random() * 7);
        color = TYPE_COLOR[shape];
        rotation = 0;

        posX = X_START;
        posY = Y_START;

        for (int i = 0; i < 4; i++) {
            block[i] = new Rectangle(SPACE * TYPE_POS[shape][rotation][i][0] + X_PREV, SPACE * TYPE_POS[shape][rotation][i][1] + Y_PREV, LEN, LEN);
            block[i].setFill(color);
        }
    }

    public void newBlockSet() {
        for (int i = 0; i < 4; i++) {
            block[i].setX(SPACE * getCoorX(i));
            block[i].setY(SPACE * getCoorY(i));
        }
    }

    public void copy(Form other) {
        posX = other.posX;
        posY = other.posY;
        shape = other.shape;
        color = other.color;
        rotation = other.rotation;
        for (int i = 0; i < 4; i++) {
            block[i].setX(SPACE * getCoorX(i));
            block[i].setY(SPACE * getCoorY(i));
            block[i].setStroke(color);
        }
    }

    public void resultSetting() {
        for (int i = 0; i < 4; i++) {
            block[i].setFill(null);
            block[i].setStrokeType(StrokeType.INSIDE);
            block[i].setStrokeWidth(STROKE_WIDTH);
        }
    }

    public int getCoorX(int idx) {
        return posX + TYPE_POS[shape][rotation][idx][0];
    }

    public int getCoorY(int idx) {
        return posY + TYPE_POS[shape][rotation][idx][1];
    }

    public Rectangle getRect(int idx) {
        return block[idx];
    }

    public boolean moveDown() {
        posY++;
        if (isEmpty()) {
            for (Rectangle rect : block) {
                rect.setY(rect.getY() + SPACE);
            }
            return true;
        } else {
            posY--;
            return false;
        }

    }

    public void moveLeft() {
        posX--;
        if (isEmpty()) {
            for (Rectangle rect : block) {
                rect.setX(rect.getX() - SPACE);
            }
        } else {
            posX++;
        }
    }

    public void moveRight() {
        posX++;
        if (isEmpty()) {
            for (Rectangle rect : block) {
                rect.setX(rect.getX() + SPACE);
            }
        } else {
            posX--;
        }
    }

    public void rotate() {
        rotation++;
        rotation %= 4;
        if (isEmpty()) {
            for (int i = 0; i < 4; i++) {
                block[i].setX(SPACE * getCoorX(i));
                block[i].setY(SPACE * getCoorY(i));
            }
        } else {
            rotation += 3;
            rotation %= 4;
        }
    }

    public boolean isEmpty() {
        for (int i = 0; i < 4; i++) {
            int x = getCoorX(i);
            int y = getCoorY(i);
            if (0 > x || x >= X_NUM)
                return false;
            if (y >= Y_NUM)
                return false;
            if (data[x][y] != null)
                return false;
        }
        return true;
    }

    public void addToPane(Pane pane) {
        for (Rectangle rect : block) {
            pane.getChildren().add(rect);
        }
    }
}