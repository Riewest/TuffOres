package com.suicidesquid.tuffores.datagen;

import java.util.Set;
import com.suicidesquid.tuffores.blocks.TuffOre;
import com.suicidesquid.tuffores.setup.Registration;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
// import net.minecraft.world.level.storage.loot.providers.number.

public class TuffOresLootTables extends BlockLootSubProvider  {
    private static final float MIN_TARGET_RAW_ORE = Registration.TARGET_RAW_ORE_PER_BLOCK - (0.1f * Registration.TARGET_RAW_ORE_PER_BLOCK);
    private static final float MAX_TARGET_RAW_ORE = Registration.TARGET_RAW_ORE_PER_BLOCK + (0.1f * Registration.TARGET_RAW_ORE_PER_BLOCK);


    public TuffOresLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
            TuffOre tuffBlock = (TuffOre) regBlock.get();
            LootPool.Builder builder = LootPool.lootPool()
                .setRolls(UniformGenerator.between(MIN_TARGET_RAW_ORE, MAX_TARGET_RAW_ORE))
                .add(LootItem.lootTableItem(tuffBlock.dropItem).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
            add(tuffBlock, LootTable.lootTable().withPool(builder));
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
