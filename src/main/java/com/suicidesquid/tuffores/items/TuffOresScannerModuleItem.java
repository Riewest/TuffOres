package com.suicidesquid.tuffores.items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.suicidesquid.tuffores.setup.Registration;

import li.cil.scannable.api.scanning.BlockScannerModule;
import li.cil.scannable.api.scanning.ScanResultProvider;
import li.cil.scannable.client.scanning.ScanResultProviders;
import li.cil.scannable.client.scanning.filter.BlockCacheScanFilter;
import li.cil.scannable.common.ModCreativeTabs;
import li.cil.scannable.common.forge.capabilities.ScannerModuleWrapper;
import li.cil.scannable.common.config.Strings;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.api.distmarker.Dist;


public class TuffOresScannerModuleItem extends Item {
    private final ICapabilityProvider capabilityProvider;

    public TuffOresScannerModuleItem(Properties properties) {
        super(properties.tab(ModCreativeTabs.COMMON));
        this.capabilityProvider = new ScannerModuleWrapper(new TuffOresScannerModule());
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, @Nullable final CompoundTag tag) {
        return capabilityProvider;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final Level level, final List<Component> tooltip, final TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        final int cost = 75;
        if (cost > 0) {
            tooltip.add(Strings.energyUsage(cost));
        }
    }

    protected class TuffOresScannerModule implements BlockScannerModule{
        @Override
        public int getEnergyCost(ItemStack arg0) {
            // TODO Auto-generated method stub
            return 75;
        }

        @Override
        @Nullable
        public ScanResultProvider getResultProvider() {
            // TODO Auto-generated method stub
            return ScanResultProviders.BLOCKS.get();
        }

        @Override
        public Predicate<BlockState> getFilter(ItemStack arg0) {
            List<Block> blocks = new ArrayList<Block>();
            for (RegistryObject<Block> regBlock : Registration.BLOCKS.getEntries()) {
                blocks.add(regBlock.get());
            }
            return new BlockCacheScanFilter(blocks);
        }
    }


}
