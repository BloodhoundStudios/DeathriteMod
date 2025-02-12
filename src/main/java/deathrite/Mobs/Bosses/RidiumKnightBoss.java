package deathrite.Mobs.Bosses;

import necesse.engine.achievements.AchievementManager;
import necesse.engine.eventStatusBars.EventStatusBarManager;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.ServerClient;
import necesse.engine.postProcessing.PostProcessingEffects;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.MusicRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.BooleanMobAbility;
import necesse.entity.mobs.ability.MobAbility;
import necesse.entity.mobs.ability.TimedMobAbility;
import necesse.entity.mobs.ability.VolumePitchSoundMobAbility;
import necesse.entity.mobs.ai.behaviourTree.AINode;
import necesse.entity.mobs.ai.behaviourTree.AINodeResult;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.composites.SequenceAINode;
import necesse.entity.mobs.buffs.staticBuffs.BossNearbyBuff;
import necesse.entity.mobs.hostile.bosses.BossMob;
import necesse.entity.mobs.hostile.bosses.FallenWizardMob;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.*;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.entity.mobs.Mob;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class RidiumKnightBoss extends BossMob {

    // variables
    public static LootTable lootTable = new LootTable(new LootItemInterface[]{new LootItem("voidshard", 25), new OneOfLootItems(LootItem.between("recallscroll", 12, 20), new LootItemInterface[]{LootItem.between("travelscroll", 5, 10)}), new ChanceLootItem(0.25F, "wizardsawakeningvinyl")});
    public static RotationLootItem uniqueDrops = RotationLootItem.privateLootRotation(new LootItemInterface[]{new LootItem("voidstaff"), new LootItem("voidmissile"), new LootItem("magicstilts")});
    public static LootTable privateLootTable;
    public static GameDamage cloneProjectile;
    public static GameDamage missile;
    public static GameDamage waveProjectile;
    public static GameDamage homingExplosion;
    public static int homingExplosionRange;
    public static MaxHealthGetter MAX_HEALTH;
    protected boolean itemSpawned;
    protected MobHealthScaling scaling = new MobHealthScaling(this);
    public boolean swingAttack;
    protected ArrayList<Projectile> projectiles;
    protected boolean inSecondStageTransition;
    protected boolean inThirdStageTransition;
    protected long secondStageTransitionStartTime;
    protected long thirdStageTransitionStartTime;
    protected boolean playedSecondStageSound;
    protected boolean playedThirdStageSound;
    protected boolean inDeathTransition;
    protected boolean allowDeath;
    protected long deathTransitionStartTime;
    protected boolean isSecondStage;
    protected boolean isThirdStage;
    public int Health;
    public final RidiumKnightTeleportAbility teleportAbility;
    public final BooleanMobAbility changeHostile;
    public final TimedMobAbility startSecondStageAbility;
    public final TimedMobAbility startThirdStageAbility;
    public final TimedMobAbility startDeathStageAbility;
    public final VolumePitchSoundMobAbility playBoltSoundAbility;


    // Ridium Knight Variable and Stage setup
    public RidiumKnightBoss(int Health, BooleanMobAbility changeHostile, TimedMobAbility startSecondStageAbility, TimedMobAbility startDeathStageAbility, VolumePitchSoundMobAbility playBoltSoundAbility) {
        super(Health);
        this.Health = 100;
        this.teleportAbility = this.registerAbility(new RidiumKnightBoss.RidiumKnightTeleportAbility());;
        this.changeHostile = changeHostile;
        this.startDeathStageAbility = startDeathStageAbility;
        this.playBoltSoundAbility = playBoltSoundAbility;
        this.difficultyChanges.setMaxHealth(MAX_HEALTH);
        this.attackAnimTime = 200;
        this.setSpeed(50.0F);
        this.setFriction(3.0F);
        this.setArmor(10);
        this.setKnockbackModifier(0.0F);
        this.setDir(2);
        this.swimMaskMove = 16;
        this.swimMaskOffset = -8;
        this.swimSinkOffset = -8;
        this.shouldSave = true;
        this.isHostile = false;
        startSecondStageAbility = this.registerAbility(new TimedMobAbility() {
            protected void run(long time) {
                RidiumKnightBoss.this.inSecondStageTransition = true;
                RidiumKnightBoss.this.playedSecondStageSound = false;
                RidiumKnightBoss.this.secondStageTransitionStartTime = time;
                RidiumKnightBoss.this.isSecondStage = true;
                RidiumKnightBoss.this.updateBoxes();
                RidiumKnightBoss.this.moveX = 0.0F;
                RidiumKnightBoss.this.moveY = 0.0F;
            }
        });
        startThirdStageAbility = this.registerAbility(new TimedMobAbility() {
            protected void run(long time) {
                RidiumKnightBoss.this.inThirdStageTransition = true;
                RidiumKnightBoss.this.playedThirdStageSound = false;
                RidiumKnightBoss.this.thirdStageTransitionStartTime = time;
                RidiumKnightBoss.this.isThirdStage = true;
                RidiumKnightBoss.this.updateBoxes();
                RidiumKnightBoss.this.moveX = 0.0F;
                RidiumKnightBoss.this.moveY = 0.0F;
            }
        });
        this.startSecondStageAbility = startSecondStageAbility;
    }

    public void init() {
    }

    // Hitboxes
    public void updateBoxes() {
        if (this.isSecondStage) {
            this.collision = new Rectangle(-10, -7, 20, 14);
            this.hitBox = new Rectangle(-18, -15, 36, 30);
            this.selectBox = new Rectangle(-18, -41, 36, 48);
        } else if (this.isThirdStage) {
            this.collision = new Rectangle(-10, -7, 20, 14);
            this.hitBox = new Rectangle(-18, -15, 36, 30);
            this.selectBox = new Rectangle(-18, -41, 36, 48);
        } else {
            this.collision = new Rectangle(-10, -7, 20, 14);
            this.hitBox = new Rectangle(-18, -15, 36, 30);
            this.selectBox = new Rectangle(-18, -41, 36, 48);
        }
    }

    // Server packet so he spawns for everyone
    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextBoolean(this.itemSpawned);
    }

    // Applies Server Packet
    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.itemSpawned = reader.getNextBoolean();
    }

    // Server packet so he moves for everyone
    public void setupMovementPacket(PacketWriter writer) {
        super.setupMovementPacket(writer);
        writer.putNextBoolean(this.isHostile);
        writer.putNextBoolean(this.isSecondStage);
        writer.putNextBoolean(this.inSecondStageTransition);
        if (this.inSecondStageTransition) {
            writer.putNextLong(this.secondStageTransitionStartTime);
        }

        writer.putNextBoolean(this.inDeathTransition);
        if (this.inDeathTransition) {
            writer.putNextLong(this.deathTransitionStartTime);
        }

    }

    // Applies Server Packet
    public void applyMovementPacket(PacketReader reader, boolean isDirect) {
        super.applyMovementPacket(reader, isDirect);
        this.isHostile = reader.getNextBoolean();
        this.isSecondStage = reader.getNextBoolean();
        this.updateBoxes();
        this.inSecondStageTransition = reader.getNextBoolean();
        if (this.inSecondStageTransition) {
            this.secondStageTransitionStartTime = reader.getNextLong();
        }

        this.inDeathTransition = reader.getNextBoolean();
        if (this.inDeathTransition) {
            this.deathTransitionStartTime = reader.getNextLong();
        }

    }

    // Server Packet so his health is the same for everyone
    public void setupHealthPacket(PacketWriter writer, boolean isFull) {
        this.scaling.setupHealthPacket(writer, isFull);
        super.setupHealthPacket(writer, isFull);
    }

    // Applies Server Packet
    public void applyHealthPacket(PacketReader reader, boolean isFull) {
        this.scaling.applyHealthPacket(reader, isFull);
        super.applyHealthPacket(reader, isFull);
    }

    // Sets Max Health
    public void setMaxHealth(int maxHealth) {
        super.setMaxHealth(maxHealth);
        if (this.scaling != null) {
            this.scaling.updatedMaxHealth();
        }
    }

    // Gets Max Health
    public int getMaxHealth() {
        return super.getMaxHealth() + (int)((float)(this.scaling == null ? 0 : this.scaling.getHealthIncrease()) * this.getMaxHealthModifier());
    }

    // Gets the Loot Table for what he drops
    public LootTable getLootTable() {
        return lootTable;
    }

    // Gets the local Loot Table for what he drops
    public LootTable getPrivateLootTable() {
        return privateLootTable;
    }

    // Spawns Items on Death
    public void makeItemSpawned() {
        this.itemSpawned = true;
    }

    // Sets Projectiles
    public boolean canAddProjectile() {
        return this.projectiles.size() < 50;
    }

    // Adds Projectiles
    public void addProjectile(Projectile p) {
        this.projectiles.add(p);
    }

    // Clears Projectiles
    public void clearProjectiles() {
        this.projectiles.forEach(Projectile::remove);
        this.projectiles.clear();
    }

    // Client only stuff
    public void clientTick() {
        super.clientTick();
        if (this.isHostile) {
        SoundManager.setMusic(MusicRegistry.WizardsAwakening, SoundManager.MusicPriority.EVENT, 1.5F);
        Color shade = getWizardShade(this);
        float red = (float)shade.getRed() / 255.0F;
        float green = (float)shade.getGreen() / 255.0F;
        float blue = (float)shade.getBlue() / 255.0F;
        float mod = 1.0F / GameMath.min(new float[]{red, green, blue});
        PostProcessingEffects.setSceneShade(red * mod, green * mod, blue * mod);
        EventStatusBarManager.registerMobHealthStatusBar(this);
        BossNearbyBuff.applyAround(this);
    }

        this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, 270.0F, 0.5F);
        if (this.inDeathTransition) {
        this.setHealthHidden(1);
    } else if (this.inSecondStageTransition) {
        long timePassed = this.getWorldEntity().getTime() - this.secondStageTransitionStartTime;
        if (!this.playedSecondStageSound && timePassed >= 4500L) {
            SoundManager.playSound(GameResources.magicroar, SoundEffect.globalEffect());
            this.playedSecondStageSound = true;
        }

        if (timePassed >= 5000L) {
            this.inSecondStageTransition = false;
        }
    }

}

