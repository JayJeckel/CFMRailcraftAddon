package cfmrailcraftaddon.compatibility;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cfmrailcraftaddon.core.Refs;

import com.mrcrayfish.furniture.api.IRecipeRegistry;
import com.mrcrayfish.furniture.api.RecipeVariables;

import cpw.mods.fml.common.event.FMLInterModComms;

public class CFMRecipeRegistry
{
	public static void send()
	{
		FMLInterModComms.sendMessage("cfm", "register",
		Refs.ModId + ".compatibility.CFMRecipeRegistry.initialize");
	}

	public static void initialize(IRecipeRegistry registry)
	{
		register(registry, "washingmachine", "Railcraft:armor.goggles");
		register(registry, "washingmachine", "Railcraft:armor.overalls");

		register(registry, "washingmachine", "Railcraft:armor.steel.helmet");
		register(registry, "washingmachine", "Railcraft:armor.steel.plate");
		register(registry, "washingmachine", "Railcraft:armor.steel.legs");
		register(registry, "washingmachine", "Railcraft:armor.steel.boots");

		register(registry, "dishwasher", "Railcraft:tool.steel.hoe");
		register(registry, "dishwasher", "Railcraft:tool.steel.axe");
		register(registry, "dishwasher", "Railcraft:tool.steel.shovel");
		register(registry, "dishwasher", "Railcraft:tool.steel.pickaxe");
		register(registry, "dishwasher", "Railcraft:tool.steel.sword");
	}

	private static void register(final IRecipeRegistry registry, final String key, final String itemName)
	{
		if (Item.itemRegistry.containsKey(itemName))
		{
			final Item item = (Item) Item.itemRegistry.getObject(itemName);
			RecipeVariables var = new RecipeVariables();
			var.addValue("input", new ItemStack(item));
			registry.registerRecipe(key, var);
		}
	}
}
