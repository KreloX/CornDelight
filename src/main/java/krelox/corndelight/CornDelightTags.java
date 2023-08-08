package krelox.corndelight;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CornDelightTags {
    public static class Items {
        public static final TagKey<Item> CABBAGE = createCommonTag("crops/cabbage");
        public static final TagKey<Item> CORN = createCommonTag("crops/corn");
        public static final TagKey<Item> CORN_SEEDS = createCommonTag("seeds/corn");
        public static final TagKey<Item> ONION = createCommonTag("vegetables/onion");
        public static final TagKey<Item> EGGS = createCommonTag("eggs");
        public static final TagKey<Item> MILK = createCommonTag("milk");
        public static final TagKey<Item> COOKED_BEEF = createCommonTag("cooked_beef");
        public static final TagKey<Item> COOKED_CHICKEN = createCommonTag("cooked_chicken");
        public static final TagKey<Item> COOKED_MUTTON = createCommonTag("cooked_mutton");
        public static final TagKey<Item> COOKED_PORK = createCommonTag("cooked_pork");

        public static final TagKey<Item> CORN_DELIGHT_MEAT = createTag("corn_delight_meat");
        private static TagKey<Item> createTag(String path) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier(CornDelight.MODID, path));
        }
        private static TagKey<Item> createCommonTag(String path) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier("c", path));
        }
    }
}
