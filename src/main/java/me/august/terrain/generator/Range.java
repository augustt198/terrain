package me.august.terrain.generator;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by August on 4/8/14.
 */
public class Range implements Iterable<Integer> {

	private int min;
	private int max;

	public Range(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public Range(int num) {
		min = num;
		max = num;
	}

	public int rand() {
		if(min == max) return min;
		return new Random().nextInt((getMax() - getMin()) + 1) + getMin();
	}

	public int getMin() {
		return Math.min(min, max);
	}
	public int getMax() {
		return Math.max(min, max);
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<Integer> {

		private int current;

		@Override
		public boolean hasNext() {
			return current != max;
		}

		@Override
		public Integer next() {
			int i = current;
			current++;
			return i;
		}

		@Override
		public void remove() {}
	}

}
