//package instahouse.presets;
//
//import necesse.engine.modLoader.annotations.ModMethodPatch;
//import necesse.entity.mobs.PlayerMob;
//import necesse.gfx.camera.GameCamera;
//import necesse.level.maps.Level;
//import necesse.level.maps.presets.Preset;
//import net.bytebuddy.asm.Advice;
//
//@ModMethodPatch(target = Preset.class, name = "applyToLevel", arguments = {Level.class, int.class, int.class})
//public class PresetMethodPatch2 {
//
//    /*
//        Other than printing debug messages, this is currently set up to override the loot table for surface grass.
//     */
//
//    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
//    static boolean onEnter(@Advice.This Preset presetObject, @Advice.Argument(0) Level level) {
//        System.out.println("Entered presetObject.applyToLevel: " + level.getIdentifier() + "\n" + presetObject.toString());
//        return false;
//    }
//}
