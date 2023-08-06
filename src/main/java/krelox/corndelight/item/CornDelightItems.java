package krelox.corndelight.item;

import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import com.nhoryzon.mc.farmersdelight.item.DrinkableItem;
import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import krelox.corndelight.CornDelight;
import krelox.corndelight.block.CornDelightBlocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CornDelightItems {

    public static List<Item> items = new ArrayList<>();

    public static final Item NACHOS = registerItem("nachos",
            new BlockItem(CornDelightBlocks.NACHOS, new Item.Settings().maxCount(1)));

    public static final Item CORN = registerItem("corn",
            new Item(foodSettings(foodBuilder(2, 0.2F))));

    public static final Item CORN_SEEDS = registerItem("corn_seeds",
            new AliasedBlockItem(CornDelightBlocks.CORN_CROP, foodSettings(foodBuilder(1, 0.2F))));

    public static final Item GRILLED_CORN = registerItem("grilled_corn",
            new Item(foodSettings(foodBuilder(6, 0.2F))));

    public static final Item BOILED_CORN = registerItem("boiled_corn",
            new Item(foodSettings(foodBuilder(6, 0.2F))));

    public static final Item POPCORN = registerItem("popcorn",
            new Item(foodSettings(foodBuilder(3, 0.5F).snack())));

    public static final Item CARAMEL_POPCORN = registerItem("caramel_popcorn",
            new Item(foodSettings(foodBuilder(5, 0.6F).snack())));

    public static final Item CREAMED_CORN = registerItem("creamed_corn",
            new ConsumableItem(foodSettings(foodBuilder(7, 0.5F)
                    .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 3600), 1F))
                    .maxCount(16).recipeRemainder(Items.BOWL), true));

    public static final Item CORN_SOUP = registerItem("corn_soup",
            new ConsumableItem(foodSettings(foodBuilder(10, 0.9F)
                    .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 3600), 1F))
                    .maxCount(16).recipeRemainder(Items.BOWL), true));

    public static final Item CREAMY_CORN_DRINK = registerItem("creamy_corn_drink",
            new DrinkableItem(foodSettings(foodBuilder(2, 0.6F).alwaysEdible()
                    .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 1200), 1F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1200), 1F))
                    .maxCount(16).recipeRemainder(Items.GLASS_BOTTLE), true));

    public static final Item CORNBREAD_BATTER = registerItem("cornbread_batter",
            new Item(foodSettings(foodBuilder(1, 0.2F))));

    public static final Item CORNBREAD = registerItem("cornbread",
            new Item(foodSettings(foodBuilder(4, 0.5F))));

    public static final Item CORN_DOG = registerItem("corn_dog",
            new Item(foodSettings(foodBuilder(8, 0.9F))));

    public static final Item CLASSIC_CORN_DOG = registerItem("classic_corn_dog",
            new Item(foodSettings(foodBuilder(10, 0.9F))));

    public static final Item RAW_TORTILLA = registerItem("raw_tortilla",
            new Item(foodSettings(foodBuilder(1, 0.2F))));

    public static final Item TORTILLA = registerItem("tortilla",
            new Item(foodSettings(foodBuilder(3, 0.4F))));

    public static final Item TACO = registerItem("taco",
            new ConsumableItem(foodSettings(foodBuilder(12, 0.8F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0), 1F)), true));

    public static final Item CORNBREAD_STUFFING = registerItem("cornbread_stuffing",
            new ConsumableItem(foodSettings(foodBuilder(12, 1F)
                    .statusEffect(new StatusEffectInstance(EffectsRegistry.NOURISHMENT.get(), 6000), 1F))
                    .maxCount(16).recipeRemainder(Items.BOWL), true));

    public static final Item TORTILLA_CHIP = registerItem("tortilla_chip",
            new Item(foodSettings(foodBuilder(1, 0.1F).snack())));

    public static final Item NACHOS_BOWL = registerItem("nachos_bowl",
            new ConsumableItem(foodSettings(foodBuilder(12, 1F)
                    .statusEffect(new StatusEffectInstance(EffectsRegistry.NOURISHMENT.get(), 6000), 1F))
                    .maxCount(16).recipeRemainder(Items.BOWL), true));

    private static Item.Settings foodSettings(FoodComponent.Builder food) {
        return new Item.Settings().food(food.build());
    }

    private static FoodComponent.Builder foodBuilder(int hunger, float saturation) {
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation);
    }

    public static Item registerItem(String name, Item item) {
        items.add(item);
        return Registry.register(Registries.ITEM, new Identifier(CornDelight.MODID, name), item);
    }

    public static void registerItems() {
    }
}
