package com.suicidesquid.tuffores.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.OreBlock;


public class TuffOre extends OreBlock {

    public final Item dropItem;


    public TuffOre(Properties properties, Item dropItem) {
        super(properties);       
        this.dropItem = dropItem;
    }
    
}
