package com.suicidesquid.tuffores.datagen;

import com.suicidesquid.tuffores.TuffOres;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = TuffOres.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            
            generator.addProvider(new TuffOresLootTables(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new TuffOresBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new TuffOresItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(new TuffOresLanguageProvider(generator, "en_us"));
        }
    }
}