package deathrite.Tiles.Aether;

import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;

import java.awt.*;

public class AetherSandTile extends TerrainSplatterTile {
    private final GameRandom drawRandom;

    public AetherSandTile() {
        super(false, "aethersandfloor");
        this.canBeMined = true;
        this.drawRandom = new GameRandom();
        this.mapColor = new Color(26, 156, 150);
    }

    public Point getTerrainSprite(GameTextureSection gameTextureSection, Level level, int tileX, int tileY) {
        int tile;
        synchronized (drawRandom) {
            tile = drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
        }
        return new Point(0, tile);
    }

    public int getTerrainPriority() {
        return TerrainSplatterTile.PRIORITY_TERRAIN;
    }

}
