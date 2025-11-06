package deathrite.Mobs.Friendly.NPCs;

import deathrite.DeathriteMod;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.packet.PacketOpenContainer;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameLootUtils;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.HumanShop;
import necesse.gfx.HumanLook;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.CountOfTicketLootItems;
import necesse.inventory.lootTable.lootItem.LootItem;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MysteryManHumanMob extends HumanShop {
    public MysteryManHumanMob() {
        super(999999999, 999999999, "mysteryman");
        this.look = new HumanLook();
        this.attackCooldown = 500;
        this.attackAnimTime = 500;
        this.setSwimSpeed(1.0F);
        this.canJoinAdventureParties = false;
    }

    public LootTable getLootTable() {
        return super.getLootTable();
    }

    public void setDefaultArmor(HumanDrawOptions drawOptions) {
        drawOptions.helmet(new InventoryItem("ridiumhelmet"));
        drawOptions.chestplate(new InventoryItem("ridiumchestplate"));
        drawOptions.boots(new InventoryItem("ridiumboots"));
    }

    protected ArrayList<GameMessage> getMessages(ServerClient client) {
        return this.getLocalMessages("mysterymantalk", 1);
    }

    public PacketOpenContainer getOpenShopPacket(Server server, ServerClient client) {
        return this.getShopContainerData(client).getPacket(DeathriteMod.MYSTERYMAN_CONTAINER, this);
    }

    public List<InventoryItem> getRecruitItems(ServerClient client) {
        if (this.isTrapped()) {
            return Collections.emptyList();
        } else {
            GameRandom random = new GameRandom((long)this.getSettlerSeed() * 89L);
            if (this.isVisitor()) {
                return Collections.singletonList(new InventoryItem("coin", random.getIntBetween(5, 10)));
            } else {
                LootTable secondItems = new LootTable(new LootItemInterface[]{new CountOfTicketLootItems(2, new Object[]{100, new LootItem("ridiumbar", Integer.MAX_VALUE)})});
                ArrayList<InventoryItem> out = GameLootUtils.getItemsValuedAt(random, random.getIntBetween(5, 10), (double)0.2F, new LootItem("coin", Integer.MAX_VALUE), new Object[0]);
                out.addAll(GameLootUtils.getItemsValuedAt(random, random.getIntBetween(1, 5), (double)0.2F, secondItems, new Object[0]));
                out.sort(Comparator.comparing(InventoryItem::getBrokerValue).reversed());
                return out;
            }
        }
    }
}
