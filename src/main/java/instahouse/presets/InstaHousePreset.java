package instahouse.presets;

import instahouse.InstaHouseMod;
import necesse.engine.commands.PermissionLevel;
import necesse.level.maps.presets.Preset;

public class InstaHousePreset extends Preset {
    public static String DefaultHousePreset = "eNrFVMtOwzAQ_BV_gA9JmjbNoSfgwAkESBxQD06ybUxNFtkuOSD-He-6Ka1KK14S0iaKZ8beya7t65uL24u72WuvG9_O0kS2oJetpy-vDVyeu9nDKJPC9erp2XnsoLK6Xi0MopUilaLR1pNSipEUS6uci6NcCnwBu7TYdzvwOCyluoYGc85A6_PcU5F94flT2fg_ZOmfymLp0iHGQxCXzyVWj1B77m8ihdKhm_k0UKhWdWgQGFVZFWZOytBjgNj7XhkjRVYWUni0dRv4PMx2HfbMN7wr8jIkds92XUMFHanyMt9DyEI53kKIK9eCWRA6GdBaVboDH7DJlG09YbM2ynpV8f4iY2S2jd6LCQ8bXAe6goagYh9aIPqU8Ok-Tm6K8lCbDVUKNeI6fP_FtjhhsqkLl4J_PuHY1RYbI8lBfKiSI_GJgvt0SsM15PJy6zcgteBH-X6h-MorjzPm0qJXXmNHbYnrcCQ7u32LZBxbavuX6UBlnxk7hn-XjUlHw3D065XT4-yJ-5M8xNv2Ku7nMwPKzrxdg6QjfQ412kOKT9oRrqbPK9-Cvdc2XOILZRy8vQNE03NM";

    public InstaHousePreset() {
        super(
                // Are client presets enabled?
                (InstaHouseMod.SettingsGetter.getBoolean("client_preset_allowed") ?
                        // Only moderators can change their client preset?
                        (InstaHouseMod.SettingsGetter.getBoolean("moderators_only") ?
                                // If permission is at least moderator, use their client preset.
                                InstaHouseMod.SERVER.getLocalClient().getPermissionLevel().getLevel() > 1 ? InstaHouseMod.SettingsGetter.getString("preset") : InstaHouseMod.SettingsGetter.getString("server_preset")
                                : InstaHouseMod.SettingsGetter.getString("server_preset"))
                        : InstaHouseMod.SettingsGetter.getString("server_preset"))
        );
        System.out.println("Permission: " + InstaHouseMod.SERVER.getLocalClient().getPermissionLevel() + " lvl: " + InstaHouseMod.SERVER.getLocalClient().getPermissionLevel().getLevel());
    }
}
