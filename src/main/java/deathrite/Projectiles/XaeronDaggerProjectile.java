//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package necesse.entity.projectile;

import java.awt.Color;
import java.util.List;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

public class XaeronDaggerProjectile extends FollowingProjectile {
    private long spawnTime;

    public XaeronDaggerProjectile() {
    }

    public XaeronDaggerProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.knockback = knockback;
    }

    public void init() {
        super.init();
        this.setWidth(10.0F);
        this.turnSpeed = 0.3F;
        this.height = 18.0F;
        this.spawnTime = this.getWorldEntity().getTime();
        this.heightBasedOnDistance = true;
        this.trailOffset = 0.0F;
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(88, 13, 112), 14.0F, 250, 18.0F);
    }

    public Color getParticleColor() {
        return new Color(88, 13, 112);
    }

    public void updateTarget() {
        if (this.traveledDistance > 50.0F) {
            this.findTarget((m) -> m.isHostile, 0.0F, 250.0F);
        }

    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y) - this.texture.getHeight() / 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate((float)(this.getWorldEntity().getTime() - this.spawnTime), this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, (float)(this.getWorldEntity().getTime() - this.spawnTime), this.texture.getHeight() / 2);
        }
    }
}