package com.suicidesquid.tuffores.datagen;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.suicidesquid.tuffores.TuffOres;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TuffOres.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            // generator.addProvider(event.includeServer(),new TuffOresLootTables(generator));
            generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
            List.of(new LootTableProvider.SubProviderEntry(TuffOresLootTables::new, LootContextParamSets.BLOCK))));
        if (event.includeClient()) {
            generator.addProvider(event.includeClient(), new TuffOresBlockStates(packOutput, event.getExistingFileHelper()));
            generator.addProvider(event.includeClient(), new TuffOresItemModels(packOutput, event.getExistingFileHelper()));
            generator.addProvider(event.includeClient(), new TuffOresLanguageProvider(packOutput, "en_us"));
        }
    }
}