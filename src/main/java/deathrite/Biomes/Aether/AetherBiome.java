package deathrite.Biomes.Aether;

import deathrite.DeathriteMod;
import necesse.engine.AbstractMusicList;
import necesse.engine.MusicList;
import necesse.engine.network.server.Server;
import necesse.engine.registries.JournalRegistry;
import necesse.engine.world.WorldEntity;
import necesse.engine.world.WorldGenerator;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.lootTable.LootTable;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.MobSpawnTable;
import necesse.level.maps.biomes.desert.DesertDeepCaveLevel;

public class AetherBiome extends Biome {
    public static MobSpawnTable surfaceMobs;
    public static MobSpawnTable caveMobs;
    public static MobSpawnTable deepDesertCaveMobs;
    public static MobSpawnTable surfaceCritters;
    public static MobSpawnTable caveCritters;
    public static MobSpawnTable deepCaveCritters;
    public boolean canRain(Level level) {
        return false;
    }

    public AetherBiome() {
    }

    public Level getNewSurfaceLevel(int islandX, int islandY, float islandSize, Server server, WorldEntity worldEntity) {
        return new AetherSurfaceLevel(islandX, islandY, islandSize, worldEntity, this);
    }

    public Level getNewCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new AetherCaveLevel(islandX, islandY, dimension, worldEntity, this);
    }

    public Level getNewDeepCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return null;
    }

    public Level getNewSkyLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new AetherSkyLevel(islandX, islandY, WorldGenerator.getIslandSize(islandX, islandY), 10, worldEntity, this);
    }

    public Level getNewSpaceLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new AetherSpaceLevel(islandX, islandY, dimension, worldEntity, this);
    }

    @Override
    public Level getNewLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        if(dimension == 10) {
            return getNewSkyLevel(islandX, islandY, dimension, server, worldEntity);
        } else if (dimension == -10) {
            return getNewSpaceLevel(islandX, islandY, dimension, server, worldEntity);
        } if (dimension == 0) {
            return this.getNewSurfaceLevel(islandX, islandY, server, worldEntity);
        } else if (dimension == -1) {
            return this.getNewCaveLevel(islandX, islandY, dimension, server, worldEntity);
        }
        return null;
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
        if (level.isCave) {
            return new MusicList(DeathriteMod.AetherMusic);
        } else {
            return new MusicList(DeathriteMod.AetherMusic);
        }
    }

    public LootTable getExtraBiomeMobDrops(JournalRegistry.LevelType levelType) {
        LootTable lootTable = new LootTable();
        switch (levelType) {
            case CAVE:
                break;
            case DEEP_CAVE:
        }

        return lootTable;
    }

    static {
        surfaceMobs = (new MobSpawnTable().add(15,"aetherspirit"));;
        caveMobs = (new MobSpawnTable().add(50,"aetherspirit"));
        surfaceCritters = (new MobSpawnTable());
        caveCritters = (new MobSpawnTable()).include(Biome.defaultCaveCritters).add(100, "aethercaveling");
    }
}
