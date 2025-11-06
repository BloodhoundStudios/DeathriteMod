package deathrite.Items.Weapons.Aethium;

import deathrite.Projectiles.AethiumStaffRidiumBallProjectile;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.inventory.lootTable.presets.MagicWeaponsLootTable;
import necesse.level.maps.Level;

import java.awt.geom.Point2D;

public class AethiumStaff extends MagicProjectileToolItem {
    public AethiumStaff() {
        super(1400, MagicWeaponsLootTable.magicWeapons);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(600);
        this.attackDamage.setBaseValue(52.0F).setUpgradedValue(1.0F, 65.0F);
        this.knockback.setBaseValue(80);
        this.attackXOffset = 20;
        this.attackYOffset = 20;
        this.velocity.setBaseValue(100);
        this.attackRange.setBaseValue(200);
        this.manaCost.setBaseValue(2.25F).setUpgradedValue(1.0F, 4.5F);
        this.resilienceGain.setBaseValue(2.0F);
        this.itemAttackerProjectileCanHitWidth = 10.0F;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "aethiumstafftip"));
        return tooltips;
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return null;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.magicbolt2, SoundEffect.effect(mob).volume(0.8F).pitch(GameRandom.globalRandom.getFloatBetween(0.9F, 1.0F)));
        }

    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        AethiumStaffRidiumBallProjectile projectile = new AethiumStaffRidiumBallProjectile(player.x, player.y, (float)x, (float)y, (float)this.getProjectileVelocity(item, player), this.getAttackRange(item), this.getAttackDamage(item), this.getKnockback(item, player), player);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        projectile.resetUniqueID(new GameRandom((long)seed));
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServer()) {
            level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, player.getServerClient());
        }

        this.consumeMana(player, item);
        return item;
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
        int velocity = this.getProjectileVelocity(item, mob);
        Point2D.Float targetPos = Projectile.getPredictedTargetPos(target, mob.x, mob.y, (float)velocity, -10.0F);
        mob.attackItem((int)targetPos.x, (int)targetPos.y, item);
        AethiumStaffRidiumBallProjectile projectile = new AethiumStaffRidiumBallProjectile(mob.x, mob.y, targetPos.x, targetPos.y, (float)velocity, this.getAttackRange(item), this.getAttackDamage(item), this.getKnockback(item, mob), mob);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        projectile.resetUniqueID(new GameRandom((long)seed));
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServer()) {
            level.getServer().network.sendToClientsWithEntity(new PacketSpawnProjectile(projectile), projectile);
        }

        return item;
    }
}
