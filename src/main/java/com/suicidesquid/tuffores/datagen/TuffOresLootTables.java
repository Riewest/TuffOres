package com.suicidesquid.tuffores.datagen;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.suicidesquid.tuffores.blocks.TuffOre;
import com.suicidesquid.tuffores.setup.Registration;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class TuffOresLootTables extends LootTableProvider {
    public TuffOresLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(BlockLootTables::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((p_218436_2_, p_218436_3_) -> LootTables.validate(validationtracker, p_218436_2_, p_218436_3_));
    }

    private static final class BlockLootTables extends net.minecraft.data.loot.BlockLoot {

        private static final float MIN_TARGET_RAW_ORE = Registration.TARGET_RAW_ORE_PER_BLOCK - (0.1f * Registration.TARGET_RAW_ORE_PER_BLOCK);
        private static final float MAX_TARGET_RAW_ORE = Registration.TARGET_RAW_ORE_PER_BLOCK + (0.1f * Registration.TARGET_RAW_ORE_PER_BLOCK);

        private void tuffOreBuilder(Block oreBlock, ItemLike rawOreItem){
            
            LootTable.Builder lootBuilder = LootTable.lootTable();
            
            int stackSize = rawOreItem.asItem().MAX_STACK_SIZE;
            int fullStacks = (int) MIN_TARGET_RAW_ORE / stackSize;
            int partialStack = (int) MIN_TARGET_RAW_ORE % stackSize;

            for(int i = 0; i < fullStacks; i++){
                lootBuilder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(rawOreItem, LootItem.lootTableItem(rawOreItem).apply(SetItemCountFunction.setCount(ConstantValue.exactly(stackSize))))));
            }
            if (partialStack >= 1){
                lootBuilder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(rawOreItem, LootItem.lootTableItem(rawOreItem).apply(SetItemCountFunction.setCount(ConstantValue.exactly(partialStack))))));
            }
            
            lootBuilder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(rawOreItem, LootItem.lootTableItem(rawOreItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, MAX_TARGET_RAW_ORE-MIN_TARGET_RAW_ORE))))));
            add(oreBlock, lootBuilder);
        }


        @Override
        protected void addTables() {
            for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
                TuffOre block = (TuffOre) regBlock.get();
                tuffOreBuilder(regBlock.get(), block.dropItem);
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
        }
    }
}
