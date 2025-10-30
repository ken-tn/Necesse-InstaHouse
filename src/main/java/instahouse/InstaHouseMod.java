package instahouse;

import customsettingslib.settings.CustomModSettings;
import customsettingslib.settings.CustomModSettingsGetter;
import instahouse.inventory.item.placeableItem.InstaHouseItem;
import instahouse.presets.InstaHousePreset;
import necesse.engine.GameEventListener;
import necesse.engine.GameEvents;
import necesse.engine.events.ServerStartEvent;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.network.server.Server;
import necesse.engine.registries.*;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

import static necesse.inventory.recipe.Recipes.ingredientsFromScript;

@ModEntry
public class InstaHouseMod {
    public static Server SERVER;

    public void init() {
        // Register ServerStartEvent event listener to obtain server instance
        GameEvents.addListener(ServerStartEvent.class, new GameEventListener<ServerStartEvent>() {
            @Override
            public void onEvent(ServerStartEvent e) {
                // Setting server instance to static variable SERVER, so it can be accessible everywhere in code
                InstaHouseMod.SERVER = e.server;
            }
        });

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
        ).showAfter("craftingguide"));
    }

    // Initialize settings from CustomSettingsLib
    public static CustomModSettingsGetter SettingsGetter;
    // Generated from preset tool (creative)
    public ModSettings initSettings() {
        CustomModSettings customModSettings = new CustomModSettings()
                .addTextSeparator("Settings")
                .addStringSetting("preset", InstaHousePreset.DefaultHousePreset, 0, false)

                .addSpace(8)

                .addTextSeparator("Server Settings")
                .addBooleanSetting("client_preset_allowed", true)
                .addBooleanSetting("moderators_only", true)
                .addStringSetting("server_preset", InstaHousePreset.DefaultHousePreset, 0, false);

        customModSettings.addServerSettings("client_preset_allowed", "server_preset");

        SettingsGetter = customModSettings.getGetter();

        return customModSettings;
    }
}
