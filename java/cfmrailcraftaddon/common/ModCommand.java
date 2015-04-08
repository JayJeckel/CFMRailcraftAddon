package cfmrailcraftaddon.common;


import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.StatCollector;

public abstract class ModCommand extends CommandBase
{
	public ModCommand(final String commandName, final String usageText)
	{
		this(commandName, 0, usageText);
	}

	public ModCommand(final String commandName, int permLevel, final String usageText)
	{
		this._aliases = new ArrayList<String>();
		this._aliases.add(commandName);
		this._permLevel = permLevel;
		this._usageText = usageText;
	}

	private final List<String> _aliases;

	private final int _permLevel;

	private final String _usageText;

	@Override public String getCommandName() { return this._aliases.get(0); }

	@SuppressWarnings("rawtypes")
	@Override public List getCommandAliases() { return this._aliases; }

	@Override public String getCommandUsage(ICommandSender sender) { return "/" + this._aliases.get(0) + " - " + StatCollector.translateToLocal(this._usageText); }

	@Override public int getRequiredPermissionLevel() { return this._permLevel; }

	@Override public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return this.getRequiredPermissionLevel() == 0 || super.canCommandSenderUseCommand(sender);
	}

	@Override public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length == 0) { this.doCommand(sender); }
		else { throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]); }
	}

	protected abstract void doCommand(ICommandSender sender);
}
