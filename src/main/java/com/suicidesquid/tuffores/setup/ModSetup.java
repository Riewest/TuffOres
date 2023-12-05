package com.suicidesquid.tuffores.setup;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class ModSetup {
    public static final String TAB_NAME = "tuffores";

    public static void init(final FMLCommonSetupEvent event) {
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
      // Add to ingredients tab
      if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
        for (RegistryObject<Item> regItem : Registration.BLOCK_ITEMS.getEntries()) {
            ItemLike tuffItem = regItem.get();
            event.accept(tuffItem);
            event.accept(tuffItem); // Takes in an ItemLike, assumes block has registered item
        }        
      }
      if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
        event.accept(Registration.TUFF_ORES_MODULE);
      }
    }
}
