package deathrite.Items.Weapons.Starite;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem.BoomerangToolItem;

public class StariteBoomerang extends BoomerangToolItem {
    public StariteBoomerang() {
        super(1500, "stariteboomerang");
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(300);
        this.attackDamage.setBaseValue(75.0F).setUpgradedValue(1.0F, 75.0F);
        this.attackRange.setBaseValue(650);
        this.velocity.setBaseValue(240);
        this.stackSize = 5;
        this.resilienceGain.setBaseValue(0.5F);
        this.knockback.setBaseValue(55);
        this.itemAttackerProjectileCanHitWidth = 18.0F;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "stariteboomerangtip"));
        return tooltips;
    }
}
