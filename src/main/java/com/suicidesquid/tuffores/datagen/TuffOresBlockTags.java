package com.suicidesquid.tuffores.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.Nullable;

import com.suicidesquid.tuffores.TuffOres;
import com.suicidesquid.tuffores.setup.Registration;

import java.util.concurrent.CompletableFuture;

public class TuffOresBlockTags extends BlockTagsProvider {

    public TuffOresBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TuffOres.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(regBlock.get());
            tag(BlockTags.NEEDS_IRON_TOOL)
                .add(regBlock.get());
        }
     }
}
