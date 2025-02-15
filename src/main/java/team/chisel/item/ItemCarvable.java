package team.chisel.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import team.chisel.config.Configurations;
import team.chisel.utils.General;

import com.cricketcraft.chisel.api.ICarvable;
import com.cricketcraft.chisel.api.carving.IVariationInfo;

public class ItemCarvable extends ItemBlock {

    public ItemCarvable(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return Block.getBlockFromItem(this).getIcon(2, damage);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean advancedTooltips) {
        if (!Configurations.blockDescriptions) return;

        Item item = General.getItem(stack);
        if (item == null) return;

        Block block = Block.getBlockFromItem(this);
        if (!(block instanceof ICarvable)) return;

        ICarvable carvable = (ICarvable) block;
        IVariationInfo var = carvable.getManager(stack.getItemDamage());
        if (var == null) return;

        lines.add(var.getDescription());

        if (!Configurations.canMobsSpawnOnTheChiselBlocks)
            lines.add(StatCollector.translateToLocal("item.chisel.nomobspawnsonthisblock.desc"));
    }
}
