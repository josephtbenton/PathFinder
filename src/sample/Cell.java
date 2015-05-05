package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cell {
    private int x, y;
	private ArrayList<Cell> neighbors;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private void buildNeighbors() {
		neighbors = new ArrayList<>();
		neighbors.add(new Cell(x, y + 1));
		neighbors.add(new Cell(x + 1, y));
		neighbors.add(new Cell(x, y - 1));
		neighbors.add(new Cell(x - 1, y));
	}
	
	public Cell(String src) {
		String[] parts = src.split(",");
		this.x = Integer.parseInt(parts[0].substring(parts[0].indexOf('(') + 1));
		this.y = Integer.parseInt(parts[1].substring(0, parts[1].indexOf(')')));
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	
	public String toString() {
		return "Cell(" + x + "," + y + ")";
	}
	
	public int distanceTo(Cell other) {
		return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
	}
	
	public int hashCode() {return x << 16 + y;}
	
	public boolean equals(Object other) {
		if (other instanceof Cell) {
			Cell that = (Cell)other;
			return this.x == that.x && this.y == that.y;
		} else {
			return false;
		}
	}
	
	public boolean isNextTo(Cell other) {
		return getNeighbors().contains(other);
	}
	
	public List<Cell> getNeighbors() {
		if (neighbors == null) {
			buildNeighbors();
		}
		return Collections.unmodifiableList(neighbors);
	}
}
