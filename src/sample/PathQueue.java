package sample;

import java.util.LinkedList;

public class PathQueue implements PathOrderer {
	private LinkedList<Path> paths = new LinkedList<Path>();
	
	public PathQueue() {
		paths = new LinkedList<Path>();
	}
	
	public PathQueue(PathQueue other) {
		this();
		paths.addAll(other.paths);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof PathQueue) {
			return this.paths.equals(((PathQueue)other).paths);
		} else {
			return false;
		}
	}

	@Override
	public void put(Path p) {
        paths.addLast(p);
	}

	@Override
	public Path peek() {
		return paths.get(0);
	}

	@Override
	public Path remove() {
		return paths.removeFirst();
	}

	@Override
	public boolean isEmpty() {
		return paths.size() == 0;
	}
}
