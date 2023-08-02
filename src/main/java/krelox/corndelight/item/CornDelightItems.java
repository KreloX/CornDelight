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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CornDelightItems {
    public static Map<String, Item> items = new HashMap<>();

    static {
        //items.put("wild_corn", new BlockItem(, new Item.Settings()));

        Arrays.stream(CornDelightFood.values())
                .forEach(food -> items.put(food.toString(), new Item(new Item.Settings().food(food.get()).recipeRemainder(food.getContainer()))));

        items.put("corn_seeds", new AliasedBlockItem(CornDelightBlocks.CORN_CROP, new Item.Settings()
                .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2F).build())));

        items.put("creamy_corn_drink", new DrinkableItem(new Item.Settings()
                .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.6F)
                        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 1200), 1F)
                        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1200), 1F).build())
                .recipeRemainder(Items.GLASS_BOTTLE)));
    }

    public static void registerItems() {
        items.forEach((name, item) -> Registry.register(Registries.ITEM, new Identifier(CornDelight.MODID, name), item));
    }
}
