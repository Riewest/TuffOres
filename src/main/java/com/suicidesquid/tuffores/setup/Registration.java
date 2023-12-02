package com.suicidesquid.tuffores.setup;

import javax.annotation.Nullable;

import com.suicidesquid.tuffores.TuffOres;
import com.suicidesquid.tuffores.blocks.TuffOre;
import com.suicidesquid.tuffores.items.TuffOresScannerModuleItem;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registration {

    public static final int TARGET_RAW_ORE_PER_VEIN = 1000000;
    public static final int TARGET_ORE_BLOCKS_PER_VEIN = 7500;
    public static final int TARGET_RAW_ORE_PER_BLOCK = TARGET_RAW_ORE_PER_VEIN / TARGET_ORE_BLOCKS_PER_VEIN;

    public static final float DEFAULT_STRENGTH_SCALAR = 3;  //Based on iron ore strength of 3

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TuffOres.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TuffOres.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TuffOres.MODID);

    

    public static void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        BLOCK_ITEMS.register(bus);
        ITEMS.register(bus);
    }

    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties();



    private static  RegistryObject<Block> registerOreBlock(String name, Item dropItem){
        RegistryObject<Block> toReturn = BLOCKS.register(name, () -> new TuffOre(BlockBehaviour.Properties.of(Material.STONE).strength(TARGET_RAW_ORE_PER_BLOCK * DEFAULT_STRENGTH_SCALAR), dropItem));
        fromBlock(toReturn);
        return toReturn;
    }

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return BLOCK_ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }

    public static RegistryObject<Item> scannableRegistration(){
        if(ModList.get().isLoaded("scannable"))
        {
            return ITEMS.register("tuff_ores_module", () -> new TuffOresScannerModuleItem(ITEM_PROPERTIES));
        }
        return null;
    }

    public static final RegistryObject<Block> TUFF_IRON_ORE = registerOreBlock("tuff_iron_ore", Items.RAW_IRON);
    public static final RegistryObject<Block> TUFF_GOLD_ORE = registerOreBlock("tuff_gold_ore", Items.RAW_GOLD);
    public static final RegistryObject<Block> TUFF_DIAMOND_ORE = registerOreBlock("tuff_diamond_ore", Items.DIAMOND);
    public static final RegistryObject<Block> TUFF_LAPIS_ORE = registerOreBlock("tuff_lapis_ore", Items.LAPIS_LAZULI);
    public static final RegistryObject<Block> TUFF_REDSTONE_ORE = registerOreBlock("tuff_redstone_ore", Items.REDSTONE);
    public static final RegistryObject<Block> TUFF_COAL_ORE = registerOreBlock("tuff_coal_ore", Items.COAL) ;
    public static final RegistryObject<Block> TUFF_EMERALD_ORE = registerOreBlock("tuff_emerald_ore", Items.EMERALD);
    public static final RegistryObject<Block> TUFF_COPPER_ORE = registerOreBlock("tuff_copper_ore", Items.RAW_COPPER);

    @Nullable
    public static final RegistryObject<Item> TUFF_ORES_MODULE = scannableRegistration();

}
