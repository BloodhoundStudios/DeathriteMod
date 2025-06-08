package deathrite.Items.Weapons.Ridium;

import deathrite.Projectiles.RidiumArrowVisualProjectile;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.SageArrowProjectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.level.maps.Level;

public class RidiumBow extends BowProjectileToolItem {
    public RidiumBow() {
        super(1500);
        this.attackAnimTime.setBaseValue(450);
        this.rarity = Item.Rarity.EPIC;
        this.attackDamage.setBaseValue(43.0F).setUpgradedValue(1.0F, 85.0F);
        this.velocity.setBaseValue(225);
        this.attackRange.setBaseValue(800);
        this.attackXOffset = 12;
        this.attackYOffset = 28;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.magicbolt1, SoundEffect.effect(mob).pitch(1.1F));
        }

    }

    protected void addExtraBowTooltips(ListGameTooltips tooltips, InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        tooltips.add(Localization.translate("itemtooltip", "ridiumbowtip"));
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return new LocalMessage("ui", "settlercantuseitem");
    }

    protected void fireProjectiles(Level level, int x, int y, PlayerMob player, InventoryItem item, int seed, ArrowItem arrow, boolean consumeAmmo, PacketReader contentReader) {
        GameRandom random = new GameRandom((long)seed);
        boolean left = (float)x < player.x;
        Projectile ridiumProjectile = new RidiumArrowVisualProjectile(player, player.x, player.y, (float)x, (float)y, (float)this.getProjectileVelocity(item, player) * arrow.speedMod, this.getAttackRange(item), this.getAttackDamage(item).add((float)arrow.damage, arrow.armorPen, arrow.critChance), this.getKnockback(item, player));
        ridiumProjectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        ridiumProjectile.setAngle(ridiumProjectile.getAngle() + (float)(left ? 10 : -10));
        ridiumProjectile.dropItem = false;
        ridiumProjectile.getUniqueID(random);

        Projectile ridium2Projectile = new RidiumArrowVisualProjectile(player, player.x, player.y, (float)x, (float)y, (float)this.getProjectileVelocity(item, player) * arrow.speedMod, this.getAttackRange(item), this.getAttackDamage(item).add((float)arrow.damage, arrow.armorPen, arrow.critChance), this.getKnockback(item, player));
        ridium2Projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        ridium2Projectile.setAngle(ridium2Projectile.getAngle() - (float)(left ? 10 : -10));
        ridium2Projectile.dropItem = false;
        ridium2Projectile.getUniqueID(random);

        Projectile ridium3Projectile = new RidiumArrowVisualProjectile(player, player.x, player.y, (float)x, (float)y, (float)this.getProjectileVelocity(item, player) * arrow.speedMod, this.getAttackRange(item), this.getAttackDamage(item).add((float)arrow.damage, arrow.armorPen, arrow.critChance), this.getKnockback(item, player));
        ridium3Projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        ridium3Projectile.setAngle(ridium3Projectile.getAngle() - (float)(left ? 0 : -0));
        ridium3Projectile.dropItem = false;
        ridium3Projectile.getUniqueID(random);

        level.entityManager.projectiles.addHidden(ridiumProjectile);
        level.entityManager.projectiles.addHidden(ridium2Projectile);
        level.entityManager.projectiles.addHidden(ridium3Projectile);
        if (level.isServer()) {
            level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(ridiumProjectile), ridiumProjectile, player.getServerClient());
            level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(ridium2Projectile), ridium2Projectile, player.getServerClient());
            level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(ridium3Projectile), ridium3Projectile, player.getServerClient());
        }

    }
}
