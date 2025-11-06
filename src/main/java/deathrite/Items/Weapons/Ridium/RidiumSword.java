package deathrite.Items.Weapons.Ridium;

import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.lootTable.presets.CloseRangeWeaponsLootTable;

// Extends SwordToolItem
public class RidiumSword extends SwordToolItem {

    // Weapon attack textures are loaded from resources/player/weapons/<itemStringID>

    public RidiumSword() {
        super(400, CloseRangeWeaponsLootTable.closeRangeWeapons);
        rarity = Rarity.RARE;
        attackAnimTime.setBaseValue(330); // ms attack time
        attackDamage.setBaseValue(85).setUpgradedValue(1, 85);
        attackRange.setBaseValue(100); // range
        knockback.setBaseValue(100); // knockback
    }
}