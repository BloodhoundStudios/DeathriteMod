//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Items.Weapons.Xaeron;

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

public class XaeronGlaive extends GlaiveToolItem {
    public XaeronGlaive() {
        super(1600);
        this.rarity = Rarity.LEGENDARY;
        this.attackAnimTime.setBaseValue(550);
        this.attackDamage.setBaseValue(80.0F).setUpgradedValue(1.0F, 85.0F);
        this.attackRange.setBaseValue(225);
        this.knockback.setBaseValue(200);
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
                    this.level.entityManager.addParticle(this.attackMob.x + angleDir.x * 75.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y + angleDir.y * 75.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), GType.COSMETIC).color(new Color(0, 0, 255)).minDrawLight(150).givesLight(179.0F, 1.0F).lifeTime(400);
                    this.level.entityManager.addParticle(this.attackMob.x - angleDir.x * 75.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y - angleDir.y * 75.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), GType.COSMETIC).color(new Color(255, 0, 0)).minDrawLight(150).givesLight(179.0F, 1.0F).lifeTime(400);
                }
            });
        }

    }
}