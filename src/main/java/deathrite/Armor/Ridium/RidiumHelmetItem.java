//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Armor.Ridium;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.gameDamageType.DamageType;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

// Cheatsheet for spriting Helmet
// First/Top row is The Back
// 2nd row is The Right
// 3rd row is The Front
// Last/Bottom row is The Left
// The Boots can only fit in its 16x16 Square

public class RidiumHelmetItem extends SetHelmetArmorItem {
    public RidiumHelmetItem() {
        super(20, (DamageType)null, 1300, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.RARE, "ridiumhelmet", "ridiumchestplate", "ridiumboots", "ancientfossilhelmetsetbonus");
        this.hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.ATTACK_SPEED, 0.15F)});
    }
}