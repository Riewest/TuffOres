package com.suicidesquid.tuffores.datagen;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.suicidesquid.tuffores.blocks.TuffOre;
import com.suicidesquid.tuffores.setup.Registration;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class TuffOresLootTables implements LootTableSubProvider  {
    private static final float MIN_TARGET_RAW_ORE = Registration.TARGET_RAW_ORE_PER_BLOCK - (0.1f * Registration.TARGET_RAW_ORE_PER_BLOCK);
    private static final float MAX_TARGET_RAW_ORE = Registration.TARGET_RAW_ORE_PER_BLOCK + (0.1f * Registration.TARGET_RAW_ORE_PER_BLOCK);


    public static LootTable.Builder createSilkTouchTable(String name, Block block, ItemLike lootItem) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantValue.exactly(1))
                .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(block)
                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))),
                                LootItem.lootTableItem(lootItem)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_TARGET_RAW_ORE, MAX_TARGET_RAW_ORE)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1))
                                        .apply(ApplyExplosionDecay.explosionDecay())
                        )
                );
        return LootTable.lootTable().withPool(builder);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {



        for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
            TuffOre block = (TuffOre) regBlock.get();
            int stackSize = block.dropItem.asItem().MAX_STACK_SIZE;
            int fullStacks = (int) MIN_TARGET_RAW_ORE / stackSize;
            int partialStack = (int) MIN_TARGET_RAW_ORE % stackSize;

            builder.accept(regBlock.getId(), createSilkTouchTable(regBlock.get().getName().toString(), regBlock.get(), block.dropItem));
            // tuffOreBuilder(regBlock.get(), block.dropItem);
        }


        // builder.accept(Registration.GENERATOR.getId(), LootTableTools.createStandardTable("generator", Registration.GENERATOR.get(), Registration.GENERATOR_BE.get()));
        // builder.accept(Registration.POWERGEN.getId(), LootTableTools.createStandardTable("powergen", Registration.POWERGEN.get(), Registration.POWERGEN_BE.get()));
        // builder.accept(Registration.MYSTERIOUS_ORE_OVERWORLD.getId(), LootTableTools.createSilkTouchTable("mysterious_ore_overworld", Registration.MYSTERIOUS_ORE_OVERWORLD.get(), Registration.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
        // builder.accept(Registration.MYSTERIOUS_ORE_NETHER.getId(), LootTableTools.createSilkTouchTable("mysterious_ore_nether", Registration.MYSTERIOUS_ORE_NETHER.get(), Registration.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
        // builder.accept(Registration.MYSTERIOUS_ORE_END.getId(), LootTableTools.createSilkTouchTable("mysterious_ore_end", Registration.MYSTERIOUS_ORE_END.get(), Registration.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
        // builder.accept(Registration.MYSTERIOUS_ORE_DEEPSLATE.getId(), LootTableTools.createSilkTouchTable("mysterious_ore_deepslate", Registration.MYSTERIOUS_ORE_DEEPSLATE.get(), Registration.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
    }

    // @Override
    // public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
    //     Map<Block, Function<Block, LootTable.Builder>> builders = new IdentityHashMap<>();

    //     for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
    //         TuffOre block = (TuffOre) regBlock.get();
    //         int stackSize = block.dropItem.asItem().MAX_STACK_SIZE;
    //         int fullStacks = (int) MIN_TARGET_RAW_ORE / stackSize;
    //         int partialStack = (int) MIN_TARGET_RAW_ORE % stackSize;

    //         for(int i = 0; i < fullStacks; i++){
    //             builders.put(block, tuffOre(stackSize, block.dropItem));
    //         }
    //         if (partialStack >= 1){
    //             builders.put(block, tuffOre(partialStack, block.dropItem));
    //         }
    //         // tuffOreBuilder(regBlock.get(), block.dropItem);
    //     }

    //     for (var entry : builders.entrySet()) {
    //         biConsumer.accept(entry.getKey().getLootTable(), entry.getValue().apply(entry.getKey()));
    //     }
    // }

    // @Override
    // protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
    //     return ImmutableList.of(
    //             Pair.of(BlockLootTables::new, LootContextParamSets.BLOCK)
    //     );
    // }

    // @Override
    // protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
    //     map.forEach((p_218436_2_, p_218436_3_) -> LootTables.validate(validationtracker, p_218436_2_, p_218436_3_));
    // }

    // private static final class BlockLootTables extends net.minecraft.data.loot.BlockLoot {

    //     private static final float MIN_TARGET_RAW_ORE = Registration.TARGET_RAW_ORE_PER_BLOCK - (0.1f * Registration.TARGET_RAW_ORE_PER_BLOCK);
    //     private static final float MAX_TARGET_RAW_ORE = Registration.TARGET_RAW_ORE_PER_BLOCK + (0.1f * Registration.TARGET_RAW_ORE_PER_BLOCK);

    //     private void tuffOreBuilder(Block oreBlock, ItemLike rawOreItem){
            
    //         LootTable.Builder lootBuilder = LootTable.lootTable();
            
    //         int stackSize = rawOreItem.asItem().MAX_STACK_SIZE;
    //         int fullStacks = (int) MIN_TARGET_RAW_ORE / stackSize;
    //         int partialStack = (int) MIN_TARGET_RAW_ORE % stackSize;

    //         for(int i = 0; i < fullStacks; i++){
    //             lootBuilder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(rawOreItem, LootItem.lootTableItem(rawOreItem).apply(SetItemCountFunction.setCount(ConstantValue.exactly(stackSize))))));
    //         }
    //         if (partialStack >= 1){
    //             lootBuilder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(rawOreItem, LootItem.lootTableItem(rawOreItem).apply(SetItemCountFunction.setCount(ConstantValue.exactly(partialStack))))));
    //         }
            
    //         lootBuilder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(rawOreItem, LootItem.lootTableItem(rawOreItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, MAX_TARGET_RAW_ORE-MIN_TARGET_RAW_ORE))))));
    //         add(oreBlock, lootBuilder);
    //     }


    //     @Override
    //     protected void addTables() {
    //         for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
    //             TuffOre block = (TuffOre) regBlock.get();
    //             tuffOreBuilder(regBlock.get(), block.dropItem);
    //         }
    //     }

    //     @Override
    //     protected Iterable<Block> getKnownBlocks() {
    //         return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    //     }
    // }
}
