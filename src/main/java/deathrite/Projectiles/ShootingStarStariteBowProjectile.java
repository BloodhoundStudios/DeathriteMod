package deathrite.Projectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
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

public class ShootingStarStariteBowProjectile extends Projectile {
    protected long spawnTime;

    public ShootingStarStariteBowProjectile() {
    }

    public ShootingStarStariteBowProjectile(Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
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
        this.isSolid = true;
        this.givesLight = true;
        this.particleRandomOffset = 8.0F;
        this.piercing = 0;
        this.bouncing = 0;
        this.setWidth(24.0F);
    }

    @Override
    public void addHit(Mob target) {
        super.addHit(target);
        target.addBuff(new ActiveBuff(BuffRegistry.getBuff("starfrost"), target, 2000, this), true);

    }

    public Color getParticleColor() {
        return null;
    }

    public void refreshParticleLight() {
        Color color = new Color(155, 55, 155);
        this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, color, this.lightSaturation);
    }

    public Trail getTrail() {
        return null;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            float alpha = 1.0F;
            if (this.traveledDistance > (float)(this.distance - 100)) {
                alpha = ((float)this.distance - this.traveledDistance) / 100.0F;
            }

            GameLight light = level.getLightLevel(this.getX() / 32, this.getY() / 32);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y) - this.texture.getHeight() / 2;
            float angle = (float)(this.getWorldEntity().getTime() - this.spawnTime) / 1.5F;
            final TextureDrawOptions options = this.texture.initDraw().light(light).alpha(alpha).rotate(angle, this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, angle, this.shadowTexture.getHeight() / 2);
        }
    }
}
