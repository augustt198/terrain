package me.august.terrain.generator;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

/**
 * Created by August on 4/8/14.
 */
public abstract class Structure extends BlockPopulator {

	private final int chance;
	private final int totalChance;
	private int offsetX = 0;
	private int offsetZ = 0;
	private boolean surface;
	private Range rangeY;


	protected Structure(int chance, int totalChance) {
		this.chance = chance;
		this.totalChance = totalChance;
	}

	public void setOffset(int x, int z) {
		offsetX = x;
		offsetZ = z;
	}

	public void setRankY(Range r) {
		rangeY = r;
	}

	public void setRankY(int y1, int y2) {
		rangeY = new Range(y1, y2);
	}

	public void setSurface(boolean surface) {
		this.surface = surface;
	}

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		if(random.nextInt(totalChance) == chance) {
			Vector origin = new Vector(chunk.getX() * 16, 0, chunk.getZ() * 16);
			origin.add(new Vector(offsetX, 0, offsetZ));
			if(surface) {
				origin.setY(world.getHighestBlockYAt(origin.getBlockX(), origin.getBlockZ()));
			} else if(rangeY != null) {
				origin.setY(rangeY.rand());
			}
			for(BlockData blockData : getBlocks()) {
				blockData.createBlock(world, origin);
			}
		}
	}

	public abstract List<BlockData> getBlocks();

}
