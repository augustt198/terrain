package me.august.terrain.commands;

import com.sk89q.minecraft.util.commands.CommandException;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

import java.util.List;

/**
 * Created by August on 4/8/14.
 */
public class CommandSenderTypeException extends CommandException {

	private List<Class<? extends CommandSender>> allowed;

	public CommandSenderTypeException(List<Class<? extends CommandSender>> allowed) {
		this.allowed = allowed;
	}

	public String allowedSendersString() {
		String s = "";
		for(int i = 0; i < allowed.size(); i++) {
			s += getSimpleName(allowed.get(i));
			if(i != allowed.size() - 1) s += ", ";
		}
		return s;
	}

	private static String getSimpleName(Class<? extends CommandSender> type) {
		if(type == ConsoleCommandSender.class || type == RemoteConsoleCommandSender.class) {
			return "console";
		} else if(type == BlockCommandSender.class) {
			return "command block";
		} else if(type == CommandMinecart.class) {
			return "command block minecart";
		} else if(type == Player.class) {
			return "player";
		}
		return "";
	}

}
