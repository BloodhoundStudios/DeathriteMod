package deathrite.Items.Weapons.Ridium;

import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;

// Extends SwordToolItem
public class RidiumSword extends SwordToolItem {

    // Weapon attack textures are loaded from resources/player/weapons/<itemStringID>

    public RidiumSword() {
        super(400);
        rarity = Rarity.EPIC;
        attackAnimTime.setBaseValue(330); // 300 ms attack time
        attackDamage.setBaseValue(85) // Base sword damage
                .setUpgradedValue(1, 90); // Upgraded tier 1 damage
        attackRange.setBaseValue(100); // 120 range
        knockback.setBaseValue(100); // 100 knockback
    }
}