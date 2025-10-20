package deathrite.Overrides;

import necesse.engine.registries.ObjectRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.level.gameObject.container.CraftingStationUpgrade;
import necesse.level.gameObject.container.FallenWorkstationObject;

public class FallenWorkstationUpgrade extends FallenWorkstationObject {

    @Override
    public CraftingStationUpgrade getStationUpgrade() {
        return new CraftingStationUpgrade(ObjectRegistry.getObject("aethiumworkstation"), new Ingredient[]{new Ingredient("aethiumbar", 5), new Ingredient("skycore", 1)});
    }
}
