//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Items.Weapons.Ridium;

import java.awt.Color;
import java.awt.geom.Point2D;
import necesse.engine.network.PacketReader;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.entity.levelEvent.GlaiveShowAttackEvent;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.particle.Particle.GType;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item.Rarity;
import necesse.inventory.item.toolItem.glaiveToolItem.GlaiveToolItem;
import necesse.inventory.lootTable.presets.CloseRangeWeaponsLootTable;
import necesse.inventory.lootTable.presets.GlaiveWeaponsLootTable;
import necesse.level.maps.Level;

public class RidiumGlaive extends GlaiveToolItem {
    public RidiumGlaive() {
        super(1600, GlaiveWeaponsLootTable.glaiveWeapons);
        this.rarity = Rarity.RARE;
        this.attackAnimTime.setBaseValue(450);
        this.attackDamage.setBaseValue(35.0F).setUpgradedValue(1.0F, 40.0F);
        this.attackRange.setBaseValue(145);
        this.knockback.setBaseValue(100);
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
                    this.level.entityManager.addParticle(this.attackMob.x + angleDir.x * 75.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y + angleDir.y * 75.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), GType.COSMETIC).color(new Color(227, 191, 9)).minDrawLight(150).givesLight(179.0F, 1.0F).lifeTime(400);
                    this.level.entityManager.addParticle(this.attackMob.x - angleDir.x * 75.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y - angleDir.y * 75.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), GType.COSMETIC).color(new Color(61, 6, 71)).minDrawLight(150).givesLight(179.0F, 1.0F).lifeTime(400);
                }
            });
        }

    }
}