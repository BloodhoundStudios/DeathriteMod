package deathrite.Projectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.ParticleOption;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class StariteArrowVisualProjectile extends Projectile {
    protected long spawnTime;

    public StariteArrowVisualProjectile() {
    }

    public StariteArrowVisualProjectile(Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.setDamage(damage);
        this.speed = speed;
        this.setDistance(distance);
    }

    public void init() {
        super.init();
        this.height = 18.0F;
        this.piercing = 1;
        this.trailOffset = -10.0F;
        this.setWidth(14.0F);
    }

    public Color getParticleColor() {
        return new Color(252, 222, 28);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(92, 15, 122), 12.0F, 250, this.getHeight());
    }

    protected int getExtraSpinningParticles() {
        return super.getExtraSpinningParticles() + 2;
    }

    protected void modifySpinningParticle(ParticleOption particle) {
        particle.lifeTime(1000);
    }


    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }
}
