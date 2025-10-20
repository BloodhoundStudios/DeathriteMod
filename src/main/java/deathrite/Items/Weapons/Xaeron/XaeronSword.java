package deathrite.Items.Weapons.Xaeron;

import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.lootTable.presets.CloseRangeWeaponsLootTable;

public class XaeronSword extends SwordToolItem {
    public XaeronSword() {
        super(400, CloseRangeWeaponsLootTable.closeRangeWeapons);
        rarity = Rarity.LEGENDARY;
        attackAnimTime.setBaseValue(300); // 300 ms attack time
        attackDamage.setBaseValue(90) // Base sword damage
                .setUpgradedValue(1, 90); // Upgraded tier 1 damage
        attackRange.setBaseValue(120); // 120 range
        knockback.setBaseValue(80); // 100 knockback
    }
}
