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

public class AethiumSpellbook extends TrinketBuff {
    public AethiumSpellbook() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.addModifier(BuffModifiers.MAGIC_DAMAGE, 0.25F);
        buff.addModifier(BuffModifiers.MAGIC_ATTACK_SPEED, 0.25F);
        buff.addModifier(BuffModifiers.MAGIC_CRIT_CHANCE, 0.15F);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "aethiumspellbooktip"));
        return tooltips;
    }
}
