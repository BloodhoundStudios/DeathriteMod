//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Biomes.Aether;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.GenerateCaveLayoutEvent;
import necesse.engine.events.worldGeneration.GenerateCaveMiniBiomesEvent;
import necesse.engine.events.worldGeneration.GenerateCaveOresEvent;
import necesse.engine.events.worldGeneration.GenerateCaveStructuresEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveLayoutEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveMiniBiomesEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveOresEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveStructuresEvent;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameLinkedList;
import necesse.engine.util.GameMath;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.util.TicketSystemList;
import necesse.engine.world.WorldEntity;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.generationModules.CaveGeneration;
import necesse.level.maps.generationModules.CellAutomaton;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.LinesGeneration;
import necesse.level.maps.generationModules.PresetGeneration;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.RandomCaveChestRoom;
import necesse.level.maps.presets.RandomLootAreaPreset;
import necesse.level.maps.presets.set.ChestRoomSet;

public class AetherCaveLevel extends AetherSurfaceLevel {
    public AetherCaveLevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public AetherCaveLevel(int islandX, int islandY, int dimension, WorldEntity worldEntity, Biome biome) {
        super(new LevelIdentifier(islandX, islandY, dimension), 300, 300, worldEntity);
        this.biome = biome;
        this.isCave = true;
        this.generateLevel();
    }

    public void generateLevel() {
        System.out.println("Aether Cave Generated");
        CaveGeneration cg = new CaveGeneration(this, "aetherstonefloor", "aetherrock");
        GameEvents.triggerEvent(new GenerateCaveLayoutEvent(this, cg), (e) -> cg.generateLevel());
        GameEvents.triggerEvent(new GeneratedCaveLayoutEvent(this, cg));
        GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.02F, 2, 2.0F, 10.0F, 2.0F, 4.0F, TileRegistry.getTileID("lavatile"), 1.0F, true);

