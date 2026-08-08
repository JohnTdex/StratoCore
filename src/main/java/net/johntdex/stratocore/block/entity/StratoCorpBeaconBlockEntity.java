package net.johntdex.stratocore.block.entity;

import net.johntdex.stratocore.block.StratoCorpBeaconBlock;
import net.johntdex.stratocore.item.StratoItems;
import net.johntdex.stratocore.screen.StratoCorpBeaconMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class StratoCorpBeaconBlockEntity extends BlockEntity implements MenuProvider {

    public static final int SLOT_INGOT = 0;
    public static final int SLOT_AMETHYST_SHARD = 1;
    public static final int SLOT_OUTPUT = 2;

    private int progress = 0;
    private int maxProgress = 100;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> StratoCorpBeaconBlockEntity.this.progress;
                case 1 -> StratoCorpBeaconBlockEntity.this.maxProgress;
                default -> 0;
            };
        }
        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> StratoCorpBeaconBlockEntity.this.progress = value;
                case 1 -> StratoCorpBeaconBlockEntity.this.maxProgress = value;
            }
        }
        @Override
        public int getCount() {
            return 2;
        }
    };

    public final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        //When Hopper is used, and you insert UEI and AS, both will be placed on the designated slot, making automation possible :D
        //actually need to register capabilities later lol.
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case SLOT_INGOT -> stack.is(StratoItems.UNSTABLE_EXORIUM_INGOT.get());
                case SLOT_AMETHYST_SHARD -> stack.is(Items.AMETHYST_SHARD);
                default -> false;
            };
        }
    };

    public StratoCorpBeaconBlockEntity(BlockPos pos, BlockState blockState) {
        super(StratoBlockEntities.STRATOCORP_BEACON_BE.get(), pos, blockState);

    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if(!hasRecipe()) {
            progress = 0;
            return;
        }

        progress++;
        setChanged(level, pos, state);

        if(progress >= maxProgress) {
            craftItem();
            progress = 0;
            }
        }

        private ItemStack recipeOutput() {
            return new ItemStack(StratoItems.EXORIUM_BLUEPRINT.get());
    }

    private boolean hasRecipe() {
        boolean hasInputs = inventory.getStackInSlot(SLOT_INGOT).is(StratoItems.UNSTABLE_EXORIUM_INGOT.get()) &&
                inventory.getStackInSlot(SLOT_AMETHYST_SHARD).is(Items.AMETHYST_SHARD);

                return hasInputs && outputAccepts(recipeOutput());
    }

    private boolean outputAccepts(ItemStack result) {
        ItemStack output = inventory.getStackInSlot(SLOT_OUTPUT);
        if(output.isEmpty()) return true;
        if(!ItemStack.isSameItemSameComponents(output, result)) return false;
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void craftItem() {
        ItemStack result = recipeOutput();

        inventory.extractItem(SLOT_INGOT, 1, false);
        inventory.extractItem(SLOT_AMETHYST_SHARD, 1, false);

        ItemStack output = inventory.getStackInSlot(SLOT_OUTPUT);
        if (output.isEmpty()) {
            inventory.setStackInSlot(SLOT_OUTPUT, result);
        } else {
            output.grow(result.getCount());
        }
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            container.setItem(i, inventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.stratocore.stratocorp_beacon");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player){
        return new StratoCorpBeaconMenu(containerId, playerInventory, this, this.data);
    }
}
