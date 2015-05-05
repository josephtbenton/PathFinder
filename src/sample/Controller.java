package sample;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;


public class Controller {
    @FXML
    GridPane canvas;
    @FXML
    TextArea text;
    BlockMaze blocks;
    private boolean changeEntry, changeExit;

    @FXML
    public void initialize() {
        canvas.setCenterShape(true);
        blocks = new BlockMaze(canvas, 13, 13);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                ev -> {
                    addOrRemove(ev);
                });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                ev -> {
                    addOrRemove(ev);
                });
    }

    @FXML
    public void clear() {
        blocks.clear();
        text.clear();
        text.appendText("Maze Cleared");
    }

    private void addOrRemove(MouseEvent ev) {
        if (ev.isSecondaryButtonDown()) {
            blocks.removeWall((int) (ev.getX() / 25), (int) (ev.getY() / 25));
        } else if(changeEntry) {
            blocks.addEntry((int) (ev.getX() / 25), (int) (ev.getY() / 25));
            changeEntry = false;
        } else if (changeExit) {
            blocks.addExit((int) (ev.getX() / 25), (int) (ev.getY() / 25));
            changeExit = false;
        } else {
            blocks.addWall((int) (ev.getX() / 25), (int) (ev.getY() / 25));
        }
        text.clear();
    }
    @FXML
    public void entry() {
        text.clear();
        text.appendText("Click to add entrance");
        changeEntry = true;
    }

    @FXML
    public void exit() {
        text.clear();
        text.appendText("Click to add exit");
        changeExit = true;
    }
    @FXML
    public void solveStack() {
       Path p = solve(new PathStack(), blocks.generateMaze());
        drawPath(p, Color.BLUE);
        text.clear();
        text.appendText("PathStack solution length: " + p.getLength());
    }

    @FXML
    public void solveHeap() {
        Maze m = blocks.generateMaze();
       Path p = solve(new PathHeap(m.getExit()), m);
        drawPath(p, Color.CHARTREUSE);
        text.clear();
        text.appendText("PathHeap solution length: " + p.getLength());
    }

    @FXML
    public void solveQueue() {
        Path p = solve(new PathQueue(), blocks.generateMaze());
        drawPath(p, Color.CORAL);
        text.clear();
        text.appendText("PathQueue solution length: " + p.getLength());
    }

    private void drawPath(Path p, Color color) {
        Cell prev = blocks.getStart();
        Cell next;
        for (int i = 0; i < p.getCells().size()-1; i++) {
            Cell c = p.getCells().get(i);
            next = p.getCells().get(i+1);
            drawLineSegment(prev, c, color);
            drawLineSegment(next, c, color);
            prev = c;
        }
    }

    private void drawLineSegment(Cell adj, Cell c, Color color) {
        if (adj.getY() == c.getY()) {
            Rectangle r = new Rectangle(26/2, 2, color);
            canvas.add(r, c.getX(), c.getY());
            if (adj.getX() < c.getX()) {
                GridPane.setHalignment(r, HPos.LEFT);
            } else {
                GridPane.setHalignment(r, HPos.RIGHT);

            }
        } else if (adj.getX() == c.getX()) {
            Rectangle r = new Rectangle(2, 26/2, color);
            canvas.add(r, c.getX(), c.getY());
            if (adj.getY() < c.getY()) {
                GridPane.setValignment(r, VPos.TOP);
                GridPane.setHalignment(r, HPos.CENTER);
            } else {
                GridPane.setValignment(r, VPos.BOTTOM);
                GridPane.setHalignment(r, HPos.CENTER);

            }
        }
    }

    private Path solve(PathOrderer queue, Maze m) {
        int evaluated;
        int enqueued = evaluated = 0;
        queue.put(new Path(m.getEntry()));
        HashSet<Cell> visited = new HashSet<Cell>();
        enqueued++;
        Path solution;
        while (!queue.isEmpty() && queue.peek().getLocation().distanceTo(m.getExit()) != 0) {
            Path p = queue.remove();
            evaluated++;
            if (!visited.contains(p.getLocation())) {
                visited.add(p.getLocation());
                if (p.getLocation().equals(m.getExit())) {
                    solution = p;
                    break;
                }
                for (Cell c: p.getLocation().getNeighbors()) {
                    if (m.inBounds(c) && m.isOpen(p.getLocation())) {
                        queue.put(new Path(c,p));
                        enqueued++;
                    }
                }
            }


        }

        System.out.println(queue.isEmpty() ? "empty" : "end:" + queue.peek().getLocation() + "; " + m.getExit());
        solution = queue.peek();
        return solution;
    }
}
