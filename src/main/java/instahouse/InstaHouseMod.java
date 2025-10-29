package instahouse;

import instahouse.inventory.item.placeableItem.InstaHouseItem;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static necesse.inventory.recipe.Recipes.ingredientsFromScript;

@ModEntry
public class InstaHouseMod {

    public void init() {
        // Register our items
        ItemRegistry.registerItem("instahouse", new InstaHouseItem(10, true), 10, true);
    }

    public void initResources() {
        // Sometimes your textures will have a black or other outline unintended under rotation or scaling
        // This is caused by alpha blending between transparent pixels and the edge
        // To fix this, run the preAntialiasTextures gradle task
        // It will process your textures and save them again with a fixed alpha edge color

        // ExampleMob.texture = GameTexture.fromFile("mobs/examplemob");
    }

    public void postInit() {
        // Add recipes
        // Example item recipe, crafted in inventory for 2 iron bars
        Recipes.registerModRecipe(new Recipe(
                "instahouse",
                1,
                RecipeTechRegistry.WORKSTATION,
                ingredientsFromScript("{{anylog, 68}, {anystone, 254}, {wool, 20}, {torch, 3}}")
        ).showAfter("craftingguide")); // Show recipe after wood boat recipe
    }
}
