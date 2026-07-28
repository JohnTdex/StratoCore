package net.johntdex.stratocore.item;

import net.johntdex.stratocore.StratoCore;
import net.johntdex.stratocore.block.StratoBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class StratoCoreCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StratoCore.MODID);


    public static final Supplier<CreativeModeTab> EXO_TAB = CREATIVE_MODE_TABS.register("stratocore_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(StratoItems.STEEL_INGOT.get()))
            .title(Component.translatable("creativetab.stratocore.stratocore_tab"))
            .displayItems(((itemDisplayParameters, output) -> {

                output.accept(StratoItems.IMPURE_STEEL);
                output.accept(StratoItems.STEEL_INGOT);
                output.accept(StratoItems.CARBON_STEEL);
                output.accept(StratoItems.UNSTABLE_EXORIUM_INGOT);
                output.accept(StratoItems.EXORIUM_INGOT);
                output.accept(StratoBlocks.STEEL_BLOCK);
                output.accept(StratoBlocks.EXORIUM_BLOCK);


            }))
            .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
