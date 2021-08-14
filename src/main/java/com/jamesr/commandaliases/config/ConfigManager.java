package com.jamesr.commandaliases.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigManager {
	
	private static Configuration config;
	private static final String CONFIG_FILE = "config/commandaliases.cfg";
	private static final String CATEGORY = "commandaliases";
	
	// just a small class to do minimal config management for this mod
	
	public static Map<String, String> getAliases() {
		
		refreshConfigObject();
		Map<String, String> ret = new HashMap<String, String>();
		
		try {
			config.load();
			
			if (config.hasCategory(CATEGORY)) {
				
				Map<String, Property> map = config.getCategory(CATEGORY).getValues();
				for (Entry<String, Property> s : map.entrySet())
					ret.put(s.getKey(), s.getValue().getString());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			config.save();
		}
		return new HashMap<String, String>();
	}
	
	public static void writeStringConfig(String key, String value) {
		refreshConfigObject();
		try {
			config.load();
			if (config.hasCategory(CATEGORY)) {
				// get once to "create" the key
				config.get(CATEGORY, key, value);
				config.getCategory(CATEGORY).get(key).set(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			config.save();
		}
	}
	
	public static void removeStringConfig(String key) {
		refreshConfigObject();
		try {
			config.load();
			if (config.hasCategory(CATEGORY)) {
				config.getCategory(CATEGORY).remove(key);
			}
		} catch (Exception e) {
			
		} finally {
			config.save();
		}
	}
	
	private static void refreshConfigObject() {
		config = new Configuration(new File(CONFIG_FILE));
		// "touch" the category
		if (!config.hasCategory(CATEGORY))
			config.getCategory(CATEGORY);
	}
}
