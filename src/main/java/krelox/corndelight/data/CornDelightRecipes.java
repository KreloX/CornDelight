package krelox.corndelight.data;

import krelox.corndelight.CornDelightTags;
import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;

import java.util.function.Consumer;

public class CornDelightRecipes extends FabricRecipeProvider {
    public CornDelightRecipes(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerSingleOutputShapelessRecipe(exporter, CornDelightItems.CORN_SEEDS, CornDelightItems.CORN, null);

        offerReversibleCompactingRecipes(exporter, CornDelightItems.CORN, CornDelightBlocks.CORN_CRATE);

        offerReversibleCompactingRecipes(exporter, CornDelightItems.CORN_SEEDS, CornDelightBlocks.CORN_KERNEL_BAG);

        offerCookingRecipes(exporter, CornDelightItems.CORN, CornDelightItems.GRILLED_CORN);
        offerCookingRecipes(exporter, CornDelightItems.CORN_SEEDS, CornDelightItems.POPCORN);
        offerCookingRecipes(exporter, CornDelightItems.CORNBREAD_BATTER, CornDelightItems.CORNBREAD);
        offerCookingRecipes(exporter, CornDelightItems.RAW_TORTILLA, CornDelightItems.TORTILLA);

        ShapelessRecipeJsonBuilder.create(CornDelightItems.CARAMEL_POPCORN)
                .input(CornDelightItems.POPCORN)
                .input(Items.SUGAR)
                .criterion(hasItem(CornDelightItems.POPCORN), conditionsFromItem(CornDelightItems.POPCORN))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(CornDelightItems.CORNBREAD_BATTER, 3)
                .input(CornDelightTags.Items.CORN)
                .input(CornDelightTags.Items.CORN)
                .input(CornDelightTags.Items.MILK)
                .input(CornDelightTags.Items.EGGS)
                .criterion(hasItem(CornDelightItems.CORN), conditionsFromTag(CornDelightTags.Items.CORN))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(CornDelightBlocks.POPCORN_BOX)
                .input(CornDelightItems.CARAMEL_POPCORN, 4)
                .input(Items.PAPER)
                .criterion(hasItem(CornDelightItems.CARAMEL_POPCORN), conditionsFromItem(CornDelightItems.CARAMEL_POPCORN))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(CornDelightItems.TACO)
                .input(CornDelightItems.TORTILLA)
                .input(CornDelightTags.Items.CABBAGE)
                .input(CornDelightTags.Items.ONION)
                .input(CornDelightTags.Items.CORN_DELIGHT_MEAT)
                .criterion(hasItem(CornDelightItems.TORTILLA), conditionsFromItem(CornDelightItems.TORTILLA))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(CornDelightItems.RAW_TORTILLA, 3)
                .input('#', CornDelightItems.CORN).input('W', ConventionalItemTags.WATER_BUCKETS)
                .pattern("###")
                .pattern(" W ")
                .criterion(hasItem(CornDelightItems.CORN), conditionsFromTag(CornDelightTags.Items.CORN))
                .offerTo(exporter);
    }

    private static void offerCookingRecipes(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemConvertible output) {
        offerCookingRecipe(exporter, "smelting", RecipeSerializer.SMELTING, 200, input, output, 0.5F);
        offerCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, 600, input, output, 0.5F);
        offerCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, 100, input, output, 0.5F);
    }
}
