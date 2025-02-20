package deathrite.Buffs.Trinkets.Spellbooks;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class StariteSpellbook extends TrinketBuff {
    public StariteSpellbook() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.addModifier(BuffModifiers.MAGIC_DAMAGE, 0.27F);
        buff.addModifier(BuffModifiers.MAGIC_ATTACK_SPEED, 0.27F);
        buff.addModifier(BuffModifiers.MAGIC_CRIT_CHANCE, 0.20F);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "staritespellbooktip"));
        return tooltips;
    }
}
