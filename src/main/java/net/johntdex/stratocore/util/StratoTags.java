package net.johntdex.stratocore.util;

import net.johntdex.stratocore.StratoCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class StratoTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_STEEL_TOOL = tag("needs_steel_tool");
        public static final TagKey<Block> INCORRECT_STEEL_TOOL = tag("incorrect_steel_tool");
        public static final TagKey<Block> NEEDS_EXORIUM_TOOL = tag("needs_exorium_tool");
        public static final TagKey<Block> INCORRECT_EXORIUM_TOOL = tag("incorrect_exorium_tool");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK,
                    ResourceLocation.fromNamespaceAndPath(StratoCore.MODID, name));
        }
    }
}

