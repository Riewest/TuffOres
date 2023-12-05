package com.suicidesquid.tuffores.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public class TuffOre extends Block {

    public final Item dropItem;

    public TuffOre(Properties properties, Item dropItem) {
        super(properties.requiresCorrectToolForDrops());       
        this.dropItem = dropItem;
    }
    
}
