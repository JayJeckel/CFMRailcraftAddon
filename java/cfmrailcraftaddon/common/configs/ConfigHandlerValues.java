package cfmrailcraftaddon.common.configs;

import java.util.ArrayList;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandlerValues extends ArrayList<ConfigHandlerValues.IConfigValue>
{
	private static final long serialVersionUID = -9120928678147846343L;

	public ConfigHandlerValues() { }

	public ConfigHandlerValues(IConfigValue... values)
	{
		for (int index = 0; index < values.length; index++)
		{
			this.add(values[index]);
		}
	}

	public void update(final Configuration config) { for (IConfigValue value : this) { value.update(config); } }

	public boolean getBoolean(String key) { return ((ConfigValueBoolean) this.getValue(key)).getValue(); }

	public float getFloat(String key) { return ((ConfigValueFloat) this.getValue(key)).getValue(); }

	public int getInt(String key) { return ((ConfigValueInt) this.getValue(key)).getValue(); }

	public String getString(String key) { return ((ConfigValueString) this.getValue(key)).getValue(); }

	protected IConfigValue getValue(String key)
	{
		for (IConfigValue value : this) { if (value.getKey().equalsIgnoreCase(key)) {  return value; } }
		return null;
	}


	public static abstract interface IConfigValue
	{
		public String getKey();

		public String getCategory();

		public String getComment();

		public void update(Configuration config);
	}

	public static abstract class AConfigValue<T> implements IConfigValue
	{
		public AConfigValue(final String key, final String category, final String comment, final T defaultValue)
		{
			this._key = key;
			this._category = category;
			this._comment = comment;
			this._default = defaultValue;
			this._value = defaultValue;
		}

		public String getKey() { return this._key; }
		protected final String _key;

		public String getCategory() { return this._category; }
		protected final String _category;

		public String getComment() { return this._comment; }
		protected final String _comment;

		public T getDefault() { return this._default; }
		protected final T _default;

		public T getValue() { return this._value; }
		protected T _value;

		public abstract void update(Configuration config);
	}

	public static class ConfigValueBoolean extends AConfigValue<Boolean>
	{
		public ConfigValueBoolean(final String key, final String category, final String comment, final boolean defaultValue)
		{
			super(key, category, comment, defaultValue);
		}

		public void update(final Configuration config)
		{
			this._value = config.getBoolean(this.getKey(), this.getCategory(), this.getDefault(), this.getComment() + "\n");
		}
	}

	public static class ConfigValueFloat extends AConfigValue<Float>
	{
		public ConfigValueFloat(final String key, final String category, final String comment,
				final float defaultValue, final float min, final float max)
		{
			super(key, category, comment, defaultValue);
			this.min = min;
			this.max = max;
		}

		public final float min;
		public final float max;

		public void update(final Configuration config)
		{
			this._value = config.getFloat(this.getKey(), this.getCategory(), this.getDefault(), this.min, this.max, this.getComment() + "\n");
		}
	}

	public static class ConfigValueInt extends AConfigValue<Integer>
	{
		public ConfigValueInt(final String key, final String category, final String comment,
				final int defaultValue, final int min, final int max)
		{
			super(key, category, comment, defaultValue);
			this.min = min;
			this.max = max;
		}

		public final int min;
		public final int max;

		public void update(final Configuration config)
		{
			this._value = config.getInt(this.getKey(), this.getCategory(), this.getDefault(), this.min, this.max, this.getComment() + "\n");
		}
	}

	public static class ConfigValueString extends AConfigValue<String>
	{
		public ConfigValueString(final String key, final String category, final String comment, final String defaultValue)
		{
			super(key, category, comment, defaultValue);
		}

		public void update(final Configuration config)
		{
			this._value = config.getString(this.getKey(), this.getCategory(), this.getDefault(), this.getComment() + "\n");
		}
	}
}
