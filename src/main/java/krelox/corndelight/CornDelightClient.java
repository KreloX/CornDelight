package krelox.corndelight;

import krelox.corndelight.block.CornDelightBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

public class CornDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(
                BlockRenderLayer.CUTOUT,
                CornDelightBlocks.CORN_CROP,
                CornDelightBlocks.WILD_CORN,
                CornDelightBlocks.POPCORN_BOX
        );
    }
}
