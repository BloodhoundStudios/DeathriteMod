package deathrite.Items.Weapons.Dev;

import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;

// Extends SwordToolItem
public class DevSword extends SwordToolItem {

    // Weapon attack textures are loaded from resources/player/weapons/<itemStringID>

    public DevSword() {
        super(400);
        rarity = Rarity.UNIQUE;
        attackAnimTime.setBaseValue(50); // Attack Time (ms)
        attackDamage.setBaseValue(9999) // Base sword damage
                .setUpgradedValue(1, 9999); // Upgraded tier 1 damage
        attackRange.setBaseValue(100); // 120 range
        knockback.setBaseValue(0); // 100 knockback
    }
}