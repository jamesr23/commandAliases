package com.jamesr.commandaliases.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {

	public static String prefix;
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static void printMessage(String message) {
		if (mc.thePlayer != null)
			mc.thePlayer.addChatMessage(new ChatComponentText(prefix + message));
	}

	public static void sendMessage(String message) {
		mc.thePlayer.sendChatMessage(message);
	}
}
