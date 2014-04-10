package me.august.terrain.schematic;

import me.august.terrain.generator.BlockData;
import me.august.terrain.generator.Structure;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import org.jnbt.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by August on 4/9/14.
 */
public class StructureBuilder {

	public static Structure buildSchematic(File schem, double probability) throws SchematicException {
		NBTInputStream in;
		try {
			in = new NBTInputStream(new FileInputStream(schem));
		} catch(IOException e) {
			throw new SchematicException("Could not create InputStream from file: " + schem.getPath());
		}

		Tag tag;
		try {
			tag = in.readTag();
		} catch(IOException e) {
			throw new SchematicException("Could not read tag from schematic");
		}

		if(!(tag instanceof CompoundTag)) throw new SchematicException("Root schematic tag should be a compound");
		CompoundTag root = (CompoundTag) tag;

		short width = ((ShortTag) root.getValue().get("Width")).getValue();
		short height = ((ShortTag) root.getValue().get("Height")).getValue();
		short length = ((ShortTag) root.getValue().get("Length")).getValue();

		List<BlockData> blocks = new ArrayList<>();

		ByteArrayTag blockBytes = ((ByteArrayTag) root.getValue().get("Blocks"));

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < length; z++) {
					byte block = blockBytes.getValue()[(x * width + z) * length + y];
					Vector vec = new Vector(x, y, z);
					blocks.add(new BlockData(vec, Material.getMaterial(block)));
				}
			}
		}

		final List<BlockData> data = blocks;
		return new Structure((int) probability * 100, 100) {
			@Override
			public List<BlockData> getBlocks() {
				return data;
			}
		};

	}

	public static class SchematicException extends Exception {

		public SchematicException(String message) {
			super(message);
		}
	}

}