        this.liquidManager.calculateShores();
        GameEvents.triggerEvent(new GenerateCaveMiniBiomesEvent(this, cg), (e) -> {
            GenerationTools.generateRandomSmoothVeinsL(this, cg.random, 0.01F, 5, 7.0F, 15.0F, 3.0F, 5.0F, (lg) -> {
                LinesGeneration lgRoot = lg.getRoot();
                double centerDist = (new Point(this.width / 2, this.height / 2)).distance((double)lgRoot.x1, (double)lgRoot.y1);
                if (centerDist >= (double)40.0F) {
                    CellAutomaton ca = lg.doCellularAutomaton(cg.random);
                    ca.forEachTile(this, (level, tileX, tileY) -> {
                        level.setTile(tileX, tileY, TileRegistry.spiderNestID);
                        if (cg.random.getChance(0.95F)) {
                            level.setObject(tileX, tileY, ObjectRegistry.cobWebID);
                        } else {
                            level.setObject(tileX, tileY, 0);
                        }
                    });
                }
            });
            GenerationTools.generateRandomPoints(this, cg.random, 0.01F, 15, (p) -> {
                LinesGeneration lg = new LinesGeneration(p.x, p.y);
                ArrayList<LinesGeneration> tntArms = new ArrayList();
                int armAngle = cg.random.nextInt(4) * 90;
                int arms = cg.random.getIntBetween(3, 10);

                for(int i = 0; i < arms; ++i) {
                    lg = lg.addArm((float)cg.random.getIntOffset(armAngle, 20), (float)cg.random.getIntBetween(5, 25), 3.0F);
                    tntArms.add(lg);
                    int angleChange = (Integer)cg.random.getOneOfWeighted(Integer.class, new Object[]{15, 0, 5, 90, 5, -90});
                    armAngle += angleChange;
                }

                CellAutomaton ca = lg.doCellularAutomaton(cg.random);
                ca.forEachTile(this, (level, tileX, tileY) -> {
                    if (level.isSolidTile(tileX, tileY)) {
                        level.setObject(tileX, tileY, 0);
                    }

                });
                ArrayList<Point> trackTiles = new ArrayList();
                lg.getRoot().recursiveLines((lg2) -> {
                    GameLinkedList<Point> tiles = new GameLinkedList();
                    LinesGeneration.pathTiles(lg2.getTileLine(), true, (from, next) -> tiles.add(next));
                    return true;
                });
                int minecartCount = (Integer)cg.random.getOneOfWeighted(Integer.class, new Object[]{100, 0, 200, 1, 50, 2});

                for(int i = 0; i < minecartCount && !trackTiles.isEmpty(); ++i) {
                    int index = cg.random.nextInt(trackTiles.size());
                    Point next = (Point)trackTiles.remove(index);
                }

                int tntCount = (Integer)cg.random.getOneOfWeighted(Integer.class, new Object[]{100, 0, 200, 1, 50, 2});

                for(int i = 0; i < tntCount && !tntArms.isEmpty(); ++i) {
                    int index = cg.random.nextInt(tntArms.size());
                    LinesGeneration next = (LinesGeneration)tntArms.remove(index);
                    int wireLength = cg.random.getIntBetween(10, 14) * (Integer)cg.random.getOneOf(new Integer[]{1, -1});
                    float lineLength = (float)(new Point(next.x1, next.y1)).distance((double)next.x2, (double)next.y2);
                    Point2D.Float dir = GameMath.normalize((float)(next.x1 - next.x2), (float)(next.y1 - next.y2));
                    float linePointLength = cg.random.getFloatBetween(0.0F, lineLength);
                    Point2D.Float linePoint = new Point2D.Float((float)next.x2 + dir.x * linePointLength, (float)next.y2 + dir.y * linePointLength);
                    Point2D.Float leverPoint = GameMath.getPerpendicularPoint(linePoint, 2.0F * Math.signum((float)wireLength), dir);
                    Point2D.Float tntPoint = GameMath.getPerpendicularPoint(linePoint, (float)wireLength, dir);
                    Line2D.Float wireLine = new Line2D.Float(leverPoint, tntPoint);
                    LinkedList<Point> tiles = new LinkedList();
                    LinesGeneration.pathTiles(wireLine, true, (fromTile, nextTile) -> tiles.add(nextTile));

                    for(Point tile : tiles) {
                        this.wireManager.setWire(tile.x, tile.y, 0, true);
                        if (this.getObject(tile.x, tile.y).isSolid) {
                            this.setObject(tile.x, tile.y, 0);
                        }
                    }

                    Point first = (Point)tiles.getFirst();
                    Point last = (Point)tiles.getLast();
                }

            });
            GenerationTools.generateRandomSmoothVeinsL(this, cg.random, 0.002F, 4, 4.0F, 7.0F, 4.0F, 6.0F, (lg) -> {
                CellAutomaton ca = lg.doCellularAutomaton(cg.random);
                ArrayList<Point> validChestTiles = new ArrayList();
                ca.streamAliveOrdered().forEachOrdered((tile) -> {
                    cg.addIllegalCrateTile(tile.x, tile.y);
                    this.setTile(tile.x, tile.y, TileRegistry.mudID);
                    this.setObject(tile.x, tile.y, 0);
                    validChestTiles.add(new Point(tile));
                    GameTile gravelTile = TileRegistry.getTile(TileRegistry.gravelID);
                    if (cg.random.getChance(0.3F) && gravelTile.canPlace(this, tile.x, tile.y, false) == null) {
                        gravelTile.placeTile(this, tile.x, tile.y, false);
                    }

                });
            });
            this.liquidManager.calculateShores();
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("aetherrock"), 0.005F);
        });
        GameEvents.triggerEvent(new GeneratedCaveMiniBiomesEvent(this, cg));
        GameEvents.triggerEvent(new GenerateCaveOresEvent(this, cg), (e) -> {
            cg.generateOreVeins(0.1F, 3, 6, ObjectRegistry.getObjectID("ridiumoreobject"));
            cg.generateOreVeins(0.1F, 3, 6, ObjectRegistry.getObjectID("aetheroreobject"));
        });
        GameEvents.triggerEvent(new GeneratedCaveOresEvent(this, cg));
        PresetGeneration presets = new PresetGeneration(this);
        GameEvents.triggerEvent(new GenerateCaveStructuresEvent(this, cg, presets), (e) -> {
            AtomicInteger chestRoomRotation = new AtomicInteger();
            int chestRoomAmount = cg.random.getIntBetween(16, 20);

            for(int i = 0; i < chestRoomAmount; ++i) {
                Preset chestRoom = new RandomCaveChestRoom(cg.random, LootTablePresets.basicCaveChest, chestRoomRotation, new ChestRoomSet[]{ChestRoomSet.stone, ChestRoomSet.wood});
                chestRoom.replaceTile(TileRegistry.stoneFloorID, (Integer)cg.random.getOneOf(new Integer[]{TileRegistry.stoneFloorID, TileRegistry.stoneBrickFloorID}));
                presets.findRandomValidPositionAndApply(cg.random, 5, chestRoom, 10, true, true);
            }

            int lootAreaAmount = cg.random.getIntBetween(5, 10);

            for(int i = 0; i < lootAreaAmount; ++i) {
                TicketSystemList<String> mobs = new TicketSystemList();
                mobs.addObject(100, "goblin");
                Preset lootArea = new RandomLootAreaPreset(cg.random, 15, "stonecolumn", mobs);
                presets.findRandomValidPositionAndApply(cg.random, 5, lootArea, 10, true, true);
            }

            AtomicInteger caveRuinsRotation = new AtomicInteger();
            int caveRuinsCount = cg.random.getIntBetween(25, 35);
        });
        GameEvents.triggerEvent(new GeneratedCaveStructuresEvent(this, cg, presets));
        GenerationTools.checkValid(this);
    }

    public GameMessage getLocationMessage() {
        return new LocalMessage("biome", "cave", "biome", this.biome.getLocalization());
    }

    public GameTile getUnderLiquidTile(int tileX, int tileY) {
        return TileRegistry.getTile(TileRegistry.dirtID);
    }

    public float getLiquidSaltWaterSinkRate() {
        return 4.0F;
    }

    public float getLiquidFreshWaterSinkRate() {
        return 10.0F;
    }

    public float getLiquidMobSinkRate() {
        return 10.0F;
    }
}
