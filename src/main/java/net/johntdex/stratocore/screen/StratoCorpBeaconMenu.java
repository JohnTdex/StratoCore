package net.johntdex.stratocore.screen;

import net.johntdex.stratocore.block.StratoBlocks;
import net.johntdex.stratocore.block.entity.StratoCorpBeaconBlockEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class StratoCorpBeaconMenu extends AbstractContainerMenu {

    public final StratoCorpBeaconBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    private static final int MACHINE_SLOT_START = 0;
    private static final int MACHINE_SLOT_COUNT = 3;
    private static final int PLAYER_INVENTORY_START = MACHINE_SLOT_START + MACHINE_SLOT_COUNT;
    private static final int PLAYER_INVENTORY_COUNT = 27;
    private static final int HOTBAR_START =  PLAYER_INVENTORY_START + PLAYER_INVENTORY_COUNT;
    private static final int HOTBAR_COUNT = 9;
    private static final int TOTAL_SLOTS = HOTBAR_START + HOTBAR_COUNT;

    public StratoCorpBeaconMenu (int containerID, Inventory inv, RegistryFriendlyByteBuf extraData) {
        this(containerID, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public StratoCorpBeaconMenu (int containerID, Inventory inv, BlockEntity blockEntity,ContainerData data) {
        super(StratoMenuTypes.STRATOCORP_BEACON_MENU.get(), containerID);
        this.blockEntity = (StratoCorpBeaconBlockEntity)blockEntity;
        this.level = inv.player.level();
        this.data = data;

        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, StratoCorpBeaconBlockEntity.SLOT_INGOT, 47,35));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, StratoCorpBeaconBlockEntity.SLOT_AMETHYST_SHARD, 67,35));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, StratoCorpBeaconBlockEntity.SLOT_OUTPUT, 124, 35));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        addDataSlots(data);
    }

    private void addPlayerInventory (Inventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }
    private void addPlayerHotbar (Inventory playerInventory) {
        for (int row = 0; row < 9; row++) {
            this.addSlot(new Slot(playerInventory, row, 8 + row * 18, 142));
        }
    }
    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelWidth = 26;
        return maxProgress != 0 && progress !=0 ? progress * arrowPixelWidth / maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if(index < PLAYER_INVENTORY_START) {
            if (!moveItemStackTo(sourceStack, PLAYER_INVENTORY_START, TOTAL_SLOTS, true)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TOTAL_SLOTS) {
            if (!moveItemStackTo(sourceStack, MACHINE_SLOT_START, PLAYER_INVENTORY_START, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        if (sourceStack.getCount() == copyOfSourceStack.getCount()) {
            return ItemStack.EMPTY;
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, StratoBlocks.STRATOCORP_BEACON.get());
    }
}
