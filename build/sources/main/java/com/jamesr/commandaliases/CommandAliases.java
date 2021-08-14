package com.jamesr.commandaliases;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jamesr.commandaliases.commands.AliasCommand;
import com.jamesr.commandaliases.commands.CommandAliasesCommand;
import com.jamesr.commandaliases.config.ConfigManager;
import com.jamesr.commandaliases.utils.ChatUtils;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//  yeah there is no way this is going to work out.
//  the closest event i can find is CommandEvent which is fired AFTER the command is parsed
//  I need to look through the game and find when exactly the command is parsed
//  or i need to register all the commands beforehand
//  wait i can do that

@Mod(modid = CommandAliases.MODID, version = CommandAliases.VERSION, clientSideOnly = true)
public class CommandAliases {
	
    public static final String MODID = "commandaliases";
    public static final String VERSION = "1.0";
    
    // could've done a map and linked it to the ICommmand or the command with arguments but
    // that can just be accessed with ClientCommandHandler's commandMap
    public static Map<String, AliasCommand> aliases = new HashMap<String, AliasCommand>();
    
    public CommandAliases() {}
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	
    	ChatUtils.prefix = "[CommandAliases] ";
    	ClientCommandHandler.instance.registerCommand(new CommandAliasesCommand());
    	for (Entry<String, String> entry : ConfigManager.getAliases().entrySet())
    		addAlias(entry.getKey(), entry.getValue());
    	
    }
    
    public static void addAlias(String alias, String command) {
		
    	// do some checks to see if it clashes with client side commands
    	// dont know how to check for server side
    	// everything in this.aliasese should be in the commandmap
    	if (ClientCommandHandler.instance.getCommands().containsKey(alias)) {
    		ChatUtils.printMessage(alias + " is a registered command or alias");
    		return;
    	}
    	AliasCommand aliascmd = new AliasCommand(alias, command);
    	aliases.put(alias, aliascmd);
		ClientCommandHandler.instance.registerCommand(aliascmd);
		ConfigManager.writeStringConfig(aliascmd.alias, aliascmd.command);
		ChatUtils.printMessage("created alias /" + aliascmd.alias + " -> " + aliascmd.command);
    }
    
    public static void removeAlias(String alias) {
    	
    	// returns null if contained and removed
    	AliasCommand aliascmd = aliases.remove(alias);
    	if (aliascmd != null) {
    		ClientCommandHandler.instance.getCommands().remove(alias);
    		ConfigManager.removeStringConfig(alias);
    		ChatUtils.printMessage("removed alias /" + aliascmd.alias + " -> " + aliascmd.command);
    	} else {
    		ChatUtils.printMessage(alias + " is not a registered alias");
    	}
    	return;
    }
}
