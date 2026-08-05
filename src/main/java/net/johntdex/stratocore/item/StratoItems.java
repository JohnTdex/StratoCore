package net.johntdex.stratocore.item;

import net.johntdex.stratocore.StratoCore;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StratoItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(StratoCore.MODID);

    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> IMPURE_STEEL = ITEMS.register("impure_steel", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UNSTABLE_EXORIUM_INGOT = ITEMS.register("unstable_exorium_ingot", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> EXORIUM_INGOT = ITEMS.register("exorium_ingot", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CARBON_STEEL = ITEMS.register("carbon_steel", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> EXORIUM_BLUEPRINT = ITEMS.register("exorium_blueprint", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(3)));



    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
