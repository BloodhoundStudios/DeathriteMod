package deathrite;

import deathrite.Armor.Aethium.AethiumBootsItem;
import deathrite.Armor.Aethium.AethiumChestplateItem;
import deathrite.Armor.Aethium.AethiumHelmetItem;
import deathrite.Armor.Ridium.RidiumBootsItem;
import deathrite.Armor.Ridium.RidiumChestplateItem;
import deathrite.Armor.Ridium.RidiumHelmetItem;
import deathrite.Biomes.Aether.*;
import deathrite.Buffs.Trinkets.Arrowheads.RidiumArrowheadBuff;
import deathrite.Buffs.Trinkets.BladeSharpeners.*;
import deathrite.Buffs.Trinkets.StarfrostBuff;
import deathrite.Items.BossDrops.*;
import deathrite.Items.BossSummons.StarryWormSpawnItem;
import deathrite.Items.BossSummons.TabletofSpiritsSpawnItem;
import deathrite.Items.Materials.Aether.AetherBar;
import deathrite.Items.Materials.Aether.AetherOre;
import deathrite.Items.Materials.Aether.AetherStone;
import deathrite.Items.Materials.Aether.PassedSpirits;
import deathrite.Items.Materials.Aethium.AethiumBar;
import deathrite.Items.Materials.Essence.EssenceOfTheGods;
import deathrite.Items.Materials.Essence.MoonEssence;
import deathrite.Items.Materials.Essence.SunEssence;
import deathrite.Items.Materials.Ridium.RidiumBar;
import deathrite.Items.Materials.Ridium.RidiumOre;
import deathrite.Items.Materials.SkyCore;
import deathrite.Items.Materials.Space.SpaceStone;
import deathrite.Items.Materials.Stardust;
import deathrite.Items.Materials.Starite.StariteOre;
import deathrite.Items.Materials.Starite.StarryBar;
import deathrite.Items.Materials.Xaeron.CrystalizedXaeronBar;
import deathrite.Items.Materials.Xaeron.XaeronBar;
import deathrite.Items.Materials.Xaeron.XaeronOre;
import deathrite.Items.Weapons.Aethium.AethiumSword;
import deathrite.Items.Weapons.Dev.DevSword;
import deathrite.Items.Weapons.Ridium.RidiumSword;
import deathrite.Items.Weapons.Starite.StarryFang;
import deathrite.Journal.DeathriteJournalChallenges;
import deathrite.Mobs.Bosses.StarfangedDestroyer.*;
import deathrite.Mobs.Friendly.Caveling.AetherCaveling;
import deathrite.Mobs.Hostile.AetherSpirit;
import deathrite.Mobs.Bosses.RidiumKnightBoss;
import deathrite.Objects.Aether.AetherRock;
import deathrite.Objects.Space.SpaceRock;
import deathrite.Objects.Space.UpgradedSpaceRock;
import deathrite.Objects.Workstations.AethiumAnvilObject;
import deathrite.Objects.Workstations.AethiumWorkstationObject;
import deathrite.Objects.Workstations.DeathriteSummonerObject;
import deathrite.Projectiles.ShootingStarProjectile;
import deathrite.Tiles.Aether.AetherSandTile;
import deathrite.Tiles.Aether.AetherStoneTile;
import deathrite.Tiles.Sky.SkyCloudTile;
import deathrite.Tiles.Space.SpaceStoneTile;
import necesse.engine.GameEventListener;
import necesse.engine.GameEvents;
import necesse.engine.events.loot.MobLootTableDropsEvent;
import necesse.engine.journal.JournalEntry;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.engine.sound.GameMusic;
import necesse.entity.mobs.HumanTexture;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.Item.Rarity;
import necesse.inventory.item.matItem.MatItem;
import necesse.inventory.item.placeableItem.mapItem.BiomeMapItem;
import necesse.inventory.item.toolItem.axeToolItem.CustomAxeToolItem;
import necesse.inventory.item.toolItem.pickaxeToolItem.CustomPickaxeToolItem;
import necesse.inventory.item.toolItem.shovelToolItem.CustomShovelToolItem;
import necesse.inventory.item.trinketItem.SimpleTrinketItem;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;

