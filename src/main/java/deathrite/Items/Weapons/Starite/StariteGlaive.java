//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Items.Weapons.Starite;

import necesse.engine.network.PacketReader;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.entity.levelEvent.GlaiveShowAttackEvent;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.particle.Particle.GType;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.glaiveToolItem.GlaiveToolItem;
import necesse.level.maps.Level;

import java.awt.*;
import java.awt.geom.Point2D;

public class StariteGlaive extends GlaiveToolItem {
    public StariteGlaive() {
        super(1600);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(525);
        this.attackDamage.setBaseValue(65.0F).setUpgradedValue(1.0F, 70.0F);
        this.attackRange.setBaseValue(215);
        this.knockback.setBaseValue(175);
        this.width = 20.0F;
        this.attackXOffset = 58;
        this.attackYOffset = 58;
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent) {
        super.showAttack(level, x, y, attackerMob, attackHeight, item, animAttack, seed, mapContent);
        if (level.isClient()) {
            level.entityManager.addLevelEventHidden(new GlaiveShowAttackEvent(attackerMob, x, y, seed, 10.0F) {
                public void tick(float angle) {
                    Point2D.Float angleDir = this.getAngleDir(angle);
                    this.level.entityManager.addParticle(this.attackMob.x + angleDir.x * 75.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y + angleDir.y * 75.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), GType.COSMETIC).color(new Color(255, 230, 0)).minDrawLight(150).givesLight(179.0F, 1.0F).lifeTime(400);
                    this.level.entityManager.addParticle(this.attackMob.x - angleDir.x * 75.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y - angleDir.y * 75.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), GType.COSMETIC).color(new Color(235, 184, 0)).minDrawLight(150).givesLight(179.0F, 1.0F).lifeTime(400);
                }
            });
        }

    }
}