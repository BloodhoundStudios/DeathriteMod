package deathrite.Items.Weapons.Aethium;

import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;

public class AethiumGreatsword extends GreatswordToolItem {
    public AethiumGreatsword() {
        super(900, getThreeChargeLevels(500, 600, 700));
        this.rarity = Rarity.EPIC;
        this.attackDamage.setBaseValue(135.0F).setUpgradedValue(1.0F, 160.0F);
        this.attackRange.setBaseValue(130);
        this.knockback.setBaseValue(150);
        this.attackXOffset = 12;
        this.attackYOffset = 14;
    }
}

