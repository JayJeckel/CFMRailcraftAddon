package cfmrailcraftaddon.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;

import cfmrailcraftaddon.core.Refs;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class UpdateChecker implements Runnable
{
	public UpdateChecker(final String modName, final String version, final Logger logger)
	{
		this.ModName = modName;
		this.Logger = logger;

		this._verLocal = ModVersion.fromString(version);
	}

	public final String ModName;
	public final Logger Logger;

	public ModVersion getVersionLocal() { return this._verLocal; }
	private ModVersion _verLocal = null;

	public ModVersion getVersionRemote() { return this._verRemote; }
	private ModVersion _verRemote = null;

	public boolean isUpdatable() { return this._updatable; }
	private boolean _updatable = false;

	public boolean isChecked() { return this._checked; }
	private boolean _checked = false;

	public boolean isEnabled() { return this._enabled; }
	public void enable(boolean enabled) { if (enabled) { this.enable(); } else { this.disable(); } }
	public void enable()
	{
		if (this.isEnabled()) { return; }
		this._enabled = true;
		this.check();
	}
	public void disable()
	{
		if (!this.isEnabled()) { return; }
		this._enabled = false;
		this._checked = false;
	}
	private boolean _enabled = false;

	public void initialize(FMLPreInitializationEvent event)
	{
		if (!this.isEnabled()) { this.Logger.info("Update Checking Disabled: Skipping"); }
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void check()
	{
		Thread thread = new Thread(this);
		thread.start();
	}

	public boolean canAnnounce(EntityPlayer player) { return this.isUpdatable() && (Refs.isSinglePlayer() || player.canCommandSenderUseCommand(2, "")); }

	public void announce(EntityPlayer player)
	{
		String text;
		text = String.format("[%s v%s] Update Available: %s", this.ModName, this.getVersionLocal(), this.getVersionRemote());
		player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + text));
	}

	@SubscribeEvent public void onPlayerJoin(EntityJoinWorldEvent event)
	{
		if (!(event.entity instanceof EntityPlayer)) { return; }
		if (event.world.isRemote) { return; }
		if (!this.isEnabled()) { return; }

		final EntityPlayer player = (EntityPlayer)event.entity;
		if (this.canAnnounce(player)) { this.announce(player); }
	}

	@Override public void run()
	{
		final String url = "http://jeckelland.site88.net/minecraft/mods/" + this.ModName + "/" + "version_latest.txt";
		String text = "Checking For Updates: ";
		try
		{
			final InputStream input = new URL(url).openStream();
			try
			{
				this._verRemote = ModVersion.fromString(IOUtils.toString(input));
				this._updatable = this.getVersionRemote().compareTo(this.getVersionLocal()) > 0;
				text += String.format("Local/Remote Version: %s/%s (%s)", this.getVersionLocal(), this.getVersionRemote(), (this.isUpdatable() ? "Updatable" : "Current"));
			}
			catch (IOException e)
			{
				this._updatable = false;
				text += "Remote File Read Failed";
			}
			finally
			{
				IOUtils.closeQuietly(input);
			}
		}
		catch (IOException e)
		{
			this._updatable = false;
			text += "Remote File Read Failed";
		}
		this._checked = true;
		this.Logger.info(text);
	}
}
