package krelox.corndelight.data;

import krelox.corndelight.CornDelight;
import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;

import java.util.concurrent.CompletableFuture;

public class CornDelightLang extends FabricLanguageProvider {
    public CornDelightLang(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(CornDelightBlocks.CORN_CROP, "Corn");
        translationBuilder.add("itemGroup.corndelight", "Corn Delight");
        translationBuilder.add("corndelight.block.popcorn.barehand", "Use your bare hand to eat popcorn.");
        CornDelightItems.ITEMS.forEach(item -> {
            Identifier id = Registries.ITEM.getId(item);
            String path = id.getPath();
            translationBuilder.add("item." + id.getNamespace() + "." + path, formatName(path));
        });
    }

    @SuppressWarnings("deprecation")
    public String formatName(String name) {
        String s = name.replace(CornDelight.MODID + ":", "").replace('_', ' ');
        return WordUtils.capitalize(s.trim());
    }
}
