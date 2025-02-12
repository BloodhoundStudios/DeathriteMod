package deathrite.Mobs.Bosses.StarfangedDestroyer;

import deathrite.DeathriteMod;
import deathrite.Projectiles.ShootingStarProjectile;
import necesse.engine.achievements.AchievementManager;
import necesse.engine.eventStatusBars.EventStatusBarManager;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.gameAreaSearch.GameAreaStream;
import necesse.engine.world.WorldEntity;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.CoordinateMobAbility;
import necesse.entity.mobs.ai.behaviourTree.AINode;
import necesse.entity.mobs.ai.behaviourTree.AINodeResult;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.composites.SelectorAINode;
import necesse.entity.mobs.ai.behaviourTree.composites.SequenceAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.*;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.ai.behaviourTree.util.TargetFinderDistance;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.BossNearbyBuff;
import necesse.entity.mobs.hostile.bosses.BossWormMobHead;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.RotationLootItem;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class EnragedStarfangedDestroyerHead extends BossWormMobHead<EnragedStarfangedDestroyerBody, EnragedStarfangedDestroyerHead> {
    static Random random = new Random();
    public static int minstardust = 10;
    public static int maxstardust = 20;
    static int randomNumber = random.nextInt(maxstardust - minstardust + 1) + minstardust;
    public static int mindarkmatter = 5;
    public static int maxdarkmatter = 10;
    static int randomDarkMatterNumber = random.nextInt(maxdarkmatter - mindarkmatter + 1) + mindarkmatter;
    public static LootTable lootTable = new LootTable(new LootItem("stardust", randomNumber));
    public static RotationLootItem nightUniqueDrops = RotationLootItem.privateLootRotation(new LootItem("darkmatter", randomDarkMatterNumber));
    public static LootTable privateLootTable;
    public static float lengthPerBodyPart;
    public static float waveLength;
    public static final int totalBodyParts = 100;
    protected MobHealthScaling scaling = new MobHealthScaling(this);
    public static GameDamage headCollisionDamage;
    public static GameDamage bodyCollisionDamage;
    public static GameDamage razorDamage;
    public static GameDamage boulderExplosionDamage;
    public static int boulderExplosionRange;
    public static int totalRazorProjectiles;
    public static MaxHealthGetter MAX_HEALTH;
    public final CoordinateMobAbility flickSound;
    public final CoordinateMobAbility swingSound;

    public EnragedStarfangedDestroyerHead() {
        super(100, waveLength, 80.0F, 70, 36.0F, -8.0F);
        this.difficultyChanges.setMaxHealth(MAX_HEALTH);
        this.moveAccuracy = 160;
        this.setSpeed(250.0F);
        this.setArmor(30);
        this.accelerationMod = 1.5F;
        this.decelerationMod = 1.5F;
        this.collision = new Rectangle(-20, -15, 40, 30);
        this.hitBox = new Rectangle(-25, -20, 50, 40);
        this.selectBox = new Rectangle(-32, -60, 64, 64);
        this.flickSound = (CoordinateMobAbility)this.registerAbility(new CoordinateMobAbility() {
            protected void run(int x, int y) {
                if (EnragedStarfangedDestroyerHead.this.isClient()) {
                    SoundManager.playSound(GameResources.magicbolt2, SoundEffect.effect((float) x, (float) y).pitch(1.5F).volume(0.5F));
                }
            }
        });
        this.swingSound = (CoordinateMobAbility)this.registerAbility(new CoordinateMobAbility() {
            protected void run(int x, int y) {
                if (EnragedStarfangedDestroyerHead.this.isClient()) {
                    SoundManager.playSound(GameResources.swing1, SoundEffect.effect((float)x, (float)y).pitch(0.8F).volume(1.0F));
                }

            }
        });
    }

    public void setupHealthPacket(PacketWriter writer, boolean isFull) {
        this.scaling.setupHealthPacket(writer, isFull);
        super.setupHealthPacket(writer, isFull);
    }

    public void applyHealthPacket(PacketReader reader, boolean isFull) {
        this.scaling.applyHealthPacket(reader, isFull);
        super.applyHealthPacket(reader, isFull);
    }

    public void setMaxHealth(int maxHealth) {
        super.setMaxHealth(maxHealth);
        if (this.scaling != null) {
            this.scaling.updatedMaxHealth();
        }

    }

    protected void onAppearAbility() {
        super.onAppearAbility();
        if (this.isClient()) {
            SoundManager.playSound(GameResources.roar, SoundEffect.globalEffect().pitch(1.2F));
        }

    }

    protected float getDistToBodyPart(EnragedStarfangedDestroyerBody bodyPart, int index, float lastDistance) {
        return lengthPerBodyPart;
    }

    protected EnragedStarfangedDestroyerBody createNewBodyPart(int index) {
        EnragedStarfangedDestroyerBody bodyPart;
        if (index == 69) {
            bodyPart = new EnragedStarfangedDestroyerTail();
        } else {
            bodyPart = new EnragedStarfangedDestroyerBody();
        }

        bodyPart.sharesHitCooldownWithNext = index % 3 < 2;
        bodyPart.relaysBuffsToNext = index % 3 < 2;
        if (index != 0 && index != 68) {
            bodyPart.sprite = new Point(index % 4, 0);
            bodyPart.shadowSprite = 0;
        } else {
            bodyPart.sprite = new Point(4, 0);
            bodyPart.shadowSprite = 1;
        }

        return bodyPart;
    }

    protected void playMoveSound() {
        SoundManager.playSound(GameResources.shake, SoundEffect.effect(this).falloffDistance(1000));
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new EnragedStarfangedDestroyerHead.EnragedStarfangedDestroyerAI<>(), new FlyingAIMover());
        if (this.isClient()) {
            SoundManager.playSound(GameResources.roar, SoundEffect.globalEffect().pitch(1.2F));
        }

    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public LootTable getPrivateLootTable() {
        return privateLootTable;
    }

    public GameDamage getCollisionDamage(Mob target) {
        return headCollisionDamage;
    }

    public int getMaxHealth() {
        return super.getMaxHealth() + (int)((float)(this.scaling == null ? 0 : this.scaling.getHealthIncrease()) * this.getMaxHealthModifier());
    }

    public void clientTick() {
        super.clientTick();
        SoundManager.setMusic(DeathriteMod.StarfangedMusic, SoundManager.MusicPriority.EVENT, 1.5F);
        EventStatusBarManager.registerMobHealthStatusBar(this);
        BossNearbyBuff.applyAround(this);
        float healthPerc = (float)this.getHealth() / (float)this.getMaxHealth();
        float mod = Math.abs((float)Math.pow((double)healthPerc, (double)0.5F) - 1.0F);
        this.setSpeed(120.0F + mod * 90.0F);
    }

    public void serverTick() {
        super.serverTick();
        this.scaling.serverTick();
        BossNearbyBuff.applyAround(this);
        float healthPerc = (float)this.getHealth() / (float)this.getMaxHealth();
        float mod = Math.abs((float)Math.pow((double)healthPerc, (double)0.5F) - 1.0F);
        this.setSpeed(120.0F + mod * 90.0F);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), DeathriteMod.StarfangedDestroyerTexture, GameRandom.globalRandom.nextInt(6), 6, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        if (this.isVisible()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - 48;
            int drawY = camera.getDrawY(y);
            float headAngle = GameMath.fixAngle(GameMath.getAngle(new Point2D.Float(this.dx, this.dy)));
            WormMobHead.addAngledDrawable(list, new GameSprite(DeathriteMod.StarfangedDestroyerTexture, 0, 1, 96), MobRegistry.Textures.swampGuardian_mask, light, (int)this.height, headAngle, drawX, drawY, 64);
            this.addShadowDrawables(tileList, x, y, light, camera);
        }
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) {
        GameTexture shadowTexture = MobRegistry.Textures.swampGuardian_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += this.getBobbing(x, y);
        return shadowTexture.initDraw().sprite(2, 0, res).light(light).pos(drawX, drawY);
    }

    public boolean shouldDrawOnMap() {
        return this.isVisible();
    }

    public void drawOnMap(TickManager tickManager, Client client, int x, int y, double tileScale, Rectangle drawBounds, boolean isMinimap) {
        super.drawOnMap(tickManager, client, x, y, tileScale, drawBounds, isMinimap);
        int drawX = x - 24;
        int drawY = y - 24;
        float headAngle = GameMath.fixAngle(GameMath.getAngle(new Point2D.Float(this.dx, this.dy)));
        DeathriteMod.StarfangedDestroyerTexture.initDraw().sprite(2, 2, 96).rotate(headAngle + 90.0F, 24, 24).size(48, 48).draw(drawX, drawY);
    }

    public Rectangle drawOnMapBox(double tileScale, boolean isMinimap) {
        return new Rectangle(-15, -15, 30, 30);
    }

    public GameTooltips getMapTooltips() {
        return !this.isVisible() ? null : new StringTooltips(this.getDisplayName() + " " + this.getHealth() + "/" + this.getMaxHealth());
    }

    public Stream<ModifierValue<?>> getDefaultModifiers() {
        return Stream.of((new ModifierValue(BuffModifiers.SLOW, 0.0F)).max(0.2F));
    }

    protected void onDeath(Attacker attacker, HashSet<Attacker> attackers) {
        super.onDeath(attacker, attackers);
        attackers.stream().map(Attacker::getFirstPlayerOwner).filter(Objects::nonNull).filter(PlayerMob::isServerClient).map(PlayerMob::getServerClient).distinct().forEach((c) -> c.sendChatMessage(new LocalMessage("misc", "bossdefeat", "name", this.getLocalization())));
        if (!this.isDamagedByPlayers) {
            AchievementManager.checkMeAndThisArmyKill(this.getLevel(), attackers);
        }

    }

    static {
        lengthPerBodyPart = 25.0F;
        waveLength = 500.0F;
        // Health tips:
        // Normal health / 1.2 = Adventure Health
        // Normal health / 2 = Casual Health
        // Normal health * 1.25 = Hard Health
        // Brutal health has no formula
        MAX_HEALTH = new MaxHealthGetter(60000, 94000, 112500, 140625, 142500);
        privateLootTable = new LootTable(new LootItemInterface[]{nightUniqueDrops});
        headCollisionDamage = new GameDamage(114.0F);
        bodyCollisionDamage = new GameDamage(90.0F);
        razorDamage = new GameDamage(96.0F);
        totalRazorProjectiles = 1000;
    }

    public static class EnragedStarfangedDestroyerAI<T extends EnragedStarfangedDestroyerHead> extends SelectorAINode<T> {
        public EnragedStarfangedDestroyerAI() {
            SequenceAINode<T> chaserSequence = new SequenceAINode<>();
            this.addChild(chaserSequence);
            chaserSequence.addChild(new RemoveOnNoTargetNode<>(100));
            final TargetFinderAINode<T> targetFinder;
            chaserSequence.addChild(targetFinder = new TargetFinderAINode<T>(3200) {
                public GameAreaStream<? extends Mob> streamPossibleTargets(T mob, Point base, TargetFinderDistance<T> distance) {
                    return TargetFinderAINode.streamPlayers(mob, base, distance);
                }
            });
            targetFinder.moveToAttacker = false;
            ChargingCirclingChaserAINode<T> chaserAI;
            chaserSequence.addChild(chaserAI = new ChargingCirclingChaserAINode<>(500, 40));
            chaserSequence.addChild(new SpawnProjectilesOnHealthLossAINode<T>(EnragedStarfangedDestroyerHead.totalRazorProjectiles) {
                public void shootProjectile(T mob) {
                    WormMobHead<EnragedStarfangedDestroyerBody, EnragedStarfangedDestroyerHead>.BodyPartTarget t = mob.getRandomTargetFromBodyPart(this, targetFinder, (m, bp) -> {
                        if (bp.getDistance(m) > 500.0F) {
                            return false;
                        } else {
                            CollisionFilter collisionFilter = bp.modifyChasingCollisionFilter((new CollisionFilter()).mobCollision(), m);
                            return !mob.getLevel().collides(new Line2D.Float(m.x, m.y, bp.x, bp.y), collisionFilter);
                        }
                    });
                    if (t != null) {
                        t.bodyPart.getLevel().entityManager.projectiles.add(new ShootingStarProjectile(t.bodyPart.getLevel(), mob, t.bodyPart.x, t.bodyPart.y, t.target.x, t.target.y, 220.0F, 2000, EnragedStarfangedDestroyerHead.razorDamage, 100));
                        mob.flickSound.runAndSend(t.bodyPart.getX(), t.bodyPart.getY());
                    }

                }
            });
            chaserSequence.addChild(new EnragedStarfangedDestroyerHead.DiveChargeRotationAI<>(chaserAI));
            this.addChild(new WandererAINode<>(0));
        }
    }

    public static class DiveChargeRotationAI<T extends EnragedStarfangedDestroyerHead> extends AINode<T> {
        private int ticker;
        private final ChargingCirclingChaserAINode<T> chaserAI;

        public DiveChargeRotationAI(ChargingCirclingChaserAINode<T> chaserAI) {
            this.chaserAI = chaserAI;
        }

        protected void onRootSet(AINode<T> root, T mob, Blackboard<T> blackboard) {
            this.ticker = 100;
        }

        public void init(T mob, Blackboard<T> blackboard) {
        }

        public AINodeResult tick(T mob, Blackboard<T> blackboard) {
            Mob target = (Mob)blackboard.getObject(Mob.class, "currentTarget");
            if (target != null) {
                --this.ticker;
                if (this.ticker <= 0) {
                    if (!mob.dive && !mob.isUnderground) {
                        mob.diveAbility.runAndSend();
                        this.chaserAI.startCircling(mob, blackboard, target, 100);
                        this.ticker = (int)(20.0F * GameRandom.globalRandom.getFloatBetween(2.0F, 3.0F));
                    } else {
                        this.chaserAI.startCharge(mob, blackboard, target);
                        float currentAngle = GameMath.getAngle(new Point2D.Float(mob.x - target.x, mob.y - target.y));
                        Point2D.Float dir = GameMath.getAngleDir(currentAngle);
                        mob.appearAbility.runAndSend(mob.x, mob.y, -dir.x, -dir.y);
                        this.ticker = (int)(20.0F * GameRandom.globalRandom.getFloatBetween(8.0F, 9.0F));
                    }
                }
            }

            return AINodeResult.SUCCESS;
        }
    }
}
