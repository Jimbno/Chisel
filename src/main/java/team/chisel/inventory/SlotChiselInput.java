package team.chisel.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotChiselInput extends Slot {

    public SlotChiselInput(ContainerChisel container, InventoryChiselSelection inv, int i, int j, int k) {
        super(inv, i, j, k);
        selInventory = inv;
        this.container = container;
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return !container.finished && !container.carving.getItemsForChiseling(itemstack).isEmpty()
                && super.isItemValid(itemstack);
    }

    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return !container.finished && super.canTakeStack(par1EntityPlayer);
    }

    @Override
    public void onSlotChanged() {
        if (container.finished) return;

        super.onSlotChanged();
        selInventory.setCurrentScroll(0);
        selInventory.updateItems();
    }

    ContainerChisel container;
    InventoryChiselSelection selInventory;
}
