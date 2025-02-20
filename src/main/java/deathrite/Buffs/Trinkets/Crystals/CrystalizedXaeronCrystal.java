package deathrite.Buffs.Trinkets.Crystals;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class CrystalizedXaeronCrystal extends TrinketBuff {
    public CrystalizedXaeronCrystal() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.addModifier(BuffModifiers.SUMMON_DAMAGE, 0.35F);
        buff.addModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.35F);
        buff.addModifier(BuffModifiers.SUMMON_CRIT_CHANCE, 0.25F);
        buff.addModifier(BuffModifiers.MAX_SUMMONS, 3);
        buff.addModifier(BuffModifiers.SUMMONS_TARGET_RANGE, 0.10F);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "crystalizedxaeroncrystaltip"));
        return tooltips;
    }
}
