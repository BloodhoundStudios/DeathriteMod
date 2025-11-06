package deathrite.Biomes.Sky;

import deathrite.DeathriteMod;
import necesse.engine.AbstractMusicList;
import necesse.engine.MusicList;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.GameMusic;
import necesse.engine.sound.SoundSettings;
import necesse.engine.sound.SoundSettingsRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.biomeGenerator.BiomeGeneratorStack;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.lootTable.LootTable;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.MobSpawnTable;
import necesse.level.maps.regionSystem.Region;

public class SkyBiome extends Biome {
    public static MobSpawnTable surfaceMobs;
    public static MobSpawnTable caveMobs;
    public static MobSpawnTable deepDesertCaveMobs;
    public static MobSpawnTable surfaceCritters;
    public static MobSpawnTable caveCritters;
    public static MobSpawnTable deepCaveCritters;

    public SoundSettings getWindSound(Level level) {
        return SoundSettingsRegistry.windDesert;
    }

    public boolean canRain(Level level) {
        return false;
    }

    public GameTile getUnderLiquidTile(Level level, int tileX, int tileY) {
        return level.isCave ? TileRegistry.getTile(TileRegistry.dirtID) : TileRegistry.getTile(TileRegistry.sandID);
    }

    public int getGenerationTerrainTileID() {
        return DeathriteMod.SKY_CLOUD;
    }

    public int getGenerationBeachTileID() {
        return DeathriteMod.SKY_CLOUD;
    }

    public int getGenerationCaveRockObjectID() {
        return DeathriteMod.SPACE_ROCK;
    }

    public int getGenerationDeepCaveRockObjectID() {
        return DeathriteMod.UPGRADED_SPACE_ROCK;
    }

    public int getGenerationCaveTileID() { return DeathriteMod.SPACE_FLOOR; }

    public int getGenerationDeepCaveTileID() { return DeathriteMod.SPACE_FLOOR; }

    public void initializeGeneratorStack(BiomeGeneratorStack stack) {
        super.initializeGeneratorStack(stack);
        stack.addRandomVeinsBranch("stariteOreVein", 0.16F, 4, 8, 0.5F, 2, false);
        stack.addRandomVeinsBranch("stariteDeepOreVein", 0.16F, 2, 4, 0.25F, 2, false);
        stack.addRandomVeinsBranch("xaeronOreVein", 0.16F, 4, 8, 0.5F, 2, false);
    }

    public void generateRegionCaveTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionCaveTerrain(region, stack, random);
        stack.startPlaceOnVein(this, region, random, "stariteOreVein").onlyOnObject(DeathriteMod.SPACE_ROCK).placeObjectForced("stariteoreobject");
    }

    public void generateRegionDeepCaveTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionDeepCaveTerrain(region, stack, random);
        stack.startPlace(this, region, random).chance((double) 0.005F).placeObject("deepcaverock");
        stack.startPlace(this, region, random).chance((double) 0.03F).placeCrates(new String[]{"crate"});
        stack.startPlaceOnVein(this, region, random, "stariteDeepOreVein").onlyOnObject(DeathriteMod.UPGRADED_SPACE_ROCK).placeObjectForced("stariteoreobject");
        stack.startPlaceOnVein(this, region, random, "xaeronOreVein").onlyOnObject(DeathriteMod.UPGRADED_SPACE_ROCK).placeObjectForced("xaeronoreobject");
    }

    public MobSpawnTable getMobSpawnTable(Level level) {
        if (!level.isCave) {
            return surfaceMobs;
        } else {
            return level.getIslandDimension() == -2 ? deepDesertCaveMobs : caveMobs;
        }
    }

    public MobSpawnTable getCritterSpawnTable(Level level) {
        if (level.isCave) {
            return level.getIslandDimension() == -2 ? deepCaveCritters : caveCritters;
        } else {
            return surfaceCritters;
        }
    }

    public AbstractMusicList getLevelMusic(Level level, PlayerMob perspective) {
        return new MusicList(new GameMusic[]{DeathriteMod.SkyMusic});
    }

    public LootTable getExtraBiomeMobDrops(Mob mob) {
        LootTable lootTable = new LootTable();
        if (mob.isHostile && !mob.isBoss() && !mob.isSummoned) {
            if (mob.getLevel().getIdentifier().equals(LevelIdentifier.CAVE_IDENTIFIER)) {
                return lootTable;
            }
        }
        return super.getExtraMobDrops(mob);
    }

    static {
        surfaceMobs = (new MobSpawnTable());;
        caveMobs = (new MobSpawnTable());
        surfaceCritters = (new MobSpawnTable());
        caveCritters = (new MobSpawnTable()).include(Biome.defaultCaveCritters);
    }
}
