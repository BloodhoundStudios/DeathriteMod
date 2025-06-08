package deathrite.Items.BossSummons;

import deathrite.Biomes.Aether.AetherBiome;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;

public class EnragedStarryWormSpawnItem extends ConsumableItem {
    public EnragedStarryWormSpawnItem() {
        super(1, true);
        this.itemCooldownTime.setBaseValue(2000);
        this.setItemCategory(new String[]{"consumable", "bossitems"});
        this.dropsAsMatDeathPenalty = true;
        this.keyWords.add("boss");
        this.rarity = Rarity.LEGENDARY;
        this.worldDrawSize = 32;
        this.incinerationTimeMillis = 30000;
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, GNDItemMap mapContent) {
        if (level instanceof IncursionLevel) {
            return "inincursion";
        } else {
            return level.getIslandDimension() == 1 && !(level.biome instanceof AetherBiome) ? "notaether" : null;
        }
    }

    public InventoryItem onAttemptPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, GNDItemMap contentReader, String error) {
        if (level.isServer() && player != null && player.isServerClient() && error.equals("inincursion")) {
            player.getServerClient().sendChatMessage(new LocalMessage("misc", "cannotsummoninincursion"));
        }

        return super.onAttemptPlace(level, x, y, player, item, contentReader, error);
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent) {
        if (level.isServer()) {
            if (level instanceof IncursionLevel) {
                GameMessage summonError = ((IncursionLevel) level).canSummonBoss("enragedstarfangeddestroyer");
                if (summonError != null) {
                    if (player != null && player.isServerClient()) {
                        player.getServerClient().sendChatMessage(summonError);
                    }

                    return item;
                }
            }
            if (level.getIslandDimension() == 10) {
                System.out.println("Starfanged Destroyer has been summoned at " + level.getIdentifier() + ".");
                float angle = (float) GameRandom.globalRandom.nextInt(360);
                float nx = (float) Math.cos(Math.toRadians((double) angle));
                float ny = (float) Math.sin(Math.toRadians((double) angle));
                float distance = 960.0F;
                // Night
                if (level.getWorldEntity().getDayTimeHour() >= 20 || level.getWorldEntity().getDayTimeHour() <= 7) {
                    System.out.println(level.getWorldEntity().getDayTimeHour());
                    Mob mob = MobRegistry.getMob("enragedstarfangeddestroyer", level);
                    level.entityManager.addMob(mob, (float) (player.getX() + (int) (nx * distance)), (float) (player.getY() + (int) (ny * distance)));
                    level.getServer().network.sendToClientsWithEntity(new PacketChatMessage(new LocalMessage("misc", "bosssummon", "name", mob.getLocalization())), mob);
                    if (level instanceof IncursionLevel) {
                        ((IncursionLevel) level).onBossSummoned(mob);
                    }
                }
            }
        }

        return item;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "enragedstarrywormtip"));
        tooltips.add(Localization.translate("itemtooltip", "enragedstarrywormtip2"));
            return tooltips;
    }

    public String getTranslatedTypeName() {
        return Localization.translate("item", "relic");
    }
}
