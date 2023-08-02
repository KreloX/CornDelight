package krelox.corndelight.item;

import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.function.Supplier;

public enum CornDelightFood {
    CORN(2, 0.2F),
    GRILLED_CORN(6, 0.2F),
    BOILED_CORN(6, 0.2F),
    POPCORN(3, 0.5F),
    CARAMEL_POPCORN(5, 0.6F),
    CREAMED_CORN(7, 0.5F, Items.BOWL, new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 3600, 0)),
    CORN_SOUP(10, 0.9F, Items.BOWL, new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 3600, 0)),
    CORNBREAD_BATTER(1, 0.2F),
    CORNBREAD(4, 0.5F),
    CORN_DOG(8, 0.9F, Items.STICK),
    CLASSIC_CORN_DOG(10, 0.9F, Items.STICK),
    RAW_TORTILLA(1, 0.2F),
    TORTILLA(3, 0.4F),
    TACO(12, 0.8F, new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0)),
    CORNBREAD_STUFFING(12, 1F, Items.BOWL, new StatusEffectInstance(EffectsRegistry.NOURISHMENT.get(), 6000, 0)),
    TORTILLA_CHIP(1, 0.1F),
    NACHOS(12, 1F, Items.BOWL, new StatusEffectInstance(EffectsRegistry.NOURISHMENT.get(), 6000, 0));

    private final Item container;
    private final Supplier<FoodComponent> food;

    CornDelightFood(int hunger, float saturation, StatusEffectInstance... effects) {
        this(hunger, saturation, Items.AIR, effects);
    }

    CornDelightFood(int hunger, float saturation, Item container, StatusEffectInstance... effects) {
        this.container = container;
        this.food = () -> {
            FoodComponent.Builder builder = new FoodComponent.Builder();
            builder.hunger(hunger).saturationModifier(saturation);

            for (StatusEffectInstance effect : effects) {
                builder.statusEffect(effect, 1F);
            }

            return builder.build();
        };
    }

    public Item getContainer() {
        return container;
    }

    public FoodComponent get() {
        return this.food.get();
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
