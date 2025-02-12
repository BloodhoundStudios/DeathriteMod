package deathrite.Objects.Aether;

import necesse.level.gameObject.RockObject;
import java.awt.Color;

public class AetherRock extends RockObject {
    public AetherRock() {
        super("aetherrock",new Color(9, 99, 77),"aetherstone",1,5,1);
        toolTier = 6;
    }
}