package deathrite.Tiles.Aether;

import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;

import java.awt.*;

public class AetherSandTile extends TerrainSplatterTile {

    private GameTexture texture;
    private final GameRandom drawRandom; // Used only in draw function

    public AetherSandTile() {
        super(false, "aethersandfloor");
        canBeMined = true;
        drawRandom = new GameRandom();
        roomProperties.add("outsidefloor");
        mapColor = new Color(26, 156, 150);
    }

    @Override
    public Point getTerrainSprite(GameTextureSection gameTextureSection, Level level, int tileX, int tileY) {
        // This runs asynchronously, so if you want to randomize the tile that's
        // being drawn we have to synchronize the random call
        int tile;
        synchronized (drawRandom) {
            tile = drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(texture.getHeight() / 32);
        }
        return new Point(0, tile);
    }

    @Override
    public int getTerrainPriority() {
        return TerrainSplatterTile.PRIORITY_TERRAIN;
    }

}
