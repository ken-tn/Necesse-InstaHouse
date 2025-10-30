package instahouse.inventory.item.placeableItem;

import instahouse.InstaHouseMod;
import instahouse.PresetPacket;
import instahouse.presets.InstaHousePreset;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.placeableItem.PlaceableItem;
import necesse.level.maps.Level;

public class InstaHouseItem extends PlaceableItem {
    public InstaHouseItem(int stackSize, boolean singleUse) {
        super(stackSize, singleUse);
    }

    @Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "instahousetip"));
        return tooltips;
    }

    @Override
    public void drawPlacePreview(Level level, int x, int y, GameCamera camera, PlayerMob player, InventoryItem item, PlayerInventorySlot slot) {
        if (level.isClient()) {
            InstaHousePreset HousePreset;
            Client client = level.getClient();
            if (InstaHousePreset.IsAllowedClientPreset(client)) {
                HousePreset = new InstaHousePreset(InstaHouseMod.SettingsGetter.getString("preset"));
            } else {
                HousePreset = new InstaHousePreset(InstaHouseMod.SettingsGetter.getString("server_preset"));
            }
            // for some reason we need to divide by 32
            HousePreset.drawPlacePreview(level, x / 32, y / 32, player, camera);
        }
    }

    @Override
    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent) {
        if (level.isClient()) {
            Client client = level.getClient();
            String script = InstaHouseMod.SettingsGetter.getString("server_preset");
            boolean useClientPreset = InstaHousePreset.IsAllowedClientPreset(client);
            if (useClientPreset) {
                script = InstaHouseMod.SettingsGetter.getString("preset");
            }

            // Send preset request to server
            client.network.sendPacket(new PresetPacket(
                    client,
                    x / 32,
                    y / 32,
                    script,
                    useClientPreset
            ));

            SoundManager.playSound(GameResources.tap, SoundEffect.effect((float) (x + 16), (float) (y + 16)));
        }

        if (this.isSingleUse(player)) {
            item.setAmount(item.getAmount() - 1);
        }

        return item;
    }
}
