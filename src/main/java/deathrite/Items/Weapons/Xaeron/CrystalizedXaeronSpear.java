package deathrite.Items.Weapons.Xaeron;

import necesse.inventory.item.toolItem.spearToolItem.SpearToolItem;

public class CrystalizedXaeronSpear extends SpearToolItem {
    public CrystalizedXaeronSpear() {
        super(1100);
        this.rarity = Rarity.LEGENDARY;
        this.attackAnimTime.setBaseValue(450);
        this.attackDamage.setBaseValue(60.0F).setUpgradedValue(1.0F, 85.0F);
        this.attackRange.setBaseValue(150);
        this.knockback.setBaseValue(65);
    }
}
