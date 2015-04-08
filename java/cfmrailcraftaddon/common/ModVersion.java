package cfmrailcraftaddon.common;


import java.util.regex.Pattern;

public class ModVersion implements Comparable<ModVersion>
{
	public static ModVersion fromString(final String version)
	{
		if (version.equals("${version}")) { return new ModVersion(0, 0, 0, 0, "dev"); }

		String label = "";
		String ver = version;
		if (version.contains("-"))
		{
			final String[] secs = version.split("-");
			label = secs[1];
			ver = secs[0];
		}

		final String[] parts = ver.split(Pattern.quote("."));
		return new ModVersion(Integer.parseInt(parts[0]),
			(parts.length > 1 ? Integer.parseInt(parts[1]) : 0),
			(parts.length > 2 ? Integer.parseInt(parts[2]) : 0),
			(parts.length > 3 ? Integer.parseInt(parts[3]) : 0),
			label);
	}

	public static int getLabelValue(final String label)
	{
		switch (label)
		{
			case "": { return 4; }
			case "universal": { return 4; }
			case "beta": { return 3; }
			case "alpha": { return 2; }
			case "dev": { return 1; }
			default: { return 0; }
		}
	}

	public ModVersion(final int major, final int minor, final int revision)
	{
		this(major, minor, revision, 0, "");
	}

	public ModVersion(final int major, final int minor, final int revision, final int build)
	{
		this(major, minor, revision, build, "");
	}

	public ModVersion(final int major, final int minor, final int revision, final String label)
	{
		this(major, minor, revision, 0, label);
	}

	public ModVersion(final int major, final int minor, final int revision, final int build, final String label)
	{
		this.major = major;
		this.minor = minor;
		this.revision = revision;
		this.build = build;
		this.label = label;
	}

	public final int major;

	public final int minor;

	public final int revision;

	public final int build;

	public final String label;

	public String toString(boolean includeBuild)
	{
		if (includeBuild) { return this.toString(); }
		if (this.label.length() == 0) { return String.format("%d.%d.%d", this.major, this.minor, this.revision); }
		else { return String.format("%d.%d.%d-%s", this.major, this.minor, this.revision, this.label); }
	}

	@Override public String toString()
	{
		if (this.label.length() == 0) { return String.format("%d.%d.%d.%d", this.major, this.minor, this.revision, this.build); }
		else { return String.format("%d.%d.%d.%d-%s", this.major, this.minor, this.revision, this.build, this.label); }
	}

	@Override public boolean equals(Object obj)
	{
		return 0 == this.compareTo((ModVersion)obj);
	}

	@Override public int compareTo(ModVersion o)
	{
		if (this.major > o.major) { return 1; }
		else if (this.major < o.major) { return -1; }
		else if (this.minor > o.minor) { return 1; }
		else if (this.minor < o.minor) { return -1; }
		else if (this.revision > o.revision) { return 1; }
		else if (this.revision < o.revision) { return -1; }
		else if (this.build > o.build) { return 1; }
		else if (this.build < o.build) { return -1; }
		else
		{
			final int thisLabelValue = getLabelValue(this.label);
			final int thatLabelValue = getLabelValue(o.label);
			if (thisLabelValue > thatLabelValue) { return 1; }
			else if (thisLabelValue < thatLabelValue) { return -1; }
			else { return 0; }
		}
	}
}
