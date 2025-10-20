package deathrite.Items.Weapons.Xaeron;

import necesse.inventory.item.toolItem.spearToolItem.SpearToolItem;
import necesse.inventory.lootTable.presets.SpearWeaponsLootTable;

public class XaeronSpear extends SpearToolItem {
    public XaeronSpear() {
        super(1100, SpearWeaponsLootTable.spearWeapons);
        this.rarity = Rarity.LEGENDARY;
        this.attackAnimTime.setBaseValue(400);
        this.attackDamage.setBaseValue(50.0F).setUpgradedValue(1.0F, 75.0F);
        this.attackRange.setBaseValue(140);
        this.knockback.setBaseValue(55);
    }
}
