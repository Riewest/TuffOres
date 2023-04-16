package com.suicidesquid.tuffores.datagen;

import com.suicidesquid.tuffores.TuffOres;
import com.suicidesquid.tuffores.setup.Registration;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TuffOresItemModels extends ItemModelProvider{
    public TuffOresItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TuffOres.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> regBlockItem : Registration.ITEMS.getEntries()) {
            withExistingParent(ForgeRegistries.ITEMS.getKey(regBlockItem.get()).getPath(), modLoc("block/" + regBlockItem.getId().getPath()));
        }


        if (Registration.TUFF_ORES_MODULE != null){
            singleTexture(Registration.TUFF_ORES_MODULE.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/"+ Registration.TUFF_ORES_MODULE.getId().getPath()));
        }        
    }
}
