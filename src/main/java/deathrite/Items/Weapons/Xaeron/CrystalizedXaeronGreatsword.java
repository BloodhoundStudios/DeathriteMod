package deathrite.Items.Weapons.Xaeron;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.SlimeGreatswordAttackHandler;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import necesse.level.maps.Level;

public class CrystalizedXaeronGreatsword extends GreatswordToolItem {
    public CrystalizedXaeronGreatsword() {
        super(900, getThreeChargeLevels(500, 600, 700));
        this.rarity = Rarity.LEGENDARY;
        this.attackDamage.setBaseValue(160.0F).setUpgradedValue(1.0F, 185.0F);
        this.attackRange.setBaseValue(120);
        this.knockback.setBaseValue(160);
        this.attackXOffset = 12;
        this.attackYOffset = 14;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "slimegreatswordchargetip1"));
        tooltips.add(Localization.translate("itemtooltip", "slimegreatswordchargetip2"));
        return tooltips;
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        attackerMob.startAttackHandler(new SlimeGreatswordAttackHandler(attackerMob, slot, item, this, seed, x, y, this.chargeLevels));
        return item;
    }

    public GameSprite getAttackSprite(InventoryItem item, PlayerMob player) {
        int timePerFrame = 100;
        int spriteRes = this.attackTexture.getHeight();
        int sprites = this.attackTexture.getWidth() / spriteRes;
        int sprite = GameUtils.getAnim(player == null ? System.currentTimeMillis() : player.getLocalTime(), sprites, sprites * timePerFrame);
        return new GameSprite(this.attackTexture, sprite, 0, spriteRes);
    }
}

