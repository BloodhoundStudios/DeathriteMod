//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Items.Weapons.Xaeron;

import java.awt.geom.Point2D;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.XaeronDaggerProjectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.ThrowToolItem;
import necesse.level.maps.Level;

public class XaeronDagger extends ThrowToolItem {
    public XaeronDagger() {
        super(1200);
        this.attackAnimTime.setBaseValue(140);
        this.damageType = DamageTypeRegistry.MELEE;
        this.attackDamage.setBaseValue(70.0F).setUpgradedValue(1.0F, 80.0F);
        this.velocity.setBaseValue(200);
        this.rarity = Rarity.LEGENDARY;
        this.stackSize = 1;
        this.attackRange.setBaseValue(900);
        this.resilienceGain.setBaseValue(0.6F);
        this.itemAttackerProjectileCanHitWidth = 8.0F;
        this.setItemCategory(new String[]{"equipment", "weapons", "meleeweapons"});
        this.setItemCategory(ItemCategory.equipmentManager, new String[]{"weapons", "meleeweapons"});
        this.setItemCategory(ItemCategory.craftingManager, new String[]{"equipment", "weapons", "meleeweapons"});
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "xaerondaggertip1"));
        tooltips.add(Localization.translate("itemtooltip", "xaerondaggertip2"));
        return tooltips;
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return null;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        int velocity = this.getThrowingVelocity(item, player);
        XaeronDaggerProjectile projectile = new XaeronDaggerProjectile(level, player, player.x, player.y, (float)x, (float)y, (float)velocity, this.getAttackRange(item), this.getAttackDamage(item), this.getKnockback(item, player));
        projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        GameRandom random = new GameRandom((long)seed);
        projectile.resetUniqueID(random);
        projectile.moveDist((double)30.0F);
        projectile.setAngle(projectile.getAngle() + (float)random.getIntBetween(-15, 15));
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServer()) {
            level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, player.getServerClient());
        }

        return item;
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
        int velocity = this.getProjectileVelocity(item, mob);
        Point2D.Float targetPos = Projectile.getPredictedTargetPos(target, mob.x, mob.y, (float)velocity, -30.0F);
        mob.attackItem((int)targetPos.x, (int)targetPos.y, item);
        GameRandom random = new GameRandom((long)seed);
        XaeronDaggerProjectile projectile = new XaeronDaggerProjectile(level, mob, mob.x, mob.y, targetPos.x, targetPos.y, (float)velocity, this.getAttackRange(item), this.getAttackDamage(item), this.getKnockback(item, mob));
        projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        projectile.resetUniqueID(random);
        projectile.moveDist((double)30.0F);
        projectile.setAngle(projectile.getAngle() + (float)random.getIntBetween(-15, 15));
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServer()) {
            level.getServer().network.sendToClientsWithEntity(new PacketSpawnProjectile(projectile), projectile);
        }

        return item;
    }

    public boolean isEnchantable(InventoryItem item) {
        return item.getAmount() >= this.getStackSize();
    }

    public String getIsEnchantableError(InventoryItem item) {
        return item.getAmount() < this.getStackSize() ? Localization.translate("itemtooltip", "enchantfullstack") : super.getIsEnchantableError(item);
    }
}