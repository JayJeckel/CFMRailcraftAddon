package cfmrailcraftaddon.common.configs;

import java.io.File;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cfmrailcraftaddon.core.Refs;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler
{
	public ConfigHandler(final String modId, final String modName, ConfigHandlerValues values)
	{
		this.ModId = modId;
		this.ModName = modName;
		this.Values = values;
	}

	public final String ModId;
	public final String ModName;
	public final ConfigHandlerValues Values;

	public Configuration getConfig() { return this._config; }
	private Configuration _config;

	public void initialize(FMLPreInitializationEvent event)
	{
		this._config = new Configuration(new File(event.getModConfigurationDirectory(), this.ModName + ".cfg"));
		this.syncConfig();
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if (eventArgs.modID.equals(this.ModId)) { this.syncConfig(); }
	}

	public void syncConfig()
	{
		this.Values.update(this.getConfig());

		if(this._config.hasChanged()) { this._config.save(); }
	}


	/**
	 * Default mod config gui screen.
	 */
	public static class Screen extends GuiConfig
	{
		public Screen(GuiScreen parent)
		{
			super(parent, new ConfigElement<Object>(Refs.getConfigHandler().getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
					Refs.ModId,
					false, false,
					Refs.ModName + " " + "Configuration");
		}
	}


	/**
	 * Default mod gui factory.
	 */
	public static class Factory implements IModGuiFactory
	{
		@Override public void initialize(Minecraft minecraftInstance) { }

		@Override public Class<? extends GuiScreen> mainConfigGuiClass() { return ConfigHandler.Screen.class; }

		@Override public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() { return null; }

		@Override public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) { return null; }
	}
}
