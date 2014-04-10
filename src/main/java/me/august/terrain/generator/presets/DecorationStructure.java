package me.august.terrain.generator.presets;

import me.august.terrain.generator.BlockData;
import me.august.terrain.generator.Range;
import me.august.terrain.generator.Structure;
import me.august.terrain.generator.VectorIterator;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by August on 4/8/14.
 */

/* Flowers, grass, etc... */
public class DecorationStructure extends Structure {

	private Material[] materials;

	public DecorationStructure(int chance, int totalChance, Material... materials) {
		super(chance, totalChance);
		setSurface(true);

		this.materials = materials;
	}

	@Override
	public List<BlockData> getBlocks() {
		Random r = new Random();
		List<BlockData> blocks = new ArrayList<>();
		for(Vector v : new VectorIterator(new Range(0, 16), new Range(0, 1), new Range(0, 16))) {
			if(r.nextInt(14) != 1) continue;
			Material mat = materials[r.nextInt(materials.length)];
			blocks.add(new BlockData(v, mat));
		}
		return blocks;
	}
}