import java.awt.*;
import java.util.Random;
import java.util.logging.Level;

// Normal = White
// Common = Green
// Uncommon = Blue
// Rare = Yellow
// Epic = Purple
// Legendary = Orange

@ModEntry
public class DeathriteMod {

    public static GameMusic AetherMusic;
    public static GameMusic StarfangedMusic;
    public static GameTexture StarfangedDestroyerTexture;
    public static HumanTexture AetherCavelingTexture;
    public static int[] AethiumWorkstation;

    public void init() {
        // Journal Challenges
        DeathriteJournalChallenges.registerCore();

        // Journal Surface
        JournalEntry forestSurfaceJournal = JournalRegistry.getJournalEntry("forestsurface");
        forestSurfaceJournal.addEntryChallenges(DeathriteJournalChallenges.EOTG_REWARD_ID);

        // Tiles
        TileRegistry.registerTile("skycloudfloor", new SkyCloudTile(), 1, true);
        TileRegistry.registerTile("aethersandfloor", new AetherSandTile(), 1, true);
        TileRegistry.registerTile("aetherstonefloor", new AetherStoneTile(), 1, true);
        TileRegistry.registerTile("spacestonefloor", new SpaceStoneTile(), 1, true);

        // Rock Objects
        RockObject aetherRock;
        ObjectRegistry.registerObject("aetherrock", aetherRock = new AetherRock(), 50, true);
        ObjectRegistry.registerObject("spacerock", new SpaceRock(), 100, true);
        ObjectRegistry.registerObject("upgradedspacerock", new UpgradedSpaceRock(), 100, true);

        // Rock Deco
        ObjectRegistry.registerObject("aethercaverocksmall", new SingleRockSmall(aetherRock, "aethercaverocksmall", new Color(27, 181, 161)), -1.0F, true);

        // OreRock
        ObjectRegistry.registerObject("aetheroreobject", new RockOreObject((RockObject)ObjectRegistry.getObject("aetherrock"), "oremask", "aetheroreobject", new Color(123, 196, 219), "aetherore"), -1.0F, true);
        ObjectRegistry.registerObject("ridiumoreobject", new RockOreObject((RockObject)ObjectRegistry.getObject("aetherrock"), "oremask", "ridiumoreobject", new Color(137, 156, 17), "ridiumore"), -1.0F, true);
        ObjectRegistry.registerObject("stariteoreobject", new RockOreObject((RockObject)ObjectRegistry.getObject("spacerock"), "oremask", "stariteoreobject", new Color(224, 224, 25), "stariteore"), -1.0F, true);
        ObjectRegistry.registerObject("xaeronoreobject", new RockOreObject((RockObject)ObjectRegistry.getObject("upgradedspacerock"), "oremask", "xaeronoreobject", new Color(63, 7, 82), "xaeronore"), -1.0F, true);

        // Trees
        ObjectRegistry.registerObject("aethertree", new TreeObject("aethertree", "aetherlog", "aethersapling", new Color(255, 255, 255), 40, 80, 120, "aetherleaves"), 0.0F, false);
        ObjectRegistry.registerObject("aethersapling", new TreeSaplingObject("aethersapling", "aethertree", 1800, 2700, true, new String[]{"aethersandfloor"}), 5.0F, true);

        // Objects
        LadderDownObject.registerLadderPair("skyladder", 10, new Color(221, 232, 237), Rarity.EPIC, 20);
        LadderDownObject.registerLadderPair("wormhole", -10, new Color(56, 8, 84), Rarity.LEGENDARY, 50);

        // Workstations
        DeathriteSummonerObject.registerWorkstation();
        AethiumWorkstationObject.registerWorkstation();
        ObjectRegistry.registerObject("aethiumanvil", new AethiumAnvilObject(), 216, true);

        // Biomes
        BiomeRegistry.registerBiome("aether", new AetherBiome(), 50, null);

        // Buffs
        BuffRegistry.registerBuff("ridiumarrowheadbuff", new RidiumArrowheadBuff());
        BuffRegistry.registerBuff("ridiumbladesharpener", new RidiumBladeSharpener());
        BuffRegistry.registerBuff("aetherbladesharpener", new AetherBladeSharpener());
        BuffRegistry.registerBuff("aethiumbladesharpener", new AethiumBladeSharpener());
        BuffRegistry.registerBuff("bladesharpener", new BladeSharpener());
        BuffRegistry.registerBuff("starfrost", new StarfrostBuff());
        BuffRegistry.registerBuff("staritebladesharpener", new StariteBladeSharpener());
        BuffRegistry.registerBuff("xaeronbladesharpener", new XaeronBladeSharpener());

        // Recipe Techs
        RecipeTechRegistry.registerTech("deathritesummoner", "deathritesummoner");
        RecipeTechRegistry.registerTech("aethiumworkstation", "aethiumworkstation");
        RecipeTechRegistry.registerTech("aethiumanvil", "aethiumanvil");

        // Ores
        ItemRegistry.registerItem("ridiumore", new RidiumOre(), 2, true);
        ItemRegistry.registerItem("aetherore", new AetherOre(), 2, true);
        ItemRegistry.registerItem("stariteore", new StariteOre(), 4, true);
        ItemRegistry.registerItem("xaeronore", new XaeronOre(), 6, true);

        // Bars
        ItemRegistry.registerItem("ridiumbar", new RidiumBar(), 8, true);
        ItemRegistry.registerItem("aetherbar", new AetherBar(), 8, true);
        ItemRegistry.registerItem("aethiumbar", new AethiumBar(), 36, true);
        ItemRegistry.registerItem("starrybar", new StarryBar(), 52, true);
        ItemRegistry.registerItem("xaeronbar", new XaeronBar(), 24, true);
        ItemRegistry.registerItem("crystalizedxaeronbar", new CrystalizedXaeronBar(), 548, true);

        // Weapons
        ItemRegistry.registerItem("devsword", new DevSword(), 9999, false);
        ItemRegistry.registerItem("ridiumsword", new RidiumSword(), 200, true);
        ItemRegistry.registerItem("aethiumsword", new AethiumSword(), 360, true);
        ItemRegistry.registerItem("starryfang", new StarryFang(), 770, true);

        // Tools
        ItemRegistry.registerItem("ridiumpickaxe", new CustomPickaxeToolItem(400, 200, 6, 30, 50, 55, 1200, Rarity.RARE), 128, true);
        ItemRegistry.registerItem("ridiumaxe", new CustomAxeToolItem(400, 200, 6, 30, 50, 55, 1200, Rarity.RARE), 128, true);
        ItemRegistry.registerItem("ridiumshovel", new CustomShovelToolItem(400, 200, 6, 30, 50, 55, 1200, Rarity.RARE), 128, true);
        ItemRegistry.registerItem("aethiumpickaxe", new CustomPickaxeToolItem(450, 220, 7, 35, 55, 55, 1400, Rarity.EPIC), 576, true);
        ItemRegistry.registerItem("aethiumaxe", new CustomAxeToolItem(450, 220, 7, 35, 55, 55, 1400, Rarity.EPIC), 576, true);
        ItemRegistry.registerItem("aethiumshovel", new CustomShovelToolItem(450, 220, 7, 35, 55, 55, 1400, Rarity.EPIC), 576, true);


        // Items
        ItemRegistry.registerItem("aetherstone", new AetherStone(), 1.5F, true);
        ItemRegistry.registerItem("spacestone", new SpaceStone(), 2, true);
        ItemRegistry.registerItem("aethermap", new BiomeMapItem(Rarity.EPIC, 500, new String[]{"aether"}), 200.0F, true);
        ItemRegistry.registerItem("passedspirits", new PassedSpirits(), 2.5F, true);
        ItemRegistry.registerItem("moonessence", new MoonEssence(), 30, true);
        ItemRegistry.registerItem("sunessence", new SunEssence(), 30, true);
        ItemRegistry.registerItem("essenceofthegods", new EssenceOfTheGods(), 0, true);
        ItemRegistry.registerItem("deathrite_stardust", new Stardust(), 10, true);
        ItemRegistry.registerItem("skycore", new SkyCore(), 25, true);

        // Logs
        ItemRegistry.registerItem("aetherlog", (new MatItem(500, new String[]{"anylog"})).setItemCategory(new String[]{"materials", "logs"}), 2.0F, true);

        // Trinkets
        ItemRegistry.registerItem("ridiumarrowhead", new SimpleTrinketItem(Rarity.EPIC, "ridiumarrowheadbuff", 10),5, true);
        ItemRegistry.registerItem("ridiumbladesharpener", new SimpleTrinketItem(Rarity.EPIC, "ridiumbladesharpener", 10).addDisables("bladesharpener"), 27, true);
        ItemRegistry.registerItem("aetherbladesharpener", new SimpleTrinketItem(Rarity.EPIC, "aetherbladesharpener", 10).addDisables("bladesharpener"), 27, true);
        ItemRegistry.registerItem("aethiumbladesharpener", new SimpleTrinketItem(Rarity.EPIC, "aethiumbladesharpener", 10).addDisables("bladesharpener", "ridiumbladesharpener", "aetherbladesharpener"), 69, true);
        ItemRegistry.registerItem("bladesharpener", new SimpleTrinketItem(Rarity.COMMON, "bladesharpener", 5), 11, true);
        ItemRegistry.registerItem("staritebladesharpener", new SimpleTrinketItem(Rarity.LEGENDARY, "staritebladesharpener", 10).addDisables("bladesharpener", "ridiumbladesharpener", "aetherbladesharpener", "aethiumbladesharpener"), 173, true);
        ItemRegistry.registerItem("xaeronbladesharpener", new SimpleTrinketItem(Rarity.LEGENDARY, "xaeronbladesharpener", 10).addDisables("bladesharpener", "ridiumbladesharpener", "aetherbladesharpener", "aethiumbladesharpener", "staritebladesharpener"), 221, true);

        // Armor
        ItemRegistry.registerItem("ridiumhelmet", new RidiumHelmetItem(), 10, true);
        ItemRegistry.registerItem("ridiumchestplate", new RidiumChestplateItem(), 10, true);
        ItemRegistry.registerItem("ridiumboots", new RidiumBootsItem(), 10, true);
        ItemRegistry.registerItem("aethiumhelmet", new AethiumHelmetItem(), 20, true);
        ItemRegistry.registerItem("aethiumchestplate", new AethiumChestplateItem(), 20, true);
        ItemRegistry.registerItem("aethiumboots", new AethiumBootsItem(), 20, true);

        // Boss Drops
        ItemRegistry.registerItem("knightsheart", new KnightsHeart(), 15, true);
        ItemRegistry.registerItem("motherslimesleftoverslime", new MotherSlimesLeftoverSlime(), 400, true);
        ItemRegistry.registerItem("spiderempresscrown", new SpiderEmpressCrown(), 450, true);
        ItemRegistry.registerItem("thepowerofthemoon", new ThePowerOfTheMoon(), 500, true);
        ItemRegistry.registerItem("thepowerofthesun", new ThePowerOfTheSun(), 500, true);
        ItemRegistry.registerItem("thepoweroftheeclipse", new ThePowerOfTheEclipse(), 1000, true);
        ItemRegistry.registerItem("darkmatter", new DarkMatter(), 50, true);
        ItemRegistry.registerItem("crystalscale", new CrystalScale(), 50, true);

        // Boss Summons
        ItemRegistry.registerItem("tabletofspirits", new TabletofSpiritsSpawnItem(), 500, true);
        ItemRegistry.registerItem("starryworm", new StarryWormSpawnItem(), 9450, true);


        // Mobs
        MobRegistry.registerMob("aetherspirit", AetherSpirit.class, true);
        MobRegistry.registerMob("aethercaveling", AetherCaveling.class, true);

        // Bosses
        MobRegistry.registerMob("starfangeddestroyer", StarfangedDestroyerHead.class, true, true);
        MobRegistry.registerMob("starfangeddestroyerbody", StarfangedDestroyerBody.class, false, true);
        MobRegistry.registerMob("starfangeddestroyertail", StarfangedDestroyerTail.class, false, true);
        MobRegistry.registerMob("enragedstarfangeddestroyer", EnragedStarfangedDestroyerHead.class, true, true);
        MobRegistry.registerMob("enragedstarfangeddestroyerbody", EnragedStarfangedDestroyerBody.class, false, true);
        MobRegistry.registerMob("enragedstarfangeddestroyertail", EnragedStarfangedDestroyerTail.class, false, true);

        // Projectiles
        ProjectileRegistry.registerProjectile("shootingstarprojectile", ShootingStarProjectile.class, "shootingstarprojectile", "shootingstarprojectile_shadow");

        // Levels
        LevelRegistry.registerLevel("aethersurface", AetherSurfaceLevel.class);
        LevelRegistry.registerLevel("aethercave", AetherCaveLevel.class);
        LevelRegistry.registerLevel("aethersky", AetherSkyLevel.class);
        LevelRegistry.registerLevel("aetherspace", AetherSpaceLevel.class);

        // Music
        AetherMusic = MusicRegistry.registerMusic("aethermusic", "music/aethersurfacemusic",
                new StaticMessage("Aether Surface"),
                new Color(47, 105, 12),
                new Color(47, 105, 12),
                new LocalMessage("itemtooltip", "fromdeathriteost"));

        StarfangedMusic = MusicRegistry.registerMusic("starfangeddestroyerbossmusic", "music/starfangeddestroyerbossmusic",
                new StaticMessage("Starfanged Destroyer"),
                new Color(47, 105, 12),
                new Color(47, 105, 12),
                new LocalMessage("itemtooltip", "fromdeathriteost"));
    }

