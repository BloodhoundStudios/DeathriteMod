package deathrite.Items.Weapons.Ridium;

import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import necesse.inventory.lootTable.presets.GreatswordWeaponsLootTable;

import java.awt.*;

public class RidiumGreatsword extends GreatswordToolItem {
    public RidiumGreatsword() {
        super(900, GreatswordWeaponsLootTable.greatswordWeapons, getThreeChargeLevels(500, 600, 700));
        this.rarity = Rarity.RARE;
        this.attackDamage.setBaseValue(130.0F).setUpgradedValue(1.0F, 155.0F);
        this.attackRange.setBaseValue(130);
        this.knockback.setBaseValue(150);
        this.attackXOffset = 12;
        this.attackYOffset = 14;
    }
}

