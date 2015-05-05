package sample;

import java.util.ArrayList;

public class PathHeap implements PathOrderer {
	private ArrayList<Path> heap;
	private Cell exit;
	
	public PathHeap(Cell exit) {
		heap = new ArrayList<>();
		this.exit = exit;
	}
	
	public PathHeap(PathHeap src) {
		this(src.exit);
		for (Path p: src.heap) {
			this.heap.add(p);
		}
	}
	
	public boolean isConsistent() {
		for (int i = 1; i < heap.size(); i++) {
			if (heap.get(i).totalDistanceTo(exit) < heap.get(parentOf(i)).totalDistanceTo(exit)) {
				return false;
			}
		}
		return true;
	}
	
	public static int leftOf(int i) {return 2 * i + 1;}
	public static int rightOf(int i) {return 2 * i + 2;}
	public static int parentOf(int i) {return (i - 1) / 2;}

	@Override
	public void put(Path p) {
        heap.add(p);
        int loc = heap.size() - 1;
        while (loc > 0 && p.totalDistanceTo(exit) < heap.get(parentOf(loc)).totalDistanceTo(exit)) {
            int parent = parentOf(loc);
            swap(loc, parent);
            loc = parent;
            /*
            if (leftOf(parent) < heap.size() && heap.get(leftOf(parent)).totalDistanceTo(exit) < p.totalDistanceTo(exit)) {
                swap(leftOf(parent), parent);
                loc = leftOf(parent);
            }
            if (rightOf(parent) < heap.size() && heap.get(rightOf(parent)).totalDistanceTo(exit) < p.totalDistanceTo(exit)) {
                swap(rightOf(parent), parent);
                loc = rightOf(parent);
            }
            */
        }

	}
    private void swap(int a, int b) {
        Path temp = heap.get(b);
        heap.set(b, heap.get(a));
        heap.set(a, temp);
    }

	@Override
	public Path peek() {
		return heap.get(0);
	}

    private int minChild(int loc) {
        int l = leftOf(loc);
        int r = rightOf(loc);
        if (r < heap.size()) {
            return heap.get(l).totalDistanceTo(exit) <= heap.get(r).totalDistanceTo(exit) ? l : r;
        } else if (l < heap.size()) {
            return l;
        } else {
            return -1;
        }
    }

	@Override
	public Path remove() {
        swap(0,heap.size() -1);
        Path res = heap.remove(heap.size() - 1);
        int loc = 0;
        while (minChild(loc) > 0) {
            if (heap.get(loc).totalDistanceTo(exit) > heap.get(minChild(loc)).totalDistanceTo(exit)) {
                int old = minChild(loc);
                swap(loc, old);
                loc = old;
            } else {
                return res;
            }
        }

		return res;
	}

	@Override
	public boolean isEmpty() {
		return heap.size() == 0;
	}

}
