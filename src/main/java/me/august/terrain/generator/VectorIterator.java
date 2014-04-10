package me.august.terrain.generator;

import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by August on 4/8/14.
 */
public class VectorIterator implements Iterable<Vector> {

	private Range rangeX;
	private Range rangeY;
	private Range rangeZ;

	public VectorIterator(Range rangeX, Range rangeY, Range rangeZ) {
		this.rangeX = rangeX;
		this.rangeY = rangeY;
		this.rangeZ = rangeZ;
	}

	@Override
	public Iterator<Vector> iterator() {
		List<Vector> vectors = new ArrayList<>();
		for(int x : rangeX) {
			for(int y : rangeY) {
				for(int z : rangeZ) {
					vectors.add(new Vector(x, y, z));
				}
			}
		}
		return vectors.iterator();
	}
}
