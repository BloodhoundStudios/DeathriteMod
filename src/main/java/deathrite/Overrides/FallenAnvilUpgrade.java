package deathrite.Overrides;

import necesse.engine.registries.ObjectRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.level.gameObject.container.CraftingStationUpgrade;
import necesse.level.gameObject.container.FallenAnvilObject;

public class FallenAnvilUpgrade extends FallenAnvilObject {

    @Override
    public CraftingStationUpgrade getStationUpgrade() {
        return new CraftingStationUpgrade(ObjectRegistry.getObject("aethiumanvil"), new Ingredient[]{new Ingredient("aethiumbar", 6), new Ingredient("skycore", 1)});
    }
}
