package com.jamesr.commandaliases.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.jamesr.commandaliases.CommandAliases;
import com.jamesr.commandaliases.utils.ChatUtils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class CommandAliasesCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "alias";
	}
	
    public int getRequiredPermissionLevel() {
        return 0;
    }
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "add", "remove", "list");
		} else if (args.length == 2) {
			if (args[0].equals("remove"))
				return getListOfStringsMatchingLastWord(args, CommandAliases.aliases.keySet());
		}
		return null;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Command Aliases usage:\n"
				+ "/alias add [YOUR ALIAS] [COMMAND TO BE RUN]\n"
				+ "/alias remove [YOUR ALIAS]\n"
				+ "/alias list";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
		if (args.length < 1 || !(args[0].equals("list") || args[0].equals("remove") || args[0].equals("add"))) {
			ChatUtils.printMessage(getCommandUsage(sender));
			return;
		}
		
		if (args[0].equals("list")) {

			ChatUtils.printMessage("Current Aliases:");
			ChatUtils.prefix = "";
			for (Entry<String, AliasCommand> entry : CommandAliases.aliases.entrySet())
				ChatUtils.printMessage("/" + entry.getValue().alias + " -> " + entry.getValue().command);
//			CommandAliases.aliases.forEach(alias -> ChatUtils.printMessage("/" + alias.alias + " -> " + alias.command + "\n"));
			ChatUtils.prefix = "[CommandAliases] ";
			
		} else if (args[0].equals("add")) {
			
			if (args.length < 3) {
				ChatUtils.printMessage(getCommandUsage(sender));
				return;
			}
			
			String alias = args[1];
			
			if (alias.startsWith("/"))
				alias = alias.substring(1);
			
			CommandAliases.addAlias(alias, String.join(" ", Arrays.copyOfRange(args, 2, args.length)));

		} else if (args[0].equals("remove")) {
			
			if (args.length != 2) {
				ChatUtils.printMessage(getCommandUsage(sender));
				return;
			}
			
			CommandAliases.removeAlias(args[1]);

		}
			
	}

}
