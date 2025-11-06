package deathrite.Items.Mounts;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.mountItem.MountItem;

public class HoverBoard2Mount extends MountItem {
    public HoverBoard2Mount() {
        super("hoverboardtwo");
        this.rarity = Item.Rarity.RARE;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "hoverboardtip"));
        tooltips.add(Localization.translate("itemtooltip", "hoverboardtip2"));
        return tooltips;
    }
}
