package deathrite.Overrides;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.level.gameObject.LadderDownObject;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(
        target = LadderDownObject.class,
        name = "canPlace",
        arguments = {Level.class, int.class, int.class, int.class, int.class, boolean.class, boolean.class}
)
public class LadderCanPlacePatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This LadderDownObject ladderDownObject, @Advice.Argument(0) int layerID, @Advice.Argument(1) int x, @Advice.Argument(2) int y, @Advice.Argument(3) int rotation, @Advice.Argument(4) boolean byPlayer, @Advice.Argument(5) boolean ignoreOtherLayers, @Advice.Return(readOnly = false) Level level, String result) {
        String error = ladderDownObject.canPlace(level, layerID, x, y, rotation, byPlayer, ignoreOtherLayers);
        if (level.isIslandPosition()) {
            result = level.getIslandDimension() != 10 ? "notsurface" : null;
        }
    }
}
