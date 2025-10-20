package deathrite.Objects.Aether;

import necesse.level.gameObject.RockObject;
import java.awt.Color;

public class AetherRock extends RockObject {
    public AetherRock() {
        super("aetherrock",new Color(9, 97, 75),"aetherstone",1,5,1, new String[0]);
        toolTier = 10;
    }
}