package krelox.corndelight;

import krelox.corndelight.data.CornDelightLang;
import krelox.corndelight.data.CornDelightLoot;
import krelox.corndelight.data.CornDelightModels;
import krelox.corndelight.data.CornDelightRecipes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CornDelightDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(CornDelightLang::new);
        pack.addProvider(CornDelightLoot::new);
        pack.addProvider(CornDelightModels::new);
        pack.addProvider(CornDelightRecipes::new);
    }
}