    public void initResources() {
        // Sometimes your textures will have a black or other outline unintended under rotation or scaling
        // This is caused by alpha blending between transparent pixels and the edge
        // To fix this, run the preAntialiasTextures gradle task
        // It will process your textures and save them again with a fixed alpha edge color

        AetherSpirit.texture = GameTexture.fromFile("mobs/aetherspirit");
        StarfangedDestroyerTexture = GameTexture.fromFile("mobs/starfangeddestroyer");
        AetherCavelingTexture = new HumanTexture(GameTexture.fromFile("mobs/aethercaveling"), GameTexture.fromFile("mobs/aethercavelingarms_front"), GameTexture.fromFile("mobs/aethercavelingarms_back"));
    }

    public void postInit() {
        // Install Byte Buddy agent (required for runtime class modification)
        // Thanks to A Wild Ferren/ferrenfx on discord for getting the workstation upgrade to work
        ByteBuddyAgent.install();

        try {
            new ByteBuddy()
                    .rebase(CraftingStationObject.class) // Rebase preseves the original class.
                    .visit(Advice.to(GetStationUpgradeInterceptor.class)
                            .on(ElementMatchers.named("getStationUpgrade"))) // Intercept this method in every instance of CraftingStationObject
                    .make()
                    .load(CraftingStationObject.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

            System.out.println("Successfully intercepted getStationUpgrade()!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Items
        Recipes.registerModRecipe(new Recipe(
                "skyladderdown",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("aetherbar", 10),
                        new Ingredient("ridiumbar", 5),
                        new Ingredient("skycore", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "wormholedown",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("deathrite_stardust", 20),
                        new Ingredient("aethiumbar", 10),
                        new Ingredient("darkmatter", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "thepoweroftheeclipse",
                1,
                RecipeTechRegistry.getTech("deathritesummoner"),
                new Ingredient[]{
                        new Ingredient("thepowerofthemoon", 1),
                        new Ingredient("thepowerofthesun", 1)
                }
        ));

        // Tools
        Recipes.registerModRecipe(new Recipe(
                "ridiumsword",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 10),
                        new Ingredient("anylog", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumsword",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumpickaxe",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 16),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumaxe",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 16),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumshovel",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 16),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumpickaxe",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 16),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumaxe",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 16),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumshovel",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 16),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "starryfang",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 10),
                        new Ingredient("darkmatter", 5)
                }
        ));

        // Quest Items
        Recipes.registerModRecipe(new Recipe(
                "essenceofthegods",
                1,
                RecipeTechRegistry.getTech("deathritesummoner"),
                new Ingredient[]{
                        new Ingredient("slimeessence", 50),
                        new Ingredient("bloodessence", 50),
                        new Ingredient("spideressence", 50),
                        new Ingredient("sunessence", 50),
                        new Ingredient("moonessence", 50),
                        new Ingredient("motherslimesleftoverslime", 1),
                        new Ingredient("spiderempresscrown", 1),
                        new Ingredient("thepoweroftheeclipse", 1)
                }
        ));

        // Materials
        Recipes.registerModRecipe(new Recipe(
                "ridiumbar",
                1,
                RecipeTechRegistry.FORGE,
                new Ingredient[]{
                        new Ingredient("ridiumore", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aetherbar",
                1,
                RecipeTechRegistry.FORGE,
                new Ingredient[]{
                        new Ingredient("aetherore", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumbar",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("ridiumbar", 1),
                        new Ingredient("aetherbar", 1),
                        new Ingredient("knightsheart", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "starrybar",
                1,
                RecipeTechRegistry.FORGE,
                new Ingredient[]{
                        new Ingredient("stariteore", 4),
                        new Ingredient("aethiumbar", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronbar",
                1,
                RecipeTechRegistry.FORGE,
                new Ingredient[]{
                        new Ingredient("xaeronore", 4),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronbar",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("xaeronbar", 2),
                        new Ingredient("crystalscale", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "skycore",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aetherbar", 5),
                        new Ingredient("glass", 10),
                        new Ingredient("glacialbar", 5)
                }
        ));

        // Armor
        Recipes.registerModRecipe(new Recipe(
                "ridiumhelmet",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumchestplate",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumboots",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 7)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumhelmet",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("aethiumbar", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumchestplate",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("aethiumbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumboots",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("aethiumbar", 7)
                }
        ));

        // Trinkets
        Recipes.registerModRecipe(new Recipe(
                "ridiumbladesharpener",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 2),
                        new Ingredient("bladesharpener", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aetherbladesharpener",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("aetherbar", 2),
                        new Ingredient("bladesharpener", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumbladesharpener",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("ridiumbladesharpener", 1),
                        new Ingredient("aetherbladesharpener", 1),
                        new Ingredient("knightsheart", 1),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bladesharpener",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ironbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritebladesharpener",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("aethiumbladesharpener", 1),
                        new Ingredient("starrybar", 2)
                }
        ));

        // Objects
        Recipes.registerModRecipe(new Recipe(
                "ridiumoreobject",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("aetherstone", 4),
                        new Ingredient("ridiumore", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aetheroreobject",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("aetherstone", 4),
                        new Ingredient("aetherore", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "stariteoreobject",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("spacestone", 4),
                        new Ingredient("stariteore", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronoreobject",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("spacestone", 4),
                        new Ingredient("xaeronore", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aetherrock",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("aetherstone", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spacerock",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("spacestone", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "upgradedspacerock",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("spacestone", 4)
                }
        ));

        // Workstations
        Recipes.registerModRecipe(new Recipe(
                "deathritesummoner",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("passedspirits", 20),
                        new Ingredient("ridiumbar", 15),
                        new Ingredient("aetherbar", 10)
                }
        ));

        // Boss Summons
        Recipes.registerModRecipe(new Recipe(
                "tabletofspirits",
                1,
                RecipeTechRegistry.getTech("deathritesummoner"),
                new Ingredient[]{
                        new Ingredient("ridiumbar", 10),
                        new Ingredient("passedspirits", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "starryworm",
                1,
                RecipeTechRegistry.getTech("deathritesummoner"),
                new Ingredient[]{
                        new Ingredient("deathrite_stardust", 10),
                        new Ingredient("essenceofthegods", 1)
                }
        ));

        // Vanilla Modded Boss Loot Tables
        GameEvents.addListener(MobLootTableDropsEvent.class, new GameEventListener<MobLootTableDropsEvent>() {
            @Override
            public void onEvent(MobLootTableDropsEvent event) {
                int moonmin = 20;
                int moonmax = 25;
                int sunmin = 20;
                int sunmax = 25;
                int crystalmin = 100;
                int crystalmax = 250;
                if (event.mob.getStringID().equals("fallenwizard")) {
                    event.drops.add(new InventoryItem("aethermap", 1));
                    event.drops.add(new InventoryItem("ridiumpickaxe", 1));
                }
                if (event.mob.getStringID().equals("motherslime")) {
                    event.drops.add(new InventoryItem("motherslimesleftoverslime", 1));
                }
                if (event.mob.getStringID().equals("spiderempress")) {
                    event.drops.add(new InventoryItem("spiderempresscrown", 1));
                }
                Random Sunrandom = new Random();
                int sunRandomNumber = Sunrandom.nextInt(sunmax - sunmin + 1) + sunmin;
                if (event.mob.getStringID().equals("sunlightchampion")) {
                    event.drops.add(new InventoryItem("thepowerofthesun", 1));
                    event.drops.add(new InventoryItem("sunessence", sunRandomNumber));
                }
                Random random = new Random();
                int randomNumber = random.nextInt(moonmax - moonmin + 1) + moonmin;
                if (event.mob.getStringID().equals("moonlightdancer")) {
                    event.drops.add(new InventoryItem("thepowerofthemoon", 1));
                    event.drops.add(new InventoryItem("moonessence", randomNumber));
                }
                Random crystalRandom = new Random();
                int crystalRandomNumber = crystalRandom.nextInt(crystalmax - crystalmin + 1) + crystalmin;
                if (event.mob.getStringID().equals("crystaldragon")) {
                    event.drops.add(new InventoryItem("crystalscale", crystalRandomNumber));
                }
            }
        });

        // Vanilla Modded Loot Tables
        LootTablePresets.startChest.items.addAll(new LootItemList(new LootItemInterface[]{new LootItem("knightheart", 200)}));
    }

    public static class GetStationUpgradeInterceptor {
        @Advice.OnMethodExit
        static void onExit(@Advice.This Object thisObject, @Advice.Return(readOnly = false) CraftingStationUpgrade returnValue) {
            System.out.println("Intercepted getStationUpgrade call!");


            if (thisObject instanceof FallenWorkstationObject) {

                // Modify the return value only for FallenWorkstationObject.
                returnValue = new CraftingStationUpgrade(ObjectRegistry.getObject("aethiumworkstation"),
                        new Ingredient[]{new Ingredient("aethiumbar", 5), new Ingredient("skycore", 1)});
            }

            if (thisObject instanceof FallenAnvilObject) {

                // Modify the return value only for FallenWorkstationObject.
                returnValue = new CraftingStationUpgrade(ObjectRegistry.getObject("aethiumanvil"),
                        new Ingredient[]{new Ingredient("aethiumbar", 6), new Ingredient("skycore", 1)});
            }
        }
    }
}
