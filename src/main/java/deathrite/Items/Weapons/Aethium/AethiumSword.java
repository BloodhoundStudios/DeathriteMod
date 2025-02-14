package deathrite.Items.Weapons.Aethium;

import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;

public class AethiumSword extends SwordToolItem {
    public AethiumSword() {
        super(650);
        rarity = Rarity.EPIC;
        attackAnimTime.setBaseValue(300); // 300 ms attack time
        attackDamage.setBaseValue(90) // Base sword damage
                .setUpgradedValue(1, 90); // Upgraded tier 1 damage
        attackRange.setBaseValue(110); // 120 range
        knockback.setBaseValue(115); // 100 knockback
    }
}
