package krelox.corndelight;

import krelox.corndelight.data.CornDelightLoot;
import krelox.corndelight.data.CornDelightModels;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CornDelightDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(CornDelightLoot::new);
        pack.addProvider(CornDelightModels::new);
    }
}
