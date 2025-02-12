package deathrite.Mobs.Bosses.StarfangedDestroyer;

import deathrite.DeathriteMod;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.WormMobHead;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.geom.Point2D;
import java.util.List;

public class EnragedStarfangedDestroyerTail extends EnragedStarfangedDestroyerBody {
    public EnragedStarfangedDestroyerTail() {
    }

    private EnragedStarfangedDestroyerBody getNextBodyPart(int count) {
        EnragedStarfangedDestroyerBody next = (EnragedStarfangedDestroyerBody)this.next;

        for(int i = 0; i < count && next.next != null; ++i) {
            next = (EnragedStarfangedDestroyerBody) next.next;
        }
        return next;
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (this.isVisible()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - 48;
            int drawY = camera.getDrawY(y);
            float tailAngle = 0.0F;
            EnragedStarfangedDestroyerBody next = this.getNextBodyPart(2);
            if (next != null) {
                tailAngle = GameMath.fixAngle(GameMath.getAngle(new Point2D.Float(next.x - (float)x, next.y - (float)y)) + 180.0F);
            }

            WormMobHead.addAngledDrawable(list, new GameSprite(DeathriteMod.StarfangedDestroyerTexture, 1, 1, 96), MobRegistry.Textures.swampGuardian_mask, light, (int)this.height, tailAngle, drawX, drawY, 64);
            this.addShadowDrawables(tileList, x, y, light, camera);
        }
    }
}
