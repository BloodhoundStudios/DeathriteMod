package deathrite.Tiles.Sky;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.projectile.BombProjectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.LevelTileTerrainDrawOptions;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.GameTile;
import necesse.level.gameTile.LavaTile;
import necesse.level.gameTile.LiquidTile;
import necesse.level.gameTile.WaterTile;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.desert.DesertBiome;
import necesse.level.maps.biomes.snow.SnowBiome;
import necesse.level.maps.biomes.swamp.SwampBiome;

import java.awt.*;
import java.util.List;

public class SkyCloudWaterTile extends LiquidTile {
    public GameTextureSection deepTexture;
    public GameTextureSection shallowTexture;
    protected final GameRandom drawRandom = new GameRandom();

    public SkyCloudWaterTile() {
        super(new Color(31, 133, 170), "skycloudwater_shallow");
    }

    protected void loadTextures() {
        super.loadTextures();
        this.deepTexture = tileTextures.addTexture(GameTexture.fromFile("tiles/waterdeep"));
        this.shallowTexture = tileTextures.addTexture(GameTexture.fromFile("tiles/watershallow"));
    }

    public void tick(Mob mob, Level level, int x, int y) {
        if (!mob.isFlying() && !mob.isWaterWalking() && level.inLiquid(mob.getX(), mob.getY())) {
            mob.buffManager.removeBuff("onfire", false);
        }

    }

    public void tickValid(Level level, int x, int y, boolean underGeneration) {
        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                if (i != 0 || j != 0) {
                    GameTile t = level.getTile(x + i, y + j);
                    if (t.isLiquid && t instanceof LavaTile) {
                        if (!underGeneration && level.isClient()) {
                            for(int k = 0; k < 10; ++k) {
                                BombProjectile.spawnFuseParticle(level, (float)(x * 32 + GameRandom.globalRandom.nextInt(33)), (float)(y * 32 + GameRandom.globalRandom.nextInt(33)), 1.0F);
                            }

                            level.lightManager.refreshParticleLight(x, y, 0.0F, 0.3F);
                            SoundManager.playSound(GameResources.fizz, SoundEffect.effect((float)(x * 32 + 16), (float)(y * 32 + 16)).volume(0.5F));
                        }

                        level.setTile(x, y, TileRegistry.getTileID("rocktile"));
                    }
                }
            }
        }

    }

    public LiquidTile.TextureIndexes getTextureIndexes(Level level) {
        return level.biome instanceof SwampBiome ? new LiquidTile.TextureIndexes(4, 5, 6, 7) : new LiquidTile.TextureIndexes(0, 1, 2, 3);
    }

    public Color getLiquidColor(Level level, int x, int y) {
        if (level.biome instanceof SwampBiome) {
            return this.getLiquidColor(2);
        } else {
            return level.biome instanceof DesertBiome && level.isCave ? this.getLiquidColor(3) : this.getLiquidColor(0);
        }
    }

    public Color getNewSplattingLiquidColor(Level level, int x, int y) {
        if (level.isCave && level.biome instanceof SwampBiome) {
            return new Color(200, 200, 100);
        } else if (level.isCave && level.biome instanceof SnowBiome) {
            return new Color(200, 240, 255);
        } else {
            return level.isCave ? new Color(180, 200, 150) : super.getNewSplattingLiquidColor(level, x, y);
        }
    }

    public Color getMapColor(Level level, int tileX, int tileY) {
        if (level.isCave && level.biome instanceof SwampBiome) {
            return new Color(33, 104, 61);
        } else {
            return level.isCave && level.biome instanceof DesertBiome ? new Color(16, 194, 188) : this.getLiquidColor(level, tileX, tileY);
        }
    }

    protected void addLiquidTopDrawables(LevelTileTerrainDrawOptions list, List<LevelSortedDrawable> sortedList, Level level, int tileX, int tileY, GameCamera camera, TickManager tickManager) {
        boolean addBobbing;
        synchronized(this.drawRandom) {
            addBobbing = this.drawRandom.seeded(getTileSeed(tileX, tileY)).getChance(0.15F);
        }

        if (addBobbing) {
            int drawX = camera.getTileDrawX(tileX);
            int drawY = camera.getTileDrawY(tileY);
            int offset = this.getLiquidBobbing(level, tileX, tileY);
            int xOffset;
            int yOffset;
            GameTextureSection bobbingTexture;
            if (level.liquidManager.getHeight(tileX, tileY) <= -10) {
                xOffset = 0;
                yOffset = offset;
                bobbingTexture = this.deepTexture;
            } else {
                xOffset = offset;
                yOffset = 0;
                bobbingTexture = this.shallowTexture;
            }

            int tile;
            synchronized(this.drawRandom) {
                tile = this.drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(bobbingTexture.getHeight() / 32);
            }

            list.add(bobbingTexture.sprite(0, tile, 32)).color(this.getLiquidColor(level, tileX, tileY).brighter()).pos(drawX + xOffset, drawY + yOffset - 2);
        }

    }

}
