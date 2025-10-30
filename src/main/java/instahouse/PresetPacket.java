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
import static necesse.level.maps.presets.PresetUtils.placePresetFromClient;

public class PresetPacket extends Packet {
    public final int playerSlot;
    public final int tileX;
    public final int tileY;
    public final String presetScript;
    public final boolean useClientPreset;

    // MUST HAVE - Used for construction in registry
    public PresetPacket(byte[] data) {
        super(data);
        PacketReader reader = new PacketReader(this);
        // Important that it's same order as written in
        playerSlot = reader.getNextByteUnsigned();
        tileX = reader.getNextInt();
        tileY = reader.getNextInt();
        presetScript = reader.getNextString();
        useClientPreset = reader.getNextBoolean();
    }

    // Client constructor - sends preset request to server
    public PresetPacket(Client client, int tileX, int tileY, String presetScript, boolean useClientPreset) {
        this.playerSlot = client.getSlot();
        this.tileX = tileX;
        this.tileY = tileY;
        this.presetScript = presetScript;
        this.useClientPreset = useClientPreset;

        PacketWriter writer = new PacketWriter(this);
        // Important that it's same order as read in
        writer.putNextByteUnsigned(playerSlot);
        writer.putNextInt(tileX);
        writer.putNextInt(tileY);
        writer.putNextString(presetScript);
        writer.putNextBoolean(useClientPreset);
    }

    // Server constructor - sends preset to clients
    public PresetPacket(ServerClient client, int tileX, int tileY, String presetScript, boolean useClientPreset) {
        this.playerSlot = client.slot;
        this.tileX = tileX;
        this.tileY = tileY;
        this.useClientPreset = false;
        this.presetScript = presetScript;

        PacketWriter writer = new PacketWriter(this);
        writer.putNextByteUnsigned(playerSlot);
        writer.putNextInt(tileX);
        writer.putNextInt(tileY);
        writer.putNextString(presetScript);
        writer.putNextBoolean(useClientPreset);
    }

    @Override
    public void processClient(NetworkPacket packet, Client client) {
        // Client receives this when server sends preset to place
        if (!useClientPreset && presetScript != null && !presetScript.isEmpty()) {
            // Create and place the preset on client side
            InstaHousePreset housePreset = new InstaHousePreset(presetScript);
            placePresetFromClient(client, housePreset, tileX, tileY);
        }
    }

    @Override
    public void processServer(NetworkPacket packet, Server server, ServerClient client) {
        // Server receives preset request from client
        if (useClientPreset && presetScript != null && !presetScript.isEmpty()) {
            // Create the preset and place it
            InstaHousePreset housePreset = new InstaHousePreset(presetScript);

            placeAndSendPresetToClients(client.getServer(), housePreset, client.getLevel(), tileX, tileY);
        }
    }
}