//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Biomes.Aether;

import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.GenerateIslandAnimalsEvent;
import necesse.engine.events.worldGeneration.GenerateIslandLayoutEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandAnimalsEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandLayoutEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandStructuresEvent;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.IslandGeneration;

public class AetherSurfaceLevel extends Level {

    public AetherSurfaceLevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public AetherSurfaceLevel(int islandX, int islandY, float islandSize, WorldEntity worldEntity, Biome biome) {
        super(new LevelIdentifier(islandX, islandY, 0), 300, 300, worldEntity);
        this.biome = biome;
        this.generateLevel(islandSize);
    }

    public void generateLevel(float islandSize) {
        System.out.println("Aether Generated");
        int size = (int) (islandSize * 90.0F) + 40;
        IslandGeneration ig = new IslandGeneration(this, size);
        int waterTile = TileRegistry.getTileID("watertile");
        int sandTile = TileRegistry.getTileID("aethersandfloor");
        int beachTile = TileRegistry.getTileID("aethersandfloor");
        GameEvents.triggerEvent(new GenerateIslandLayoutEvent(this, islandSize, ig), (e) -> {
            if (ig.random.getChance(0.05F)) {
                ig.generateSimpleIsland(this.width / 2, this.height / 2, waterTile, sandTile, beachTile);
            } else {
                ig.generateShapedIsland(waterTile, sandTile, beachTile);
            }

            int rivers = ig.random.getIntBetween(1, 3);

            for(int i = 0; i < rivers && (i <= 0 || !ig.random.getChance(0.4F)); ++i) {
                ig.generateRiver(waterTile, sandTile, beachTile);
            }

            ig.generateLakes(0.01F, waterTile, sandTile, beachTile);
            ig.clearTinyIslands(waterTile);
            this.liquidManager.calculateHeights();
        });
        GameEvents.triggerEvent(new GeneratedIslandLayoutEvent(this, islandSize, ig));
        GameEvents.triggerEvent(new GeneratedIslandStructuresEvent(this, islandSize, ig));
        GameEvents.triggerEvent(new GenerateIslandAnimalsEvent(this, islandSize, ig), (e) -> GenerationTools.spawnMobHerds(this, ig.random, "wildostrich", 1, sandTile, 1, 1));
        GameEvents.triggerEvent(new GeneratedIslandAnimalsEvent(this, islandSize, ig));
        GenerationTools.checkValid(this);
    }

    public GameMessage getLocationMessage() {
        return new LocalMessage("biome", "surface", "biome", this.biome.getLocalization());
    }

    public GameTile getUnderLiquidTile(int tileX, int tileY){
        return TileRegistry.getTile(TileRegistry.sandID);
    }
}
