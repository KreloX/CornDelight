package krelox.corndelight.item;

import krelox.corndelight.CornDelight;
import krelox.corndelight.block.CornDelightBlocks;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public class CornDelightItems {

    public static final ArrayList<Item> ITEMS = new ArrayList<>();

    // -------------------------
    // Blocks as items
    // -------------------------

//    public static final Item NACHOS_BLOCK = registerBlockWithTab(
//            CornDelightBlocks.NACHOS_BLOCK,
//            baseItem().maxCount(1)
//    );

    // -------------------------
    // Crop products
    // -------------------------

    public static final Item CORN = registerWithTab("corn",
            Item::new,
            foodItem(foodBuilder(2, 0.2F))
    );

    public static final Item CORN_SEEDS = registerItemNameBlockWithTab("corn_seeds",
            CornDelightBlocks.CORN_CROP,
            foodItem(foodBuilder(1, 0.2F))
    );

    public static final Item GRILLED_CORN = registerWithTab("grilled_corn",
            Item::new,
            foodItem(foodBuilder(6, 0.2F))
    );

    public static final Item BOILED_CORN = registerWithTab("boiled_corn",
            Item::new,
            foodItem(foodBuilder(6, 0.2F))
    );

    public static final Item POPCORN = registerWithTab("popcorn",
            Item::new,
            foodItem(foodBuilder(3, 0.5F), snackConsumable())
    );

    public static final Item CARAMEL_POPCORN = registerWithTab("caramel_popcorn",
            Item::new,
            foodItem(foodBuilder(5, 0.6F), snackConsumable())
    );

    public static final Item CREAMED_CORN = registerWithTab("creamed_corn",
            props -> new ConsumableItem(props, true),
            foodItem(
                    foodBuilder(7, 0.5F),
                    foodWithEffects(1.0F, new StatusEffectInstance(ModEffects.COMFORT, 3600))
            ).maxCount(16).recipeRemainder(Items.BOWL)
    );

    public static final Item CORN_SOUP = registerWithTab("corn_soup",
            props -> new ConsumableItem(props, true),
            foodItem(
                    foodBuilder(10, 0.9F),
                    foodWithEffects(1.0F, new StatusEffectInstance(ModEffects.COMFORT, 3600))
            ).maxCount(16).recipeRemainder(Items.BOWL)
    );

    public static final Item CREAMY_CORN_DRINK = registerWithTab("creamy_corn_drink",
            props -> new ConsumableItem(props, true),
            foodItem(
                    foodBuilder(2, 0.6F).alwaysEdible(),
                    foodWithEffects(1.0F,
                            new StatusEffectInstance(ModEffects.COMFORT, 1200),
                            new StatusEffectInstance(StatusEffects.REGENERATION, 1200)
                    )
            ).maxCount(16).recipeRemainder(Items.GLASS_BOTTLE)
    );

    public static final Item CORNBREAD_BATTER = registerWithTab("cornbread_batter",
            Item::new,
            foodItem(foodBuilder(1, 0.2F))
    );

    public static final Item CORNBREAD = registerWithTab("cornbread",
            Item::new,
            foodItem(foodBuilder(4, 0.5F))
    );

    public static final Item CORN_DOG = registerWithTab("corn_dog",
            Item::new,
            foodItem(foodBuilder(8, 0.9F))
    );

    public static final Item CLASSIC_CORN_DOG = registerWithTab("classic_corn_dog",
            Item::new,
            foodItem(foodBuilder(10, 0.9F))
    );

    public static final Item RAW_TORTILLA = registerWithTab("raw_tortilla",
            Item::new,
            foodItem(foodBuilder(1, 0.2F))
    );

    public static final Item TORTILLA = registerWithTab("tortilla",
            Item::new,
            foodItem(foodBuilder(3, 0.4F))
    );

    public static final Item TACO = registerWithTab("taco",
            props -> new ConsumableItem(props, true),
            foodItem(
                    foodBuilder(12, 0.8F),
                    foodWithEffects(1.0F, new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0))
            )
    );

    public static final Item CORNBREAD_STUFFING = registerWithTab("cornbread_stuffing",
            props -> new ConsumableItem(props, true),
            foodItem(
                    foodBuilder(12, 1.0F),
                    foodWithEffects(1.0F, new StatusEffectInstance(ModEffects.NOURISHMENT, 6000))
            ).maxCount(16).recipeRemainder(Items.BOWL)
    );

    public static final Item TORTILLA_CHIP = registerWithTab("tortilla_chip",
            Item::new,
            foodItem(foodBuilder(1, 0.1F), snackConsumable())
    );

//    public static final Item NACHOS_BOWL = registerWithTab("nachos_bowl",
//            props -> new ConsumableItem(props, true),
//            foodItem(
//                    foodBuilder(12, 1.0F),
//                    foodWithEffects(1.0F, new StatusEffectInstance(ModEffects.NOURISHMENT, 6000))
//            ).maxCount(16).recipeRemainder(Items.BOWL)
//    );

    private static Item.Settings baseItem() {
        return new Item.Settings();
    }

    private static Item.Settings foodItem(FoodComponent.Builder food) {
        return baseItem().food(food.build());
    }

    private static Item.Settings foodItem(FoodComponent.Builder food, ConsumableComponent.Builder consumable) {
        return baseItem().food(food.build(), consumable.build());
    }

    private static FoodComponent.Builder foodBuilder(int nutrition, float saturation) {
        return new FoodComponent.Builder().nutrition(nutrition).saturationModifier(saturation);
    }

    // Vanilla eat ~1.6s; snack is fast-eat ~0.8s.
    private static ConsumableComponent.Builder snackConsumable() {
        return ConsumableComponents.food().consumeSeconds(0.8F);
    }

    private static ConsumableComponent.Builder foodWithEffects(float chance, StatusEffectInstance... effects) {
        return ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(List.of(effects), chance));
    }

    @SuppressWarnings("unused")
    private static ConsumableComponent.Builder drinkWithEffects(float chance, StatusEffectInstance... effects) {
        return ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(List.of(effects), chance));
    }

    // -------------------------
    // Registration
    // -------------------------

    private static Item registerWithTab(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Identifier id = Identifier.of(CornDelight.MODID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

        // Critical for 1.21.2+:
        settings.registryKey(key);

        Item item = factory.apply(settings);
        ITEMS.add(item);

        return Registry.register(Registries.ITEM, id, item);
    }

    private static Item registerWithTab(String name, Item.Settings settings) {
        return registerWithTab(name, Item::new, settings);
    }

    private static Item registerBlockWithTab(net.minecraft.block.Block block, Item.Settings settings) {
        Identifier id = Registries.BLOCK.getId(block); // block and block-item share id
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

        settings.registryKey(key);
        settings.useBlockPrefixedTranslationKey();

        BlockItem item = new BlockItem(block, settings);
        ITEMS.add(item);

        return Registry.register(Registries.ITEM, id, item);
    }

    private static Item registerBlockWithTab(net.minecraft.block.Block block) {
        return registerBlockWithTab(block, baseItem());
    }

    private static Item registerItemNameBlockWithTab(String name, net.minecraft.block.Block block, Item.Settings settings) {
        Identifier id = Identifier.of(CornDelight.MODID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

        settings.registryKey(key);

        BlockItem item = new BlockItem(block, settings);
        ITEMS.add(item);

        return Registry.register(Registries.ITEM, id, item);
    }

    public static void registerItems() {}
}