// Server only Stuff
public void serverTick() {
    super.serverTick();
    this.scaling.serverTick();
    if (this.isHostile) {
        BossNearbyBuff.applyAround(this);
    }

    if (this.inDeathTransition) {
        this.setHealthHidden(1);
        long timePassed = this.getWorldEntity().getTime() - this.deathTransitionStartTime;
        if (timePassed >= 3000L && !this.removed()) {
            this.allowDeath = true;
            this.setHealthHidden(0);
        }
    } else if (this.inSecondStageTransition) {
        long timePassed = this.getWorldEntity().getTime() - this.secondStageTransitionStartTime;
        if (timePassed >= 5000L) {
            this.inSecondStageTransition = false;
        }
    }
}

    // On Death
    protected void onDeath(Attacker attacker, HashSet<Attacker> attackers) {
        super.onDeath(attacker, attackers);
        attackers.stream().map(Attacker::getFirstPlayerOwner).filter(Objects::nonNull).filter(PlayerMob::isServerClient).map(PlayerMob::getServerClient).distinct().forEach((c) -> c.sendChatMessage(new LocalMessage("misc", "bossdefeat", "name", this.getLocalization())));
        if (!this.isDamagedByPlayers) {
            AchievementManager.checkMeAndThisArmyKill(this.getLevel(), attackers);
        }

    }

    // Wizard map color?
    public static Color getWizardShade(Mob mob) {
        Color shade = new Color(255, 255, 255);
        if (mob == null) {
            return shade;
        } else {
            float time = (float)mob.getWorldEntity().getTime() / 5000.0F;
            float v = time - (float)Math.floor((double)time);
            return Color.getHSBColor(v, 0.2F, 1.0F);
        }
    }

    // Wizard map color?
    public static Color getWizardColor(Mob mob) {
        return new Color(100, 22, 22);
    }

    // Projectile map color?
    public static Color getWizardProjectileColor(Mob mob) {
        return new Color(50, 0, 102);
    }

    // Draws icon on map
    public boolean shouldDrawOnMap() {
        return true;
    }

    // Icon stuff bruh
    public Rectangle drawOnMapBox(double tileScale, boolean isMinimap) {
        return new Rectangle(-8, -22, 16, 25);
    }

    // Toolip for when you hover over the icon
    public GameTooltips getMapTooltips() {
        return new StringTooltips(this.getDisplayName() + " " + this.getHealth() + "/" + this.getMaxHealth());
    }

    // More Map Drawing
    public void drawOnMap(TickManager tickManager, Client client, int x, int y, double tileScale, Rectangle drawBounds, boolean isMinimap) {
        super.drawOnMap(tickManager, client, x, y, tileScale, drawBounds, isMinimap);
        if (this.isSecondStage) {
            int drawX = x - 24;
            int drawY = y - 32;
            int anim = GameUtils.getAnim(this.getWorldEntity().getTime(), 4, 400);
            MobRegistry.Textures.voidWizard2.initDraw().sprite(anim, 0, 96).size(48, 48).draw(drawX, drawY);
        } else {
            int drawX = x - 16;
            int drawY = y - 26;
            int dir = this.getDir();
            Point sprite = this.getAnimSprite(this.getDrawX(), this.getDrawY(), dir);
            (new HumanDrawOptions(this.getLevel(), MobRegistry.Textures.voidWizard)).sprite(sprite).dir(dir).size(32, 32).draw(drawX, drawY);
        }
    }

    // Changes speed based on Stage
    public float getSpeed() {
        return super.getSpeed() * (this.isSecondStage ? 1.4F : 1.0F);
    }

    // Death Message
    public DeathMessageTable getDeathMessages() {
        return this.getDeathMessages("voidwiz", 4);
    }

    // Just random stuff by the looks of it
    static {
        privateLootTable = new LootTable(new LootItemInterface[]{new ConditionLootItem("emptypendant", (r, o) -> {
            ServerClient client = (ServerClient)LootTable.expectExtra(ServerClient.class, o, 1);
            return client != null && client.playerMob.getInv().equipment.getTrinketSlotsSize() < 5 && client.playerMob.getInv().getAmount(ItemRegistry.getItem("emptypendant"), false, false, true, true, "have") == 0;
        }), uniqueDrops});
        cloneProjectile = new GameDamage(42.0F);
        missile = new GameDamage(35.0F);
        waveProjectile = new GameDamage(32.0F);
        homingExplosion = new GameDamage(50.0F);
        homingExplosionRange = 55;
        MAX_HEALTH = new MaxHealthGetter(3500, 4000, 4500, 5000, 6000);
    }

    public static class TeleportParticle extends Particle {
        private final Point animSprite;
        private int dir;

        // Particles for when he teleports
        public TeleportParticle(Level level, int x, int y, RidiumKnightBoss owner) {
            super(level, (float)x, (float)y, 0.0F, 0.0F, 1000L);
            this.animSprite = owner.getAnimSprite(x, y, this.dir);
            this.dir = owner.getDir();
        }

        // Draws the Particles
        public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
            float life = this.getLifeCyclePercent();
            if (!this.removed()) {
                GameLight light = level.getLightLevel(this);
                int drawX = this.getX() - camera.getX() - 22 - 10;
                int drawY = this.getY() - camera.getY() - 44 - 7;
                float alpha = Math.abs(life - 1.0F);
                final DrawOptions drawOptions = (new HumanDrawOptions(level, MobRegistry.Textures.voidWizard)).sprite(this.animSprite).dir(this.dir).light(light).alpha(alpha).pos(drawX, drawY);
                list.add(new EntityDrawable(this) {
                    public void draw(TickManager tickManager) {
                        drawOptions.draw();
                    }
                });
            }
        }
    }
    
    public class RidiumKnightTeleportAbility extends MobAbility {
        public RidiumKnightTeleportAbility() {
        }

        // Runs and Sends the Packet to Server
        public void runAndSend(int x, int y, int dir, boolean spawnParticles) {
            Packet content = new Packet();
            PacketWriter writer = new PacketWriter(content);
            writer.putNextInt(x);
            writer.putNextInt(y);
            writer.putNextInt(dir);
            writer.putNextBoolean(spawnParticles);
            this.runAndSendAbility(content);
        }

        // Executes the Packet when he teleports
        public void executePacket(PacketReader reader) {
            int x = reader.getNextInt();
            int y = reader.getNextInt();
            int dir = reader.getNextInt();
            boolean spawnParticles = reader.getNextBoolean();
            if (spawnParticles && RidiumKnightBoss.this.isClient()) {
                RidiumKnightBoss.this.getLevel().entityManager.addParticle(new RidiumKnightBoss.TeleportParticle(RidiumKnightBoss.this.getLevel(), RidiumKnightBoss.this.getX(), RidiumKnightBoss.this.getY(), RidiumKnightBoss.this), Particle.GType.CRITICAL);
                RidiumKnightBoss.this.getLevel().lightManager.refreshParticleLightFloat((float)x, (float)y, 270.0F, 0.5F);
            }

            RidiumKnightBoss.this.setDir(dir);
            RidiumKnightBoss.this.setPos((float)x, (float)y, true);
        }
    }

    public static class RidiumKnightAI<T extends RidiumKnightBoss> extends SequenceAINode<T> {
        private int inActiveTimer;

        public RidiumKnightAI() {
            this.addChild(new AINode<T>() {
                protected void onRootSet(AINode<T> root, T mob, Blackboard<T> blackboard) {
                }

                public void init(T mob, Blackboard<T> blackboard) {
                }

                public AINodeResult tick(T mob, Blackboard<T> blackboard) {
                    if (!mob.isHostile) {
                        blackboard.mover.stopMoving(mob);
                        return AINodeResult.FAILURE;
                    } else {
                        return AINodeResult.SUCCESS;
                    }
                }
            });
        }
    }
}
