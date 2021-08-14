package com.jamesr.commandaliases.commands;


import com.jamesr.commandaliases.utils.ChatUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class AliasCommand extends CommandBase {

	public String alias;
	public String command;
	
	public AliasCommand(String alias, String command) {
		this.alias = alias;
		this.command = command; // has the arguments
	}
	
	@Override
	public String getCommandName() {
		return alias;
	}

	@Override
	public int getRequiredPermissionLevel() {return 0;}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		// have to send the command, to be able to use non-clientside only commands
		// i mean the space doesnt matter but i dont know if there is a method to join with a starting string
		if (args.length > 0)
			ChatUtils.sendMessage(command + " " + String.join(" ", args));
		else
			ChatUtils.sendMessage(command);
		
	}

}
