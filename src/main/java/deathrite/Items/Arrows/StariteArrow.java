package deathrite.Items.Arrows;

import necesse.engine.registries.ProjectileRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.projectile.Projectile;
import necesse.inventory.item.arrowItem.ArrowItem;

public class StariteArrow extends ArrowItem {
    public StariteArrow() {
        this.damage = 24;
    }

    public Projectile getProjectile(float x, float y, float targetX, float targetY, float velocity, int range, GameDamage damage, int knockback, Mob owner) {
        return ProjectileRegistry.getProjectile("staritearrow", owner.getLevel(), x, y, targetX, targetY, velocity, range, damage, knockback, owner);
    }
}
