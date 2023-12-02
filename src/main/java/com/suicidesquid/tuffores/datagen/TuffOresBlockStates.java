package com.suicidesquid.tuffores.datagen;

import com.suicidesquid.tuffores.TuffOres;
import com.suicidesquid.tuffores.setup.Registration;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class TuffOresBlockStates extends BlockStateProvider {
    public TuffOresBlockStates(PackOutput packOutput, ExistingFileHelper helper) {
        super(packOutput, TuffOres.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
            simpleBlock(regBlock.get());
        }
    }
}
