package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class Maze {
	private boolean[][] open;
	private Cell start, end;
	
	public Maze(int width, int height) {
		open = new boolean[width][height];
		start = new Cell(0, 0);
	}
	
	public Maze(String src) {
		String[] rows = src.split("\n");
		open = new boolean[rows[0].length()][rows.length];
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				char c = rows[j].charAt(i);
				if (c != '#') {
					open[i][j] = true;
					if (c == 'S') {
						start = new Cell(i, j);
					} else if (c == 'X') {
						end = new Cell(i, j);
					}
				}
			}
		}
	}
	
	public Cell getEntry() {return start;}
	public Cell getExit() {return end;}
	
	private void resetExit() {
		for (int i = 0; i < Math.min(getWidth(), getHeight()); i++) {
			if (tryExit(new Cell(getWidth() - i - 1, getHeight() - 1))) {
				return;
			} else if (tryExit(new Cell(getWidth() - 1, getHeight() - i - 1))) {
				return;
			}
		}
	}
	
	private boolean tryExit(Cell candidate) {
		if (isOpen(candidate)) {
			end = candidate;
			System.out.println("New end: " + end);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValidPath(ArrayList<Cell> pathCells) {
		if (pathCells.get(0).equals(getEntry()) && pathCells.get(pathCells.size() - 1).equals(getExit())) {
			for (int i = 1; i < pathCells.size(); i++) {
				if (!isOpen(pathCells.get(i)) || !pathCells.get(i).isNextTo(pathCells.get(i-1))) {
					return false;
				}
			}
		}
		return true;
	}
	
	public int getWidth() {return open.length;}
	public int getHeight() {return open[0].length;}
	
	public void tunnel(Cell c) {
		open[c.getX()][c.getY()] = true;
	}
	
	public boolean isOpen(Cell c) {
		return inBounds(c) && open[c.getX()][c.getY()];
	}
	
	public int numOpenNeighbors(Cell c) {
		int count = 0;
		for (Cell n: c.getNeighbors()) {
			if (inBounds(n) && isOpen(n)) {
				count += 1;
			}
		}
		return count;
	}
	
	public boolean inBounds(Cell c) {
		return c.getX() >= 0 && c.getX() < getWidth() && c.getY() >= 0 && c.getY() < getHeight();
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				Cell c = new Cell(j, i);
				if (c.equals(getEntry())) {
					result.append("S");
				} else if (c.equals(getExit())) {
					result.append("X");
				} else {
					result.append(isOpen(c) ? "_" : "#");
				}
			}
			result.append("\n");
		}
		return result.toString();
	}

	public static Maze makeRandomized(int width, int height) {
		Maze m = new Maze(width, height);
		Random rand = new Random();
		ArrayList<Cell> candidates = new ArrayList<>();
		HashSet<Cell> visited = new HashSet<>();
		Cell start = m.getEntry();
		candidates.add(start);
		while (candidates.size() > 0) {
			int pick = rand.nextInt(candidates.size());
			Collections.swap(candidates, pick, candidates.size() - 1);
			Cell next = candidates.remove(candidates.size() - 1);
			if (!visited.contains(next) && m.numOpenNeighbors(next) <= 1) {
				visited.add(next);
				m.tunnel(next);
				for (Cell c: next.getNeighbors()) {
					if (m.inBounds(c)) {
						candidates.add(c);
					}
				}
			}
		}
		m.resetExit();
		return m;
	}
}
