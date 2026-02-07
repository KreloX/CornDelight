package krelox.corndelight;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class CornDelightTags {
    public static class Items {
        public static final TagKey<Item> CORN = createCommonTag("crops/corn");
        public static final TagKey<Item> CORN_SEEDS = createCommonTag("seeds/corn");
        public static final TagKey<Item> FOODS_TORTILLA_MEATS = createTag("foods/tortilla_meats");
        public static final TagKey<Item> VEGETABLES = createCommonTag("vegetables");

        private static TagKey<Item> createTag(String path) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(CornDelight.MODID, path));
        }

        private static TagKey<Item> createCommonTag(String path) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", path));
        }
    }
}
