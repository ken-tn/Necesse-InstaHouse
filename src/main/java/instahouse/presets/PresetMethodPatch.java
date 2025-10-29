//package instahouse.presets;
//
//import necesse.engine.modLoader.annotations.ModMethodPatch;
//import necesse.entity.mobs.PlayerMob;
//import necesse.gfx.camera.GameCamera;
//import necesse.inventory.lootTable.LootTable;
//import necesse.inventory.lootTable.lootItem.LootItem;
//import necesse.level.gameObject.SurfaceGrassObject;
//import necesse.level.maps.Level;
//import necesse.level.maps.presets.Preset;
//import net.bytebuddy.asm.Advice;
//
///**
// * The game uses Byte Buddy to allow mods to make patches inside the source code.
// * The class you assign as a mod patch acts as the advice class in Byte Buddy.
// * Use @ModMethodPatch to define which class and method to apply the patch to.
// * Define an optional priority if you want some patch to happen before another (higher priority means happens first)
// *
// * You can read the official documentation here:
// * <a href="https://javadoc.io/doc/net.bytebuddy/byte-buddy/1.12.8/net/bytebuddy/asm/Advice.html">...</a>
// *
// * I have written a brief overview of some of the most important annotations:
// *
// * "@Advice.OnMethodEnter" - The annotated method happens when we enter the method, before the methods
// *      content is run.
// *      Combine this with onSkip = Advice.OnNonDefaultValue.class and return true to skip the original
// *      method's execution. Make sure the annotated method returns something for this to work. See the example below.
// *      If you do not plan to ever skip the original method, the annotated OnMethodEnter method
// *      does not have to return anything (it can be a void method).
// *
// * "@Advice.OnMethodExit" - The annotate method happens when we exit the method, after the method has
// *      returned what it should.
// *      This returned object can be overridden using the "@Advice.Return(readOnly = false)" for a parameter
// *
// * "@Advice.This" - The annotated parameter is mapped to the object running, similar to "this" in java.
// *
// * "@Advice.FieldValue" - The annotated parameter is mapped to a field in the scope of the method.
// *      This could for example be a private field, or something similar.
// *      Setting "readOnly = false" on this indicates being able to write a value to the field.
// *
// * "@Advice.AllArguments" - The annotated parameter is assigned all arguments passed into the target method.
// *      This annotated parameter must be an array type
// *
// * "@Advice.Argument(n)" - The annotated parameter is mapped to the n argument passed into the target method.
// */
//@ModMethodPatch(target = Preset.class, name = "drawPlacePreview", arguments = {Level.class, int.class, int.class, PlayerMob.class, GameCamera.class})
//public class PresetMethodPatch {
//
//    /*
//        Other than printing debug messages, this is currently set up to override the loot table for surface grass.
//     */
//
//    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
//    static boolean onEnter(@Advice.This Preset presetObject, @Advice.Argument(0) Level level, @Advice.Argument(1) int x, @Advice.Argument(2) int y, @Advice.Argument(3) PlayerMob player, @Advice.Argument(4) GameCamera camera) {
//        System.out.println("Entered presetObject.drawPlacePreview: " + level.getIdentifier() + "\n" + presetObject.toString());
//        System.out.println(x + " " + y + "\n" + player + "\n" + camera);
//        return false;
//    }
//}
