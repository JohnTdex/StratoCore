package net.johntdex.stratocore.datagen;

import net.johntdex.stratocore.StratoCore;
import net.johntdex.stratocore.block.StratoBlocks;
import net.johntdex.stratocore.util.StratoTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class StratoBlockTagProvider extends BlockTagsProvider {
    public StratoBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, StratoCore.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Steel behaves as iron tier: it cannot mine anything that requires a diamond tool
        // (obsidian, diamond ore, ancient debris, etc.). This is the tag the STEEL tier reads
        // via getIncorrectBlocksForDrops().
        tag(StratoTags.Blocks.INCORRECT_STEEL_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        //STEEL BLOCK
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(StratoBlocks.STEEL_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(StratoBlocks.STEEL_BLOCK.get());

        //EXORIUM BLOCK
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(StratoBlocks.EXORIUM_BLOCK.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(StratoBlocks.EXORIUM_BLOCK.get());

        //STRATOCORP BEACON
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(StratoBlocks.STRATOCORP_BEACON.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(StratoBlocks.STRATOCORP_BEACON.get());

        //NETHERITE LEVEL

        // NEEDS_STEEL_TOOL is intentionally left empty for now — populate it later with your own
        // custom blocks that should require a steel-or-better tool to mine.
    }
}
