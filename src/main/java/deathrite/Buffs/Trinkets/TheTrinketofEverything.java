package deathrite.Buffs.Trinkets;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class TheTrinketofEverything extends TrinketBuff {
    public TheTrinketofEverything() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.addModifier(BuffModifiers.RANGED_DAMAGE, 0.35F);
        buff.addModifier(BuffModifiers.RANGED_ATTACK_SPEED, 0.35F);
        buff.addModifier(BuffModifiers.RANGED_CRIT_CHANCE, 0.25F);
        buff.addModifier(BuffModifiers.MELEE_DAMAGE, 0.35F);
        buff.addModifier(BuffModifiers.MELEE_ATTACK_SPEED, 0.35F);
        buff.addModifier(BuffModifiers.MELEE_CRIT_CHANCE, 0.25F);
        buff.addModifier(BuffModifiers.SUMMON_DAMAGE, 0.35F);
        buff.addModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.35F);
        buff.addModifier(BuffModifiers.SUMMON_CRIT_CHANCE, 0.25F);
        buff.addModifier(BuffModifiers.MAX_SUMMONS, 3);
        buff.addModifier(BuffModifiers.SUMMONS_TARGET_RANGE, 0.10F);
        buff.addModifier(BuffModifiers.MAGIC_DAMAGE, 0.35F);
        buff.addModifier(BuffModifiers.MAGIC_ATTACK_SPEED, 0.35F);
        buff.addModifier(BuffModifiers.MAGIC_CRIT_CHANCE, 0.25F);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "thetrinketofeverythingtip"));
        return tooltips;
    }
}
