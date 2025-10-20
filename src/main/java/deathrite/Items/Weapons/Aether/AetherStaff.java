package deathrite.Items.Weapons.Aether;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketLevelEvent;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import deathrite.Events.WeaponEvents.AetherStaffEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.AncientDredgingStaffEvent;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.inventory.lootTable.presets.MagicWeaponsLootTable;
import necesse.level.maps.Level;

import java.awt.geom.Point2D;

public class AetherStaff extends MagicProjectileToolItem {
    public AetherStaff() {
        super(1300, MagicWeaponsLootTable.magicWeapons);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(550);
        this.attackDamage.setBaseValue(57.0F).setUpgradedValue(1.0F, 140.0F);
        this.velocity.setBaseValue(150);
        this.attackXOffset = 14;
        this.attackYOffset = 4;
        this.attackRange.setBaseValue(700);
        this.knockback.setBaseValue(50);
        this.manaCost.setBaseValue(2.5F).setUpgradedValue(1.0F, 4.5F);
        this.itemAttackerProjectileCanHitWidth = 5.0F;
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress) {
        super.setDrawAttackRotation(item, drawOptions, attackDirX, attackDirY, attackProgress);
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return null;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.magicbolt3, SoundEffect.effect(mob).pitch(1.2F));
        }

    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        int range = this.getAttackRange(item);
        Point2D.Float dir = new Point2D.Float((float)(x - attackerMob.getX()), (float)(y - attackerMob.getY()));
        AncientDredgingStaffEvent event = new AncientDredgingStaffEvent(attackerMob, attackerMob.getX(), attackerMob.getY(), new GameRandom((long)seed), GameMath.getAngle(dir), this.getAttackDamage(item), this.getResilienceGain(item), (float)this.getProjectileVelocity(item, attackerMob), (float)this.getKnockback(item, attackerMob), (float)range);
        attackerMob.addAndSendAttackerLevelEvent(event);
        this.consumeMana(attackerMob, item);
        return item;
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
        int velocity = this.getProjectileVelocity(item, mob);
        Point2D.Float targetPos = Projectile.getPredictedTargetPos(target, mob.x, mob.y, (float)velocity, -10.0F);
        mob.attackItem((int)targetPos.x, (int)targetPos.y, item);
        int range = this.getAttackRange(item);
        Point2D.Float dir = new Point2D.Float(targetPos.x - (float)mob.getX(), targetPos.y - (float)mob.getY());
        AetherStaffEvent event = new AetherStaffEvent(mob, mob.getX(), mob.getY(), new GameRandom((long)seed), GameMath.getAngle(dir), this.getAttackDamage(item), this.getResilienceGain(item), (float)velocity, (float)this.getKnockback(item, mob), (float)range);
        level.entityManager.addLevelEventHidden(event);
        if (level.isServer()) {
            level.getServer().network.sendToClientsWithEntity(new PacketLevelEvent(event), event);
        }

        return item;
    }
}
