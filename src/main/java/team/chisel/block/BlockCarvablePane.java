package team.chisel.block;

import java.util.List;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import team.chisel.client.render.RendererCTMPane;

import com.cricketcraft.chisel.api.ChiselTabs;
import com.cricketcraft.chisel.api.ICarvable;
import com.cricketcraft.chisel.api.carving.CarvableHelper;
import com.cricketcraft.chisel.api.carving.IVariationInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCarvablePane extends BlockPane implements ICarvable {

    public CarvableHelper carverHelper;
    public boolean isAlpha;

    public BlockCarvablePane(Material material, boolean drops) {
        super("", "", material, drops);

        carverHelper = new CarvableHelper(this);

        setCreativeTab(ChiselTabs.tabOtherChiselBlocks);
    }

    public BlockCarvablePane setStained(boolean a) {
        this.isAlpha = a;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return isAlpha ? 1 : 0;
    }

    @Override
    public int getRenderType() {
        return RendererCTMPane.id;
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return carverHelper.getIcon(side, metadata);
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        carverHelper.registerBlockIcons("Chisel", this, register);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        carverHelper.registerSubBlocks(this, tabs, list);
    }

    @Override
    public IVariationInfo getManager(IBlockAccess world, int x, int y, int z, int metadata) {
        return carverHelper.getVariation(metadata);
    }

    @Override
    public IVariationInfo getManager(int meta) {
        return carverHelper.getVariation(meta);
    }
}
