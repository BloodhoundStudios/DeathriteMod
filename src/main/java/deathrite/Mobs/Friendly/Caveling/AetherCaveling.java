package deathrite.Mobs.Friendly.Caveling;

import deathrite.DeathriteMod;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.critters.caveling.CavelingMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;

import java.awt.*;

public class AetherCaveling extends CavelingMob {
    public AetherCaveling() {
        super(900, 55);
    }

    public void init() {
        super.init();
        this.texture = DeathriteMod.AetherCavelingTexture;
        this.popParticleColor = new Color(144, 117, 58);
        this.singleRockSmallStringID = "aethercaverocksmall";
        if (this.item == null) {
            this.item = GameRandom.globalRandom.getOneOf(new InventoryItem("ridiumore", GameRandom.globalRandom.getIntBetween(12, 24)), new InventoryItem("aetherore", GameRandom.globalRandom.getIntBetween(3, 6)));
        }

    }

    public LootTable getLootTable() {
        return super.getLootTable();
    }

    public LootTable getCavelingDropsAsLootTable() {
        return new LootTable(new LootItem("ridiumore", 1), new LootItem("aetherore", 1));
    }
}
