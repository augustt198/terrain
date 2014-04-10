package me.august.terrain.generator;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by August on 4/8/14.
 */
@SuppressWarnings("deprecated")
public abstract class Generator extends ChunkGenerator {

	protected List<BlockPopulator> structures = new ArrayList<>();
	protected String name;
	protected NoiseGenerator noise;
	private double stretchX;
	private double stretchZ;
	private int varianceY;
	private int noiseHeightOffset;
	private boolean useHeightNoise;

	protected NoiseGenerator getNoise(World w) {
		if(noise == null) {
			noise = new SimplexNoiseGenerator(w);
		}
		return noise;
	}

	protected int getHeight(World world, double x, double z) {
		NoiseGenerator noise = getNoise(world);
		double result = noise.noise(x / stretchX, z / stretchZ);
		result *= varianceY;
		return NoiseGenerator.floor(result);
	}

	/* Flatten multidimensional array */
	public byte[] generate(World world, Random random, int cx, int cz) {
		byte[][][] bytes = getBytes(world, random, cx, cz);
		byte[] result = new byte[16 * 16 * 256];
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				int height = useHeightNoise ? getHeight(world, cx + x * 0.0625, cz + z * 0.0625) + noiseHeightOffset: 256;
				for(int y = 0; y < height; y++) {
					result[(x * 16 + z) * 256 + y] = bytes[x][y][z];
				}
			}
		}
		return result;
	}


	/* Should return byte[16][256][16] */
	public abstract byte[][][] getBytes(World w, Random r, int x, int z);

	public double getStretchX() {
		return stretchX;
	}

	public void setStretchX(double stretchX) {
		this.stretchX = stretchX;
	}

	public double getStretchZ() {
		return stretchZ;
	}

	public void setStretchZ(double stretchZ) {
		this.stretchZ = stretchZ;
	}

	public int getVarianceY() {
		return varianceY;
	}

	public void setVarianceY(int varianceY) {
		this.varianceY = varianceY;
	}

	public boolean isUsingHeightNoise() {
		return useHeightNoise;
	}

	public void setUseHeightNoise(boolean useHeightNoise) {
		this.useHeightNoise = useHeightNoise;
	}

	public int getNoiseHeightOffset() {
		return noiseHeightOffset;
	}

	public void setNoiseHeightOffset(int noiseHeightOffset) {
		this.noiseHeightOffset = noiseHeightOffset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addStructure(Structure s) {
		structures.add(s);
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World w) {
		return structures;
	}

}
