package com.sjkz1.sjkz1misc.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sjkz1.sjkz1misc.SJKZ1Misc;

import net.fabricmc.loader.api.FabricLoader;

public abstract  class ConfigHandler {

	  protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	    protected File configFile;

	    public ConfigHandler(String name)
	    {
	        this.configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), name + ".json");

	        try
	        {
	            this.loadConfig();
	        }
	        catch (IOException e)
	        {
	            SJKZ1Misc.LOGGER.error("Failed to load config, using default.", e);
	        }
	    }

	    public File getConfigFile()
	    {
	        return this.configFile;
	    }

	    public static String readFile(String path, Charset encoding) throws IOException
	    {
	        byte[] encoded = Files.readAllBytes(Paths.get(path));
	        return new String(encoded, encoding);
	    }

	    public abstract void loadConfig() throws IOException;

	    public abstract void saveConfig() throws IOException;
}
