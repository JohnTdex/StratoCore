package net.johntdex.stratocore.item;

import net.johntdex.stratocore.util.StratoTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class StratoToolTiers {
    private static Tier steel(int uses) {
        return new SimpleTier(
                StratoTags.Blocks.INCORRECT_STEEL_TOOL,
                uses,
                7.0f,   // speed
                2.5f,   // attackDamageBonus
                28,     // enchantmentValue
                () -> Ingredient.of(StratoItems.STEEL_INGOT.get()));
    }
    public static final Tier STEEL = steel(1024);

    //Weapons
    public static final Tier STEEL_SWORD = steel(800);

    public static final Tier CARBON_STEEL = new SimpleTier(
            StratoTags.Blocks.INCORRECT_STEEL_TOOL,
            1200,
            7.0f,
            2.7f,
            18,
            () -> Ingredient.of(StratoItems.CARBON_STEEL.get()));

    public static final Tier EXORIUM = new SimpleTier(
            StratoTags.Blocks.INCORRECT_EXORIUM_TOOL,
            2077,
            9.0f,
            4f,
            28,
            () -> Ingredient.of(StratoItems.EXORIUM_INGOT.get()));
}