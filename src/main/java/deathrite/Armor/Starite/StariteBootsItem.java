package deathrite.Armor.Starite;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

// Cheatsheet for spriting Boots
// First/Top row is The Back
// 2nd row is The Right
// 3rd row is The Front
// Last/Bottom row is The Left
// The Boots can only fit in its 16x16 Square

public class StariteBootsItem extends BootsArmorItem {
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.2F).setUpgradedValue(1.0F, 0.25F);

    public StariteBootsItem() {
        super(20, 1300, Rarity.LEGENDARY, "");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.SPEED, this.speed.getValue(this.getUpgradeTier(item)))});
    }
}
