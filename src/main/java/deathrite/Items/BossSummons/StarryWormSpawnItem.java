package deathrite.Items.BossSummons;

import deathrite.Biomes.Aether.AetherBiome;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.WorldDataRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.engine.world.World;
import necesse.engine.world.WorldEntity;
import necesse.engine.world.WorldEntityGameClock;
import necesse.engine.world.WorldSettings;
import necesse.engine.world.worldData.WorldData;
import necesse.engine.world.worldEvent.WorldEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;

public class StarryWormSpawnItem extends ConsumableItem {
    public StarryWormSpawnItem() {
        super(1, true);
        this.itemCooldownTime.setBaseValue(2000);
        this.setItemCategory(new String[]{"consumable", "bossitems"});
        this.dropsAsMatDeathPenalty = true;
        this.keyWords.add("boss");
        this.rarity = Rarity.LEGENDARY;
        this.worldDrawSize = 32;
        this.incinerationTimeMillis = 30000;
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        if (level instanceof IncursionLevel) {
            return "inincursion";
        } else {
            return level.getIslandDimension() == 0 && !(level.biome instanceof AetherBiome) ? "notaether" : null;
        }
    }

    public InventoryItem onAttemptPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader, String error) {
        if (level.isServer() && player != null && player.isServerClient() && error.equals("inincursion")) {
            player.getServerClient().sendChatMessage(new LocalMessage("misc", "cannotsummoninincursion"));
        }

        return super.onAttemptPlace(level, x, y, player, item, contentReader, error);
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        if (level.isServer()) {
            if (level instanceof IncursionLevel) {
                GameMessage summonError = ((IncursionLevel)level).canSummonBoss("starfangeddestroyer");
                if (summonError != null) {
                    if (player != null && player.isServerClient()) {
                        player.getServerClient().sendChatMessage(summonError);
                    }

                    return item;
                }
            }

            System.out.println("Starfanged Destroyer has been summoned at " + level.getIdentifier() + ".");
            float angle = (float) GameRandom.globalRandom.nextInt(360);
            float nx = (float)Math.cos(Math.toRadians((double)angle));
            float ny = (float)Math.sin(Math.toRadians((double)angle));
            float distance = 960.0F;
            // Night
            if(level.getWorldEntity().getDayTimeHour() >= 20) {
                System.out.println(level.getWorldEntity().getDayTimeHour());
                Mob mob = MobRegistry.getMob("enragedstarfangeddestroyer", level);
                level.entityManager.addMob(mob, (float) (player.getX() + (int) (nx * distance)), (float) (player.getY() + (int) (ny * distance)));
                level.getServer().network.sendToClientsWithEntity(new PacketChatMessage(new LocalMessage("misc", "bosssummon", "name", mob.getLocalization())), mob);
                if (level instanceof IncursionLevel) {
                    ((IncursionLevel) level).onBossSummoned(mob);
                }
            } else {
                System.out.println(level.getWorldEntity().getDayTimeHour());
                Mob mob = MobRegistry.getMob("starfangeddestroyer", level);
                level.entityManager.addMob(mob, (float) (player.getX() + (int) (nx * distance)), (float) (player.getY() + (int) (ny * distance)));
                level.getServer().network.sendToClientsWithEntity(new PacketChatMessage(new LocalMessage("misc", "bosssummon", "name", mob.getLocalization())), mob);
                if (level instanceof IncursionLevel) {
                    ((IncursionLevel) level).onBossSummoned(mob);
                }
            }
        }

        if (this.singleUse) {
            item.setAmount(item.getAmount() - 1);
        }

        return item;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "starrywormtip"));
        tooltips.add(Localization.translate("itemtooltip", "starrywormtip2"));
            return tooltips;
    }

    public String getTranslatedTypeName() {
        return Localization.translate("item", "relic");
    }
}
