package deathrite.Items.LootBags;

import deathrite.DeathriteMod;
import lbml.Items.LootBags.Lootbag;

import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;

public class StarfangedLootBag extends Lootbag {
    public LootTable lootTable = new LootTable(new LootItem("starryfang", 1), new ChanceLootItem(0.01F, "staritehelmet", 1), new ChanceLootItem(0.01F, "staritechestplate", 1), new ChanceLootItem(0.01F, "stariteboots", 1), new ChanceLootItem(0.25F, DeathriteMod.EnragedStarfangedMusic.getStringID() + "vinyl", 1));

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }
}
