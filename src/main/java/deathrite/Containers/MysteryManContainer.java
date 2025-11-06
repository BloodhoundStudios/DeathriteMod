//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Containers;

import deathrite.Mobs.Friendly.NPCs.MysteryManHumanMob;
import necesse.engine.network.NetworkClient;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketDeath;
import necesse.engine.registries.ContainerRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.ShopContainerData;
import necesse.gfx.GameResources;
import necesse.inventory.InventoryItem;
import necesse.inventory.container.customAction.ContentCustomAction;
import necesse.inventory.container.mob.ShopContainer;

public class MysteryManContainer extends ShopContainer {
    public final ContentCustomAction giveButton;
    public MysteryManHumanMob MysteryManMob;
    public long costSeed;

    public MysteryManContainer(final NetworkClient client, int uniqueSeed, MysteryManHumanMob mob, Packet contentPacket, ShopContainerData serverData) {
        super(client, uniqueSeed, mob, contentPacket, serverData);
        this.MysteryManMob = mob;
        this.costSeed = mob.getShopSeed();
        this.giveButton = (ContentCustomAction)this.registerAction(new ContentCustomAction() {
            protected void run(Packet content) {
                PacketReader reader = new PacketReader(content);
                GNDItemMap gndData = new GNDItemMap(reader);
                int amount = reader.getNextShortUnsigned();
                System.out.println("Scanning Inventory");
                if (MysteryManContainer.this.scanInventory(gndData, mob) > 0) {
                    System.out.println("Item Bought");
                    SoundManager.playSound(GameResources.coins, SoundEffect.effect(client.playerMob));
                    mob.remove(0,0, null, true);
                }
            }
        });
    }

    public int scanInventory(GNDItemMap gndData, MysteryManHumanMob mob) {

        System.out.println("Scanning for Essence of the gods");

        if(this.client.playerMob.getInv().getAmount(ItemRegistry.getItem("essenceofthegods"), false, false, false, false, "buy") > 0) {
            System.out.println("Item Found!");
            InventoryItem item = new InventoryItem("deathrite_stardust");
            item.setGndData(gndData);
            this.client.playerMob.getInv().main.addItem(this.client.playerMob.getLevel(), this.client.playerMob, new InventoryItem(ItemRegistry.getItem("deathrite_stardust"), 10, false), "buy");
            this.client.playerMob.getInv().main.removeItems(this.client.playerMob.getLevel(), this.client.playerMob, ItemRegistry.getItem("essenceofthegods"), 250, "buy");
            return 1;
        }
        return 0;
    }

    public boolean canGive(GNDItemMap gndData) {
        return true;
    }
}
