package deathrite.Objects.Workstations;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.journal.JournalChallenge;
import necesse.engine.localization.Localization;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.JournalChallengeRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptionsList;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Tech;
import necesse.level.gameObject.*;
import necesse.level.gameObject.container.CraftingStationObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.level.maps.multiTile.MultiTile;
import necesse.level.maps.multiTile.SideMultiTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AethiumAnvilObject extends CraftingStationObject {
    public ObjectDamagedTextureArray texture;

    public AethiumAnvilObject() {
        super(new Rectangle(32, 32));
        this.mapColor = new Color(148, 99, 25);
        this.toolType = ToolType.ALL;
        this.objectHealth = 50;
        this.isLightTransparent = true;
        this.roomProperties.add("potionwork");
        this.hoverHitbox = new Rectangle(0, -16, 32, 48);
    }

    public int getCraftingCategoryDepth() {
        return 2;
    }

//    public CraftingStationUpgrade getStationUpgrade() {
//        return new CraftingStationUpgrade(ObjectRegistry.getObject("voidalchemytable"), new Ingredient[]{new Ingredient("glassbottle", 5), new Ingredient("voidshard", 8)});
//    }
//
//    public void performUpgrade(GameObject upgradeObject, Level level, int tileX, int tileY, ServerClient client) {
//        super.performUpgrade(upgradeObject, level, tileX, tileY, client);
//        JournalChallenge challenge = JournalChallengeRegistry.getChallenge(JournalChallengeRegistry.UPGRADE_ALCHEMY_TABLE);
//        if (!challenge.isCompleted(client) && challenge.isJournalEntryDiscovered(client)) {
//            challenge.markCompleted(client);
//            client.forceCombineNewStats();
//        }
//
//    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/aethiumanvil");
    }

    public Rectangle getCollision(Level level, int x, int y, int rotation) {
        return rotation % 2 == 0 ? new Rectangle(x * 32 + 2, y * 32 + 6, 28, 20) : new Rectangle(x * 32 + 4, y * 32 + 2, 24, 28);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        int rotation = level.getObjectRotation(tileX, tileY) % 4;
        GameTexture texture = this.texture.getDamagedTexture(this, level, tileX, tileY);
        final TextureDrawOptions options = texture.initDraw().sprite(rotation % 4, 0, 32, texture.getHeight()).light(light).pos(drawX, drawY - texture.getHeight() + 32);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }

            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        GameTexture texture = this.texture.getDamagedTexture(0.0F);
        texture.initDraw().sprite(rotation % 4, 0, 32, texture.getHeight()).alpha(alpha).draw(drawX, drawY - texture.getHeight() + 32);
    }

    public Tech[] getCraftingTechs() {
        return new Tech[]{RecipeTechRegistry.getTech("aethiumanvil") ,RecipeTechRegistry.TUNGSTEN_ANVIL, RecipeTechRegistry.IRON_ANVIL, RecipeTechRegistry.FALLEN_ANVIL, RecipeTechRegistry.DEMONIC_ANVIL};
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "aethiumanviltip"));
        return tooltips;
    }
}
