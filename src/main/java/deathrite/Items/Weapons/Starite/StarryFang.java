package deathrite.Items.Weapons.Starite;

import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;

public class StarryFang extends SwordToolItem {
    public StarryFang() {
        super(400);
        rarity = Rarity.EPIC;
        attackAnimTime.setBaseValue(600); // 300 ms attack time
        attackDamage.setBaseValue(130) // Base sword damage
                .setUpgradedValue(1, 130); // Upgraded tier 1 damage
        attackRange.setBaseValue(120); // 120 range
        knockback.setBaseValue(80); // 100 knockback
    }
}
