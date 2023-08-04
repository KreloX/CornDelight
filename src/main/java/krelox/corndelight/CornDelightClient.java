package krelox.corndelight;

import krelox.corndelight.block.CornDelightBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class CornDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(CornDelightBlocks.CORN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CornDelightBlocks.WILD_CORN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CornDelightBlocks.POPCORN_BOX, RenderLayer.getCutout());
    }
}
