package me.august.terrain;

import com.sk89q.minecraft.util.commands.*;
import me.august.terrain.commands.CommandSenderTypeException;
import me.august.terrain.commands.CommandsHelper;
import me.august.terrain.commands.WorldCommands;
import me.august.terrain.generator.Generator;
import me.august.terrain.generator.Range;
import me.august.terrain.generator.presets.DecorationStructure;
import me.august.terrain.generator.presets.LayerGenerator;
import me.august.terrain.generator.presets.OreStructure;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by August on 4/8/14.
 */
public class TerrainPlugin extends JavaPlugin {

	private static TerrainPlugin instance;

	private CommandsHelper commands = new CommandsHelper(this);
	private List<Generator> generators = new ArrayList<>();

	@Override
	public void onEnable() {
		instance = this;

		addLayerGen();

		commands.addCommand(WorldCommands.class);

		getLogger().info("Testing range iterator:");
		for(int i : new Range(0, 10)) {
			getLogger().info("" + i);
		}

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			this.commands.execute(cmd.getName(), args, sender, sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
		} catch (MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
			} else {
				sender.sendMessage(ChatColor.RED + "An error has occurred. Try again later.");
				e.printStackTrace();
			}
		} catch(CommandSenderTypeException e) {
			sender.sendMessage(ChatColor.RED + "You must be a " + e.allowedSendersString() + " to use that command");
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}

		return true;
	}

	private void addLayerGen() {
		Map<Range, Material> layers = new HashMap<>();
		layers.put(new Range(0, 1), Material.BEDROCK);
		layers.put(new Range(1, 50), Material.STONE);
		layers.put(new Range(50, 56), Material.DIRT);
		layers.put(new Range(56, 57), Material.GRASS);
		Generator gen = new LayerGenerator(layers);

		gen.addStructure(new DecorationStructure(1, 2, Material.YELLOW_FLOWER, Material.RED_ROSE));
		gen.addStructure(new OreStructure(0, 20, Material.IRON_BLOCK, 2, 60, 70));
		gen.setName("layered");
		addGenerator(gen);
	}

	public static List<Generator> getGenerators() {
		return get().generators;
	}

	public static void addGenerator(Generator gen) {
		get().generators.add(gen);
	}

	public static Generator getGenerator(String name) {
		for(Generator g : getGenerators()) {
			if(g.getName() != null && g.getName().equalsIgnoreCase(name)) return g;
		}
		return null;
	}

	public static TerrainPlugin get() {
		return instance;
	}
}
