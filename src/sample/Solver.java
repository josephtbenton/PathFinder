package sample;

import java.util.HashSet;
import java.util.function.Function;

public class Solver {
	private Function<Maze,PathOrderer> constructor;
	private int enqueued, evaluated;
	private Path solution;
	
	public Solver(Function<Maze, PathOrderer> constructor) {
		this.constructor = constructor;
		this.enqueued = this.evaluated = 0;
		this.solution = null;
	}
	
	public boolean hasSolution() {
		return solution != null;
	}
	
	public Path getSolution() {
		return solution;
	}
	
	public int getEnqueued() {
		return enqueued;
	}
	
	public int getEvaluated() {
		return evaluated;
	}
	
	public void findSolutionFor(Maze m) {
		PathOrderer queue = constructor.apply(m);
		enqueued = evaluated = 0;
		queue.put(new Path(m.getEntry()));
		HashSet<Cell> visited = new HashSet<Cell>();
        enqueued++;
        while (!queue.isEmpty() && queue.peek().totalDistanceTo(m.getExit()) != 0) {
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

		solution = queue.peek();
	}
}
