package com.suicidesquid.tuffores.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

public class DataGeneration {

    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));
        TuffOresBlockTags blockTags = new TuffOresBlockTags(packOutput, lookupProvider, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeClient(), new TuffOresBlockStates(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new TuffOresLanguageProvider(packOutput, "en_us"));
        generator.addProvider(event.includeClient(), new TuffOresItemModels(packOutput, event.getExistingFileHelper()));
        
    }
}