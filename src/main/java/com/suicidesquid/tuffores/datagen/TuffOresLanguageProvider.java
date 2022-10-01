package com.suicidesquid.tuffores.datagen;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.suicidesquid.tuffores.TuffOres;
import com.suicidesquid.tuffores.setup.Registration;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class TuffOresLanguageProvider extends LanguageProvider {

    public TuffOresLanguageProvider(DataGenerator gen, String locale) {
        super(gen, TuffOres.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        // add("itemGroup." + TuffOres.MODID, "Tuff Ores");

        for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
            String readableName = Arrays.stream(regBlock.getId().getPath().replace("_", " ").split(" "))
                      .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                      .collect(Collectors.joining(" "));
            add(regBlock.get(), readableName);
        }

        add(Registration.TUFF_ORES_MODULE.get(), "Tuff Ores Module");

    }
}
