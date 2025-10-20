package deathrite.Objects.Space;

import necesse.level.gameObject.RockObject;

import java.awt.*;

public class SpaceRock extends RockObject {
    public SpaceRock() {
        super("spacerock",new Color(57, 60, 66),"spacestone",1,5,1);
        toolTier = 11;
    }
}
