package krelox.corndelight.data;

import krelox.corndelight.CornDelight;
import krelox.corndelight.CornDelightTags;
import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.crafting.CookingPotBookCategory;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class CornDelightRecipes extends FabricRecipeProvider {
    public CornDelightRecipes(FabricDataOutput output,
                              CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {
            @Override
            public void generate() {
                var itemLookup = this.registries.getOrThrow(RegistryKeys.ITEM);

                offerSingleOutputShapelessRecipe(CornDelightItems.CORN_SEEDS, CornDelightItems.CORN, "corndelight");

                offerReversibleCompactingRecipes(RecipeCategory.FOOD, CornDelightItems.CORN,
                        RecipeCategory.FOOD, CornDelightBlocks.CORN_CRATE);

                offerReversibleCompactingRecipes(RecipeCategory.FOOD, CornDelightItems.CORN_SEEDS,
                        RecipeCategory.FOOD, CornDelightBlocks.CORN_KERNEL_BAG);

                offerFoodCookingRecipes(CornDelightItems.CORN, CornDelightItems.GRILLED_CORN);
                offerFoodCookingRecipes(CornDelightItems.CORN_SEEDS, CornDelightItems.POPCORN);
                offerFoodCookingRecipes(CornDelightItems.CORNBREAD_BATTER, CornDelightItems.CORNBREAD);
                offerFoodCookingRecipes(CornDelightItems.RAW_TORTILLA, CornDelightItems.TORTILLA);

                CookingPotRecipeBuilder.cookingPotRecipe(
                                itemLookup,
                                /* result */ CornDelightItems.BOILED_CORN,
                                /* resultCount */ 1,
                                /* cookTime */ 200,
                                /* exp */ 0.35f
                        )
                        .addIngredient(CornDelightItems.CORN)
                        .addIngredient(Items.WATER_BUCKET)
                        .unlockedBy(hasName(CornDelightItems.CORN), conditionsFromItem(CornDelightItems.CORN))
                        .setRecipeBookCategory(CookingPotBookCategory.MISC)
                        .build(exporter, getResourceLocation(CornDelightItems.BOILED_CORN));

                CookingPotRecipeBuilder.cookingPotRecipe(
                                itemLookup,
                                /* result */ CornDelightItems.CREAMED_CORN,
                                /* resultCount */ 1,
                                /* cookTime */ 200,
                                /* exp */ 0.35f,
                                /* container */ Items.BOWL
                        )
                        .addIngredient(CornDelightItems.CORN_SEEDS)
                        .addIngredient(CornDelightItems.CORN_SEEDS)
                        .addIngredient(CommonTags.FOODS_MILK)
                        .unlockedBy(hasName(CornDelightItems.CORN_SEEDS), conditionsFromItem(CornDelightItems.CORN_SEEDS))
                        .setRecipeBookCategory(CookingPotBookCategory.MISC)
                        .build(exporter, getResourceLocation(CornDelightItems.CREAMED_CORN));

                CookingPotRecipeBuilder.cookingPotRecipe(
                                itemLookup,
                                /* result */ CornDelightItems.CORN_SOUP,
                                /* resultCount */ 1,
                                /* cookTime */ 400,
                                /* exp */ 0.35f,
                                /* container */ Items.BOWL
                        )
                        .addIngredient(CornDelightItems.CORN_SEEDS)
                        .addIngredient(CommonTags.FOODS_MILK)
                        .addIngredient(CornDelightTags.Items.FOODS_TORTILLA_MEATS)
                        .addIngredient(CornDelightTags.Items.VEGETABLES)
                        .addIngredient(Items.BROWN_MUSHROOM)
                        .unlockedBy(hasName(CornDelightItems.CORN_SEEDS), conditionsFromItem(CornDelightItems.CORN_SEEDS))
                        .setRecipeBookCategory(CookingPotBookCategory.MISC)
                        .build(exporter, getResourceLocation(CornDelightItems.CORN_SOUP));

                CookingPotRecipeBuilder.cookingPotRecipe(
                                itemLookup,
                                /* result */ CornDelightItems.CREAMY_CORN_DRINK,
                                /* resultCount */ 1,
                                /* cookTime */ 400,
                                /* exp */ 0.35f,
                                /* container */ Items.GLASS_BOTTLE
                        )
                        .addIngredient(CornDelightItems.CORN_SEEDS)
                        .addIngredient(CommonTags.FOODS_MILK)
                        .addIngredient(Items.SUGAR)
                        .unlockedBy(hasName(CornDelightItems.CORN_SEEDS), conditionsFromItem(CornDelightItems.CORN_SEEDS))
                        .setRecipeBookCategory(CookingPotBookCategory.MISC)
                        .build(exporter, getResourceLocation(CornDelightItems.CREAMY_CORN_DRINK));

                CookingPotRecipeBuilder.cookingPotRecipe(
                                itemLookup,
                                /* result */ CornDelightItems.CORN_DOG,
                                /* resultCount */ 2,
                                /* cookTime */ 200,
                                /* exp */ 0.35f
                        )
                        .addIngredient(CornDelightItems.CORN_SEEDS)
                        .addIngredient(ModItems.MINCED_BEEF.get())
                        .addIngredient(Items.STICK)
                        .unlockedBy(hasName(CornDelightItems.CORN_SEEDS), conditionsFromItem(CornDelightItems.CORN_SEEDS))
                        .setRecipeBookCategory(CookingPotBookCategory.MISC)
                        .build(exporter, getResourceLocation(CornDelightItems.CORN_DOG));

                CookingPotRecipeBuilder.cookingPotRecipe(
                                itemLookup,
                                /* result */ CornDelightItems.CLASSIC_CORN_DOG,
                                /* resultCount */ 2,
                                /* cookTime */ 200,
                                /* exp */ 0.35f
                        )
                        .addIngredient(CornDelightItems.CORN_SEEDS)
                        .addIngredient(ModItems.MINCED_BEEF.get())
                        .addIngredient(ModItems.TOMATO_SAUCE.get())
                        .addIngredient(Items.STICK)
                        .unlockedBy(hasName(CornDelightItems.CORN_SEEDS), conditionsFromItem(CornDelightItems.CORN_SEEDS))
                        .setRecipeBookCategory(CookingPotBookCategory.MISC)
                        .build(exporter, getResourceLocation(CornDelightItems.CLASSIC_CORN_DOG));

                CookingPotRecipeBuilder.cookingPotRecipe(
                                itemLookup,
                                /* result */ CornDelightItems.CORNBREAD_STUFFING,
                                /* resultCount */ 2,
                                /* cookTime */ 200,
                                /* exp */ 0.35f
                        )
                        .addIngredient(CornDelightItems.CORNBREAD)
                        .addIngredient(CornDelightTags.Items.VEGETABLES)
                        .addIngredient(Items.BAKED_POTATO)
                        .addIngredient(Items.SWEET_BERRIES)
                        .unlockedBy(hasName(CornDelightItems.CORN_SEEDS), conditionsFromItem(CornDelightItems.CORN_SEEDS))
                        .setRecipeBookCategory(CookingPotBookCategory.MISC)
                        .build(exporter, getResourceLocation(CornDelightItems.CORNBREAD_STUFFING));

                CuttingBoardRecipeBuilder.cuttingRecipe(
                                Ingredient.ofItem(CornDelightItems.TORTILLA),
                                Ingredient.ofTag(itemLookup.getOrThrow(CommonTags.TOOLS_KNIFE)),
                                CornDelightItems.TORTILLA_CHIP,
                                2
                        )
                        .build(exporter, getResourceLocation(CornDelightItems.TORTILLA_CHIP));

                createShapeless(RecipeCategory.FOOD, CornDelightItems.CARAMEL_POPCORN)
                        .input(CornDelightItems.POPCORN)
                        .input(Items.SUGAR)
                        .criterion(hasItem(CornDelightItems.POPCORN), conditionsFromItem(CornDelightItems.POPCORN))
                        .offerTo(this.exporter);

                createShapeless(RecipeCategory.FOOD, CornDelightItems.CORNBREAD_BATTER, 3)
                        .input(CornDelightTags.Items.CORN)
                        .input(CornDelightTags.Items.CORN)
                        .input(CommonTags.FOODS_MILK)
                        .input(Items.EGG)
                        .criterion(hasItem(CornDelightItems.CORN), conditionsFromTag(CornDelightTags.Items.CORN))
                        .offerTo(this.exporter);

                createShapeless(RecipeCategory.FOOD, CornDelightBlocks.POPCORN_BOX)
                        .input(CornDelightItems.CARAMEL_POPCORN, 4)
                        .input(Items.PAPER)
                        .criterion(hasItem(CornDelightItems.CARAMEL_POPCORN), conditionsFromItem(CornDelightItems.CARAMEL_POPCORN))
                        .offerTo(this.exporter);

                createShapeless(RecipeCategory.FOOD, CornDelightItems.TACO)
                        .input(CornDelightItems.TORTILLA)
                        .input(CommonTags.CROPS_CABBAGE)
                        .input(CommonTags.CROPS_ONION)
                        .input(CornDelightTags.Items.FOODS_TORTILLA_MEATS)
                        .criterion(hasItem(CornDelightItems.TORTILLA), conditionsFromItem(CornDelightItems.TORTILLA))
                        .offerTo(this.exporter);

                createShaped(RecipeCategory.FOOD, CornDelightItems.RAW_TORTILLA, 3)
                        .input('#', CornDelightItems.CORN)
                        .input('W', ConventionalItemTags.WATER_BUCKETS)
                        .pattern("###")
                        .pattern(" W ")
                        .criterion(hasItem(CornDelightItems.CORN), conditionsFromTag(CornDelightTags.Items.CORN))
                        .offerTo(this.exporter);
            }

            private void offerFoodCookingRecipes(ItemConvertible input, ItemConvertible output) {
                offerFoodCookingRecipe("smelting", RecipeSerializer.SMELTING, SmeltingRecipe::new, 200, input, output, 0.5F);
                offerFoodCookingRecipe("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 600, input, output, 0.5F);
                offerFoodCookingRecipe("smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, input, output, 0.5F);
            }
        };
    }

    private static String hasName(ItemConvertible item) {
        return "has_" + Registries.ITEM.getId(item.asItem()).getPath();
    }

    public static Identifier getResourceLocation(Item item) {
        Identifier location = Registries.ITEM.getId(item);
        return Identifier.of(CornDelight.MODID, location.getPath());
    }

    @Override
    public @NotNull String getName() {
        return "Corn Delight Recipes";
    }
}
