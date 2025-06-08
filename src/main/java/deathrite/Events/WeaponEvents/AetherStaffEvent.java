package deathrite.Events.WeaponEvents;

import deathrite.DeathriteMod;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GroundPillar;
import necesse.engine.util.GroundPillarList;
import necesse.entity.levelEvent.mobAbilityLevelEvent.AncientDredgingStaffEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ShockWaveLevelEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.WeaponShockWaveLevelEvent;
import necesse.entity.manager.GroundPillarHandler;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;

public class AetherStaffEvent extends WeaponShockWaveLevelEvent {
    protected final GroundPillarList<AetherStaffEvent.AncientDredgePillar> pillars = new GroundPillarList<>();

    public AetherStaffEvent() {
        super(30.0F, 20.0F, 5.0F);
    }

    public AetherStaffEvent(Mob owner, int x, int y, GameRandom uniqueIDRandom, float targetAngle, GameDamage damage, float resilienceGain, float velocity, float knockback, float range) {
        super(owner, x, y, uniqueIDRandom, targetAngle, 30.0F, 20.0F, 5.0F, damage, resilienceGain, velocity, knockback, range);
    }

    public void init() {
        super.init();
        if (this.isClient()) {
            this.level.entityManager.addPillarHandler(new GroundPillarHandler<AetherStaffEvent.AncientDredgePillar>(this.pillars) {
                protected boolean canRemove() {
                    return AetherStaffEvent.this.isOver();
                }

                public double getCurrentDistanceMoved() {
                    return (double)0.0F;
                }
            });
        }

    }

    protected void spawnHitboxParticles(Polygon hitbox) {
    }

    protected void spawnHitboxParticles(float radius, float startAngle, float endAngle) {
        if (this.isClient()) {
            synchronized(this.pillars) {
                for(Point2D.Float pos : this.getPositionsAlongHit(radius, startAngle, endAngle, 20.0F, false)) {
                    this.pillars.add(new AetherStaffEvent.AncientDredgePillar((int)(pos.x + GameRandom.globalRandom.getFloatBetween(-10.0F, 10.0F)), (int)(pos.y + GameRandom.globalRandom.getFloatBetween(-10.0F, 10.0F)), (double)radius, this.level.getWorldEntity().getLocalTime()));
                }
            }
        }

    }

    public static class AncientDredgePillar extends GroundPillar {
        public GameTextureSection texture;
        public boolean mirror;

        public AncientDredgePillar(int x, int y, double spawnDistance, long spawnTime) {
            super(x, y, spawnDistance, spawnTime);
            this.mirror = GameRandom.globalRandom.nextBoolean();
            this.texture = null;
            GameTexture pillarSprites = DeathriteMod.aetherStaffPillars;
            if (pillarSprites != null) {
                int res = pillarSprites.getHeight();
                int sprite = GameRandom.globalRandom.nextInt(pillarSprites.getWidth() / res);
                this.texture = (new GameTextureSection(DeathriteMod.aetherStaffPillars)).sprite(sprite, 0, res);
            }

            this.behaviour = new GroundPillar.TimedBehaviour(200, 100, 200);
        }

        public DrawOptions getDrawOptions(Level level, long currentTime, double distanceMoved, GameCamera camera) {
            GameLight light = level.getLightLevel(this.x / 32, this.y / 32);
            int drawX = camera.getDrawX(this.x);
            int drawY = camera.getDrawY(this.y);
            double height = this.getHeight(currentTime, distanceMoved);
            int endY = (int)(height * (double)this.texture.getHeight());
            return this.texture.section(0, this.texture.getWidth(), 0, endY).initDraw().mirror(this.mirror, false).light(light).pos(drawX - this.texture.getWidth() / 2, drawY - endY);
        }
    }
}
