package sample;

import java.util.ArrayList;

public class Path {
	private Cell location;
	private Path prev;
	private int length;
	
	public Path(Cell start) {
		this(start.getX(), start.getY(), null);
	}
	
	public Path(Cell current, Path parent) {
		this(current.getX(), current.getY(), parent);
	}
	
	public Cell getLocation() {
		return location;
	}
	
	public int totalDistanceTo(Cell target) {
		return length + target.distanceTo(location);
	}
	
	public boolean hasCycle() {
		ArrayList<Cell> cells = getCells();
		for (int i = 0; i < cells.size(); i++) {
			for (int j = i+1; j < cells.size(); j++) {
				if (cells.get(i).equals(cells.get(j))) {
					return true;
				}
			}
		}
		return false;
	}
    public int getLength() {
        return length;
    }
	
	private Path(int x, int y, Path prev) {
		location = new Cell(x, y);
		this.prev = prev;
		this.length = (prev == null ? 1 : 1 + prev.length);
	}
	
	public ArrayList<Cell> getCells() {
		ArrayList<Cell> result = new ArrayList<>();
		putCellsIn(result);
		return result;
	}
	
	private void putCellsIn(ArrayList<Cell> cells) {
		if (prev != null) {
			prev.putCellsIn(cells);
		}
		cells.add(location);
	}
	
	public boolean equals(Object other) {
		return toString().equals(other.toString());
	}
	
	public int hashCode() {return toString().hashCode();}
	
	public String toString() {
		String prevStr = prev == null ? "" : prev.toString();
		return prevStr + location.toString() + ":";
	}
}
