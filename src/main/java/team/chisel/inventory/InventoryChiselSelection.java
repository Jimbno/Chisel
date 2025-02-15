package team.chisel.inventory;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import team.chisel.item.chisel.ItemChisel;
import team.chisel.utils.General;

import com.cricketcraft.chisel.api.IChiselItem;

public class InventoryChiselSelection implements IInventory {

    ItemStack chisel;
    public final static int normalSlots = 60;
    ContainerChisel container;
    ItemStack[] inventory;
    private int currentScroll = 0;
    private int maxScroll = 0;

    public InventoryChiselSelection(ItemStack c) {
        super();

        inventory = new ItemStack[normalSlots + 1];
        chisel = c;
    }

    public void setCurrentScroll(int currentScroll) {
        if (this.currentScroll != currentScroll) {
            this.currentScroll = currentScroll;
            updateItems();
        }
    }

    public int getCurrentScroll() {
        return currentScroll;
    }

    public int getMaxScroll() {
        return maxScroll;
    }

    public void onInventoryUpdate(int slot) {

    }

    @Override
    public int getSizeInventory() {
        return normalSlots + 1;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return inventory[var1];
    }

    public void updateInventoryState(int slot) {
        onInventoryUpdate(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.inventory[slot] != null) {
            ItemStack stack;
            if (this.inventory[slot].stackSize <= amount) {
                stack = this.inventory[slot];
                this.inventory[slot] = null;
                updateInventoryState(slot);
                return stack;
            } else {
                stack = this.inventory[slot].splitStack(amount);

                if (this.inventory[slot].stackSize == 0) this.inventory[slot] = null;

                updateInventoryState(slot);

                return stack;
            }
        } else return null;
    }

    @Override
    public String getInventoryName() {
        return "container.chisel";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    public void clearItems() {
        for (int i = 0; i < normalSlots; i++) {
            inventory[i] = null;
        }
        maxScroll = 0;
    }

    public ItemStack getStackInSpecialSlot() {
        return inventory[normalSlots];
    }

    public void updateItems() {
        ItemStack chiseledItem = inventory[normalSlots];
        clearItems();

        if (chiseledItem == null) {
            container.onChiselSlotChanged();
            return;
        }

        Item item = chiseledItem.getItem();
        if (item == null) return;

        if (Block.getBlockFromItem(item) == null) return;

        if (!((IChiselItem) chisel.getItem())
                .canChisel(container.playerInventory.player.worldObj, chisel, General.getVariation(chiseledItem)))
            return;

        List<ItemStack> list = container.carving.getItemsForChiseling(chiseledItem);
        maxScroll = list.size();
        int activeVariations = 0;
        while (activeVariations < normalSlots && activeVariations + currentScroll < list.size()) {
            if (Block.blockRegistry.getNameForObject(
                    Block.getBlockFromItem(list.get(activeVariations + currentScroll).getItem())) != null) {
                inventory[activeVariations] = list.get(activeVariations + currentScroll);
                activeVariations++;
            }
        }

        container.onChiselSlotChanged();
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);

        if (stack == null) return null;
        inventory[slot] = null;

        updateInventoryState(slot);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        // we do not want external chisel variant updates
        if (slot == normalSlots) {
            inventory[slot] = stack;
            updateInventoryState(slot);
        }
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        // Really didn't think people would chisel a shovel
        if (stack.getItem() instanceof ItemTool) {
            return false;
        }

        return !(stack != null && (stack.getItem() instanceof ItemChisel)) && i == normalSlots;
    }
}
