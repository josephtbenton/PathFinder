package sample;

import java.util.ArrayList;

public class PathStack implements PathOrderer {
	private ArrayList<Path> paths;
	
	public PathStack() {
		paths = new ArrayList<Path>();
	}
	
	public PathStack(PathStack other) {
		this();
		paths.addAll(other.paths);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof PathStack) {
			return this.paths.equals(((PathStack)other).paths);
		} else {
			return false;
		}
	}

	@Override
	public void put(Path p) {
        paths.add(p);
	}

	@Override
	public Path peek() {
		return paths.get(paths.size() - 1);
	}

	@Override
	public Path remove() {
		return paths.remove(paths.size() - 1);
	}

	@Override
	public boolean isEmpty() {
		return paths.size() == 0;
	}
}
