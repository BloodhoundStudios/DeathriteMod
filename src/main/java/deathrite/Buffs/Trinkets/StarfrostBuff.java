package deathrite.Buffs.Trinkets;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;

public class StarfrostBuff extends Buff {
    public StarfrostBuff() {
        this.canCancel = false;
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.addModifier(BuffModifiers.FROST_DAMAGE_FLAT, 7.0F);
    }

    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        if (buff.owner.isVisible()) {
            Mob owner = buff.owner;
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)6.0F), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(240, 201, 10)).givesLight(200.0F, 0.0F).height(16.0F);
        }

    }
}
