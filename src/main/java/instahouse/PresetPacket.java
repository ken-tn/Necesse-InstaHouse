package instahouse;

import instahouse.presets.InstaHousePreset;
import necesse.engine.network.NetworkPacket;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

import static necesse.level.maps.presets.PresetUtils.placeAndSendPresetToClients;

public class PresetPacket extends Packet {
    public final int playerSlot;
    public final int tileX;
    public final int tileY;
    public final String presetScript;

    // MUST HAVE - Used for construction in registry
    public PresetPacket(byte[] data) {
        super(data);
        PacketReader reader = new PacketReader(this);
        // Important that it's same order as written in
        playerSlot = reader.getNextByteUnsigned();
        tileX = reader.getNextInt();
        tileY = reader.getNextInt();
        presetScript = reader.getNextString();
    }

    // Client constructor - sends preset request to server
    public PresetPacket(Client client, int tileX, int tileY, String presetScript) {
        this.playerSlot = client.getSlot();
        this.tileX = tileX;
        this.tileY = tileY;
        this.presetScript = presetScript;

        PacketWriter writer = new PacketWriter(this);
        // Important that it's same order as read in
        writer.putNextByteUnsigned(playerSlot);
        writer.putNextInt(tileX);
        writer.putNextInt(tileY);
        writer.putNextString(presetScript);
    }

    @Override
    public void processServer(NetworkPacket packet, Server server, ServerClient client) {
        // Server receives preset request from client
        if (presetScript != null && !presetScript.isEmpty()) {
            String script = presetScript;

            // Verify the presetScript from server
            boolean useClientPreset = InstaHousePreset.IsAllowedPreset(client.getPermissionLevel());
            if (!useClientPreset) {
                script = InstaHouseMod.SettingsGetter.getString("server_preset");
            }

            placeAndSendPresetToClients(client.getServer(), new InstaHousePreset(script), client.getLevel(), tileX, tileY);
        }
    }
}