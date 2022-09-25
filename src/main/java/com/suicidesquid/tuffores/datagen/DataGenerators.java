package com.suicidesquid.tuffores.datagen;

import com.suicidesquid.tuffores.TuffOres;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TuffOres.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
            generator.addProvider(event.includeServer(),new TuffOresLootTables(generator));
        if (event.includeClient()) {
            generator.addProvider(event.includeClient(), new TuffOresBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(event.includeClient(), new TuffOresItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(event.includeClient(), new TuffOresLanguageProvider(generator, "en_us"));
        }
    }
}