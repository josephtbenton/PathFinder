package sample;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.HashSet;

public class BlockMaze {
    private boolean[][] walls;
    private Cell start, end;
    int width, height;
    GridPane canvas;

    public BlockMaze(GridPane canvas, int width, int height) {
        walls = new boolean[width][height];
        this.width = width;
        this.height = height;
        this.canvas = canvas;
    }
    public void addWall(int x, int y) {
        if (inBounds(x, y) && !walls[x][y] ) {
            walls[x][y] = true;
            addRect(x, y, Color.DARKGRAY);
        }
    }
    public void removeWall(int x, int y) {
        if (inBounds(x, y) && walls[x][y]) {
            walls[x][y] = false;
            addRect(x, y, Color.WHITESMOKE);
            if (new Cell(x,y).equals(start)) {
                start = null;
            } else if (new Cell(x,y).equals(start)) {
                end = null;
            }

        }
    }
    public void addExit(int x, int y) {
        if (end == null) {
            end = new Cell(x, y);
            walls[x][y] = true;
            addRect(x, y, Color.RED);
        }
    }
    public void addEntry(int x, int y) {
        if (start == null) {
            start = new Cell(x, y);
            walls[x][y] = true;
            addRect(x, y, Color.GREENYELLOW);
        }
    }

    private void addRect(int x, int y, Color color) {
        Rectangle r = new Rectangle(0, 0, 23, 23);
        r.setFill(color);
        canvas.add(r, x, y);
        GridPane.setValignment(r, VPos.CENTER);
        GridPane.setHalignment(r, HPos.CENTER);
    }

    private boolean inBounds(int x, int y) {
        return  x >= 0 && x < width && y >= 0 && y < height;
    }

    public void clear() {
        start = null;
        end = null;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addRect(i, j, Color.WHITESMOKE);
                removeWall(i, j);
            }
        }
    }

    public Maze generateMaze() {
        return new Maze(toString());
    }
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell c = new Cell(j, i);
                if (c.equals(start)) {
                    result.append("S");
                } else if (c.equals(end)) {
                    result.append("X");
                } else {
                    result.append(walls[j][i] ? "#" : "_");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    public Cell getExit() {
        return end;
    }

    public Cell getStart() {
        return start;
    }
}
