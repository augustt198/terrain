package me.august.terrain.generator.presets;

import me.august.terrain.generator.BlockData;
import me.august.terrain.generator.Range;
import me.august.terrain.generator.Structure;
import me.august.terrain.generator.VectorIterator;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by August on 4/8/14.
 */
public class OreStructure extends Structure {

	private Material type;
	private int veinSize;

	public OreStructure(int chance, int totalChance, Material type, int veinSize, int minHeight, int maxHeight) {
		super(chance, totalChance);
		this.type = type;
		this.veinSize = veinSize;
		setRankY(minHeight, maxHeight);
	}

	public List<BlockData> getBlocks() {
		List<BlockData> blocks = new ArrayList<>();
		Range range = new Range(0, veinSize);
		for(Vector v : new VectorIterator(range, range, range)) {
			blocks.add(new BlockData(v, type));
		}
		return blocks;
	}

}
