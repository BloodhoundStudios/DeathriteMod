package deathrite.Items.Weapons.Xaeron;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem.BoomerangToolItem;
import necesse.inventory.lootTable.presets.ThrowWeaponsLootTable;

public class CrystalizedXaeronBoomerang extends BoomerangToolItem {
    public CrystalizedXaeronBoomerang() {
        super(1500, ThrowWeaponsLootTable.throwWeapons, "crystalizedxaeronboomerang");
        this.rarity = Rarity.LEGENDARY;
        this.attackAnimTime.setBaseValue(300);
        this.attackDamage.setBaseValue(90.0F).setUpgradedValue(1.0F, 90.0F);
        this.attackRange.setBaseValue(650);
        this.velocity.setBaseValue(260);
        this.stackSize = 5;
        this.resilienceGain.setBaseValue(0.6F);
        this.knockback.setBaseValue(60);
        this.itemAttackerProjectileCanHitWidth = 18.0F;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "crystalizedxaeronboomerangtip"));
        return tooltips;
    }
}
