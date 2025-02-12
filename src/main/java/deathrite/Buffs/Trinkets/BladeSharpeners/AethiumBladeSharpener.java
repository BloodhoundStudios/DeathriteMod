package deathrite.Buffs.Trinkets.BladeSharpeners;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class AethiumBladeSharpener extends TrinketBuff {
    public AethiumBladeSharpener() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MELEE_DAMAGE, .25F);
        buff.setModifier(BuffModifiers.MELEE_ATTACK_SPEED, .25F);
        buff.setModifier(BuffModifiers.MELEE_CRIT_CHANCE, .15F);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "aethiumbladesharpenertip"));
        return tooltips;
    }
}
