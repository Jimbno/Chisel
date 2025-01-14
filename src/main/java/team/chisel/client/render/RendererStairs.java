package team.chisel.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import team.chisel.block.BlockCarvableStairs;
import team.chisel.ctmlib.Drawing;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RendererStairs implements ISimpleBlockRenderingHandler {

    public static int id;

    public RendererStairs() {
        id = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
        Drawing.drawBlock(block, meta, renderer);
        renderer.setRenderBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
        Drawing.drawBlock(block, meta, renderer);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block blck, int modelId,
            RenderBlocks renderer) {
        if (blck == null || !(blck instanceof BlockCarvableStairs)) return false;
        BlockCarvableStairs block = (BlockCarvableStairs) blck;

        renderer.renderBlockStairs(block, x, y, z);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int renderId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return id;
    }

    public int getRenderBlockPass() {
        return 1;
    }

}
