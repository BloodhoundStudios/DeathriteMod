package deathrite.Biomes.Aether;

import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.GenerateIslandLayoutEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandLayoutEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandStructuresEvent;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.IslandGeneration;

public class AetherSkyLevel extends AetherSurfaceLevel {

    public AetherSkyLevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public AetherSkyLevel(int islandX, int islandY, float islandSize, int dimension, WorldEntity worldEntity, Biome biome) {
        super(new LevelIdentifier(islandX, islandY, dimension), 300, 300, worldEntity);
        this.biome = biome;
        this.generateLevel(islandSize);
    }

    public void generateLevel(float islandSize) {
        System.out.println("Sky Level Generated");
        int size = (int) (islandSize * 90.0F) + 40;
        IslandGeneration ig = new IslandGeneration(this, size);
        int waterTile = TileRegistry.getTileID("watertile");
        int cloudTile = TileRegistry.getTileID("skycloudfloor");
        GameEvents.triggerEvent(new GenerateIslandLayoutEvent(this, islandSize, ig), (e) -> {
            if (ig.random.getChance(0.05F)) {
                ig.generateSimpleIsland(this.width / 2, this.height / 2, waterTile, cloudTile, -1);
            } else {
                ig.generateShapedIsland(waterTile, cloudTile, -1);
            }

            int rivers = ig.random.getIntBetween(1, 3);

            for(int i = 0; i < rivers && (i <= 0 || !ig.random.getChance(0.4F)); ++i) {
                ig.generateRiver(waterTile, cloudTile, -1);
            }

            ig.generateLakes(0.01F, waterTile, cloudTile, -1);
            ig.clearTinyIslands(waterTile);
            this.liquidManager.calculateHeights();
        });
        GameEvents.triggerEvent(new GeneratedIslandLayoutEvent(this, islandSize, ig));
        GameEvents.triggerEvent(new GeneratedIslandStructuresEvent(this, islandSize, ig));
        GenerationTools.checkValid(this);
    }

    public GameMessage getLocationMessage() {
        return new LocalMessage("biome", "sky", "biome", this.biome.getLocalization());
    }

    public GameTile getUnderLiquidTile(int tileX, int tileY){
        return TileRegistry.getTile(TileRegistry.sandID);
    }
}

