package cfmrailcraftaddon.common;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.ModMetadata;

public class InfoModCommand extends ModCommand
{
	public InfoModCommand(final ModMetadata modMetadata, final UpdateChecker checker, final String usageText)
	{
		super(modMetadata.modId, usageText);
		this.modMetadata = modMetadata;
		this.checker = checker;
	}

	public InfoModCommand(final ModMetadata modMetadata, final UpdateChecker checker, int permLevel, final String usageText)
	{
		super(modMetadata.modId, permLevel, usageText);
		this.modMetadata = modMetadata;
		this.checker = checker;
	}

	public final ModMetadata modMetadata;

	public final UpdateChecker checker;

	@Override public void doCommand(ICommandSender sender)
	{
		String text;

		addMessage(sender, EnumChatFormatting.BOLD + "+++++ Mod Info +++++");

		addMessage(sender, "Mod Name: " + this.modMetadata.name);

		addMessage(sender, "Mod Id: " + this.modMetadata.modId);

		addMessage(sender, "Version: " + this.modMetadata.version);

		text = "Update Checker: ";
		if (!this.checker.isEnabled()) { text += "Disabled"; }
		else
		{
			text += "Enabled (";
			if (!this.checker.isChecked()) { text += "Unchecked"; }
			else
			{
				if (this.checker.isUpdatable()) { text += "Updatable"; }
				else { text += "Current"; }
				text += " [" + this.checker.getVersionRemote() + "]";
			}
			text += ")";
		}
		addMessage(sender, text);

		addMessage(sender, "--------------------");
	}

	private static void addMessage(ICommandSender sender, String text)
	{
		sender.addChatMessage(new ChatComponentText(text));
	}
}
