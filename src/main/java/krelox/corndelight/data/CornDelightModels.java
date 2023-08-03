package krelox.corndelight.data;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import krelox.corndelight.CornDelight;
import krelox.corndelight.block.CornDelightBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

public class CornDelightModels extends FabricModelProvider {
    public CornDelightModels(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        TextureMap textureMap = new TextureMap()
                .put(TextureKey.SIDE, new Identifier(CornDelight.MODID, "block/corn_crate_side"))
                .put(TextureKey.BOTTOM, new Identifier(FarmersDelightMod.MOD_ID, "block/crate_bottom"))
                .put(TextureKey.TOP, new Identifier(CornDelight.MODID, "block/corn_crate_top"));
        blockStateModelGenerator.registerSingleton(CornDelightBlocks.CORN_CRATE, textureMap, TexturedModel.CUBE_BOTTOM_TOP.get(CornDelightBlocks.CORN_CRATE).getModel());
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
