package me.august.terrain.generator.presets;

import me.august.terrain.generator.Generator;
import me.august.terrain.generator.Range;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Map;
import java.util.Random;

/**
 * Created by August on 4/9/14.
 */
public class LayerGenerator extends Generator {

	private Map<Range, Material> layers;

	public LayerGenerator(Map<Range, Material> layers) {
		this.layers = layers;
	}

	@Override
	public byte[][][] getBytes(World world, Random random, int cx, int cz) {
		byte[][][] data = new byte[16][256][16];
		for(Range r : layers.keySet()) {
			Material mat = layers.get(r);
			for(int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					for(int y : r) {
						data[x][y][z] = (byte) mat.getId();
					}
				}
			}
		}
		return data;
	}

}
