package me.august.terrain.commands;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by August on 4/8/14.
 */
public class CommandsHelper extends CommandsManager<CommandSender> {

	private CommandsManagerRegistration register;
	private Plugin plugin;

	public CommandsHelper(Plugin plugin) {
		this.plugin = plugin;
		register = new CommandsManagerRegistration(plugin, this);
	}

	@Override
	public void execute(String cmd, String[] args, CommandSender player, Object... methodArgs) throws CommandException {
		String[] newArgs = new String[args.length + 1];
		System.arraycopy(args, 0, newArgs, 1, args.length);
		newArgs[0] = cmd;
		Object[] newMethodArgs = new Object[methodArgs.length + 1];
		System.arraycopy(methodArgs, 0, newMethodArgs, 1, methodArgs.length);

		checkSenderAndContinue(null, newArgs, player, newMethodArgs, 0);
	}

	private void checkSenderAndContinue(Method parent, String[] args, CommandSender player, Object[] methodArgs, int level) throws CommandException {
		Map<String, Method> map = commands.get(parent);
		Method method = map.get(args[level].toLowerCase());

		if(method.isAnnotationPresent(Sender.class)) {
			Sender senderAnnot = method.getAnnotation(Sender.class);
			List<Class<? extends CommandSender>> allowedSenders = Arrays.asList(senderAnnot.value());
			if(!allowedSenders.contains(player.getClass())) {
				throw new CommandSenderTypeException(allowedSenders);
			}
		}

		executeMethod(parent, args, player, methodArgs, level);

	}

	public void addCommand(Class<?> cmd) {
		register.register(cmd);
	}

	@Override
	public boolean hasPermission(CommandSender sender, String perm) {
		return sender.hasPermission(perm);
	}
}
