package krelox.corndelight;

import krelox.corndelight.data.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CornDelightDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(CornDelightLang::new);
        generator.addProvider(CornDelightLoot::new);
        generator.addProvider(CornDelightModels::new);
        generator.addProvider(CornDelightRecipes::new);
        generator.addProvider(CornDelightItemTags::new);
    }
}
