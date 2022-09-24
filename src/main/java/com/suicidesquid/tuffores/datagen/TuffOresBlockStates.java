package com.suicidesquid.tuffores.datagen;

import com.suicidesquid.tuffores.TuffOres;
import com.suicidesquid.tuffores.setup.Registration;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class TuffOresBlockStates extends BlockStateProvider {
    public TuffOresBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, TuffOres.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
            simpleBlock(regBlock.get());
        }
    }
}
