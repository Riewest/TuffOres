package com.suicidesquid.tuffores.setup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {
    public static final String TAB_NAME = "tuffores";

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.TUFF_DIAMOND_ORE.get());
        }
    };


    public static void init(final FMLCommonSetupEvent event) {
    }
}
