package deathrite.Patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.registries.ObjectRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.level.gameObject.container.CraftingStationUpgrade;
import necesse.level.gameObject.container.FallenWorkstationObject;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(
        target = FallenWorkstationObject.class,
        name = "getStationUpgrade",
        arguments = {}
)

public class FallenWorkstationPatch {
    @Advice.OnMethodExit()
    static void onExit(@Advice.This FallenWorkstationObject type, @Advice.Return(readOnly = false) CraftingStationUpgrade out) {
        out = new CraftingStationUpgrade(ObjectRegistry.getObject("aethiumworkstation"), new Ingredient[]{new Ingredient("aethiumbar", 5), new Ingredient("skycore", 1)});
    }
}
