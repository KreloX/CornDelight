package krelox.corndelight.item;

import com.nhoryzon.mc.farmersdelight.item.DrinkableItem;
import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import krelox.corndelight.CornDelight;
import krelox.corndelight.block.CornDelightBlocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CornDelightItems {

    public static List<Item> items = new ArrayList<>();

    public static final Item CORN = registerItem("corn",
            new Item(foodSettings(2, 0.2F)));

    public static final Item CORN_SEEDS = registerItem("corn_seeds",
            new AliasedBlockItem(CornDelightBlocks.CORN_CROP, foodSettings(1, 0.2F)));

    public static final Item GRILLED_CORN = registerItem("grilled_corn",
            new Item(foodSettings(6, 0.2F)));

    public static final Item BOILED_CORN = registerItem("boiled_corn",
            new Item(foodSettings(6, 0.2F)));

    public static final Item POPCORN = registerItem("popcorn",
            new Item(foodSettings(3, 0.5F)));

    public static final Item CARAMEL_POPCORN = registerItem("caramel_popcorn",
            new Item(foodSettings(5, 0.6F)));

    public static final Item CREAMED_CORN = registerItem("creamed_corn",
            new Item(foodSettings(7, 0.5F, Items.BOWL,
                    new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 3600))));

    public static final Item CORN_SOUP = registerItem("corn_soup",
            new Item(foodSettings(10, 0.9F, Items.BOWL,
                    new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 3600))));

    public static final Item CREAMY_CORN_DRINK = registerItem("creamy_corn_drink",
            new DrinkableItem(foodSettings(2, 0.6F, Items.GLASS_BOTTLE,
                    new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 1200),
                    new StatusEffectInstance(StatusEffects.REGENERATION, 1200))));

    public static final Item CORNBREAD_BATTER = registerItem("cornbread_batter",
            new Item(foodSettings(1, 0.2F)));

    public static final Item CORNBREAD = registerItem("cornbread",
            new Item(foodSettings(4, 0.5F)));

    public static final Item CORN_DOG = registerItem("corn_dog",
            new Item(foodSettings(8, 0.9F, Items.STICK)));

    public static final Item CLASSIC_CORN_DOG = registerItem("classic_corn_dog",
            new Item(foodSettings(10, 0.9F, Items.STICK)));

    public static final Item RAW_TORTILLA = registerItem("raw_tortilla",
            new Item(foodSettings(1, 0.2F)));

    public static final Item TORTILLA = registerItem("tortilla",
            new Item(foodSettings(3, 0.4F)));

    public static final Item TACO = registerItem("taco",
            new Item(foodSettings(12, 0.8F,
                    new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0))));

    public static final Item CORNBREAD_STUFFING = registerItem("cornbread_stuffing",
            new Item(foodSettings(12, 1F, Items.BOWL,
                    new StatusEffectInstance(EffectsRegistry.NOURISHMENT.get(), 6000, 0))));

    public static final Item TORTILLA_CHIP = registerItem("tortilla_chip",
            new Item(foodSettings(1, 0.1F)));

    public static final Item NACHOS = registerItem("nachos",
            new Item(foodSettings(12, 1F, Items.BOWL,
                    new StatusEffectInstance(EffectsRegistry.NOURISHMENT.get(), 6000))));

    private static Item.Settings foodSettings(int hunger, float saturation, StatusEffectInstance... effects) {
        return foodSettings(hunger, saturation, null, effects);
    }

    private static Item.Settings foodSettings(int hunger, float saturation, Item container, StatusEffectInstance... effects) {
        FoodComponent.Builder builder = new FoodComponent.Builder();
        for (StatusEffectInstance effect : effects) {
            builder.statusEffect(effect, 1F);
        }
        return new Item.Settings().food(builder.hunger(hunger).saturationModifier(saturation).build()).recipeRemainder(container);
    }

    public static Item registerItem(String name, Item item) {
        items.add(item);
        return Registry.register(Registries.ITEM, new Identifier(CornDelight.MODID, name), item);
    }

    public static void registerItems() {
    }
}
