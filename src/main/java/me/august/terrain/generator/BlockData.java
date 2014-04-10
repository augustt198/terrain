package me.august.terrain.generator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * Created by August on 4/8/14.
 */
public class BlockData {

	private Vector coords;
	private Material material;
	private byte data;

	public BlockData(Vector coords, Material material) {
		this(coords, material, (byte) -1);
	}

	public BlockData(Vector coords, Material material, byte data) {
		this.coords = coords;
		this.material = material;
		this.data = data;
	}

	@SuppressWarnings("deprecated")
	public void createBlock(World world, Vector origin) {
		Location base = coords.add(origin).toLocation(world);
		base.getBlock().setType(material);
		if(data > 0) {
			base.getBlock().setData(data);
		}
	}

	public Vector getCoords() {
		return coords;
	}

	public Material getMaterial() {
		return material;
	}

	public byte getData() {
		return data;
	}
}
