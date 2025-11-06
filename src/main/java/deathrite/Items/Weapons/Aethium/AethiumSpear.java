package deathrite.Items.Weapons.Aethium;

import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.spearToolItem.SpearToolItem;
import necesse.inventory.lootTable.presets.SpearWeaponsLootTable;

public class AethiumSpear extends SpearToolItem {
    public AethiumSpear() {
        super(1100, SpearWeaponsLootTable.spearWeapons);
        this.rarity = Rarity.RARE;
        this.attackAnimTime.setBaseValue(400);
        this.attackDamage.setBaseValue(42.0F).setUpgradedValue(1.0F, 65.0F);
        this.attackRange.setBaseValue(140);
        this.knockback.setBaseValue(35);
    }
}
