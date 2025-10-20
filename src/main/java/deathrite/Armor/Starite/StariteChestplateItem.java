//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Armor.Starite;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.lootTable.presets.BodyArmorLootTable;

// Cheatsheet for spriting Chestplate and Arms
// First/Top row is The Back L | is The Left R
// 2nd row is The Right L | is The Right R
// 3rd row is The Front L | is The Front R
// Last/Bottom row is The Left L | is The Back R
// The Arms can only fit in its 8x16 Rectangle
// The Chestplate can only fit in its 16x16 Square
// This Cheatsheet can also be applied to The Boots and Helmet

public class StariteChestplateItem extends ChestArmorItem {
    public StariteChestplateItem() {
        super(30, 1300, Rarity.LEGENDARY, "", "", BodyArmorLootTable.bodyArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.ALL_DAMAGE, 0.1F)});
    }
}