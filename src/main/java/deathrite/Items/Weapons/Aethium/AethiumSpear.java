package deathrite.Items.Weapons.Aethium;

import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.spearToolItem.SpearToolItem;

public class AethiumSpear extends SpearToolItem {
    public AethiumSpear() {
        super(1100);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(400);
        this.attackDamage.setBaseValue(42.0F).setUpgradedValue(1.0F, 65.0F);
        this.attackRange.setBaseValue(140);
        this.knockback.setBaseValue(35);
    }
}
