package cfmrailcraftaddon.core;

import net.minecraftforge.common.config.Configuration;
import cfmrailcraftaddon.common.configs.ConfigHandlerValues;

public class ConfigValues extends ConfigHandlerValues
{
	private static final long serialVersionUID = -2368469394837807847L;

	public ConfigValues()
	{
		this.add(this._updateChecking);
	}

	public boolean isUpdateCheckingEnabled() { return this._updateChecking.getValue(); }
	protected final ConfigValueBoolean _updateChecking = new ConfigValueBoolean("EnableUpdateChecking", Configuration.CATEGORY_GENERAL,
			"Control automatic update checking.\n.Setting this option to false will disable version checking.",
			true);

	@Override public void update(final Configuration config)
	{
		super.update(config);

		Refs.getUpdateChecker().enable(this.isUpdateCheckingEnabled());
	}
}
