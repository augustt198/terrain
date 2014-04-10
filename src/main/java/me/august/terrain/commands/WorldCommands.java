package me.august.terrain.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import me.august.terrain.TerrainPlugin;
import me.august.terrain.generator.Generator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by August on 4/8/14.
 */
public class WorldCommands {

	@Command(
			aliases = {"gen", "generate"},
			usage = "[world name] [generator]",
			desc = "Generate a new world",
			min = 2,
			max = 2
	)
	public static void generateCommand(CommandContext args, CommandSender sender) throws CommandException {
		String genName = args.getString(1);
		Generator gen = TerrainPlugin.getGenerator(genName);
		if(gen == null) {
			throw new CommandException("Generator \"" + genName + "\" not found.");
		}

		String worldName = args.getString(0);

		if(Bukkit.getWorld(worldName) != null) {
			throw new CommandException("World \"" + worldName + "\" already exists.");
		}

		long startTime = System.currentTimeMillis();

		WorldCreator creator = new WorldCreator(worldName).generator(gen);
		creator.createWorld();

		double duration = ((double) System.currentTimeMillis() - startTime) / 1000;

		sender.sendMessage(ChatColor.GREEN + "Created world \"" + worldName + "\" with generator \"" + genName + "\" [" +
				((double) Math.round(duration * 1000) / 1000) + "s]");

	}

	@Command(
			aliases = {"wtp", "worldtp"},
			usage = "[world name]",
			desc = "Teleport to a world",
			min = 1,
			max = 1
	)
	public static void worldTPCommand(CommandContext args, CommandSender sender) throws CommandException {
		World world = Bukkit.getWorld(args.getString(0));
		if(world == null) throw new CommandException("World not found!");
		if(!(sender instanceof Player)) throw new CommandException("You must be a player to use that command");
		Player player = (Player) sender;
		player.teleport(world.getHighestBlockAt(0, 0).getLocation());
	}

}
