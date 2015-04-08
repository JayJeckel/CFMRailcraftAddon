package cfmrailcraftaddon.core;

import net.minecraft.server.MinecraftServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cfmrailcraftaddon.CFMRailcraftAddon;
import cfmrailcraftaddon.common.UpdateChecker;
import cfmrailcraftaddon.common.configs.ConfigHandler;
import cfmrailcraftaddon.compatibility.CFMRecipeRegistry;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Refs
{
	public static final String ModId = "cfmrailcraftaddon";
	public static final String ModName = "CFMRailcraftAddon";

	public static final String ConfigFactoryTypeName = Refs.ModId + ".common.configs.ConfigHandler$Factory";

	public static Logger getLogger() { return _logger; }
	private static Logger _logger;

	public static ModMetadata getMetadata() { return _metadata; }
	private static ModMetadata _metadata;

	public static ConfigHandler getConfigHandler() { return _configHandler; }
	private static ConfigHandler _configHandler;

	public static ConfigValues getConfigValues() { return _configValues; }
	private static ConfigValues _configValues;

	public static UpdateChecker getUpdateChecker() { return _updateChecker; }
	private static UpdateChecker _updateChecker;

	public static void pre(FMLPreInitializationEvent event)
	{
		_logger = LogManager.getLogger(Refs.ModName);
		_metadata = event.getModMetadata();

		_configValues = new ConfigValues();
		_configHandler = new ConfigHandler(Refs.ModId, Refs.ModName, Refs.getConfigValues());
		_updateChecker = new UpdateChecker(Refs.ModName, Refs.getMetadata().version, Refs.getLogger());

		_configHandler.initialize(event);
		_updateChecker.initialize(event);
	}

	public static void initialize(FMLInitializationEvent event)
	{
		if (CFMRailcraftAddon.CfmMod != null) { getLogger().info("Addon Disabled: Mr Crayfish Furniture Mod is not found."); }
		else if (CFMRailcraftAddon.RailcraftMod != null) { getLogger().info("Addon Disabled: Railcraft is not found."); }
		else { CFMRecipeRegistry.send(); }
	}

	public static void post(FMLPostInitializationEvent event)
	{
	}

	public static boolean isSinglePlayer() { return MinecraftServer.getServer().isSinglePlayer(); }

	public static boolean isMultiPlayer() { return MinecraftServer.getServer().isDedicatedServer(); }
}
