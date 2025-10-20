package deathrite;

import deathrite.Armor.Aethium.AethiumBootsItem;
import deathrite.Armor.Aethium.AethiumChestplateItem;
import deathrite.Armor.Aethium.AethiumHelmetItem;
import deathrite.Armor.CrystalizedXaeron.CrystalizedXaeronBootsItem;
import deathrite.Armor.CrystalizedXaeron.CrystalizedXaeronChestplateItem;
import deathrite.Armor.CrystalizedXaeron.CrystalizedXaeronHelmetItem;
import deathrite.Armor.Ridium.RidiumBootsItem;
import deathrite.Armor.Ridium.RidiumChestplateItem;
import deathrite.Armor.Ridium.RidiumHelmetItem;
import deathrite.Armor.Starite.StariteBootsItem;
import deathrite.Armor.Starite.StariteChestplateItem;
import deathrite.Armor.Starite.StariteHelmetItem;
import deathrite.Armor.Xaeron.XaeronBootsItem;
import deathrite.Armor.Xaeron.XaeronChestplateItem;
import deathrite.Armor.Xaeron.XaeronHelmetItem;
import deathrite.Biomes.Aether.*;
import deathrite.Biomes.Sky.SkyBiome;
import deathrite.Buffs.Trinkets.Arrowheads.*;
import deathrite.Buffs.Trinkets.BladeSharpeners.*;
import deathrite.Buffs.Trinkets.Crystals.*;
import deathrite.Buffs.Trinkets.Spellbooks.*;
import deathrite.Buffs.Trinkets.StarfrostBuff;
import deathrite.Buffs.Trinkets.TheTrinketofEverything;
import deathrite.Containers.MysteryManContainer;
import deathrite.Containers.MysteryManContainerForm;
import deathrite.Events.WeaponEvents.AetherStaffEvent;
import deathrite.Items.Arrows.AetherArrow;
import deathrite.Items.Arrows.CrystalizedXaeronArrow;
import deathrite.Items.Arrows.RidiumArrow;
import deathrite.Items.Arrows.StariteArrow;
import deathrite.Items.BossDrops.*;
import deathrite.Items.BossSummons.StarryWormSpawnItem;
import deathrite.Items.Materials.Aether.*;
import deathrite.Items.Materials.Aethium.AethiumBar;
import deathrite.Items.Materials.VoidShards.*;
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
import deathrite.Items.Weapons.Aether.AetherStaff;
import deathrite.Items.Weapons.Aethium.*;
import deathrite.Items.Weapons.Dev.DevSword;
import deathrite.Items.Weapons.Ridium.*;
import deathrite.Items.Weapons.Starite.*;
import deathrite.Items.Weapons.Xaeron.*;
import deathrite.Journal.DeathriteJournalChallenges;
import deathrite.Mobs.Bosses.StarfangedDestroyer.*;
import deathrite.Mobs.Friendly.Caveling.AetherCaveling;
import deathrite.Mobs.Friendly.NPCs.MysteryManHumanMob;
import deathrite.Mobs.Friendly.NPCs.MysteryManSettler;
import deathrite.Mobs.Hostile.AetherSpirit;
import deathrite.Objects.Aether.AetherRock;
import deathrite.Objects.Space.SpaceRock;
import deathrite.Objects.Space.UpgradedSpaceRock;
import deathrite.Objects.Workstations.AethiumAnvilObject;
import deathrite.Objects.Workstations.AethiumWorkstationObject;
import deathrite.Objects.Workstations.DeathriteSummonerObject;
import deathrite.Overrides.FallenAnvilUpgrade;
import deathrite.Projectiles.*;
import deathrite.Projectiles.Arrows.AetherArrowProjectile;
import deathrite.Projectiles.Arrows.CrystalizedXaeronArrowProjectile;
import deathrite.Projectiles.Arrows.RidiumArrowProjectile;
import deathrite.Projectiles.Arrows.StariteArrowProjectile;
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
import necesse.entity.mobs.friendly.human.humanShop.ShopContainerData;
import necesse.entity.projectile.XaeronDaggerProjectile;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.shader.ShaderLoader;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item.Rarity;
import necesse.inventory.item.matItem.MatItem;
import necesse.inventory.item.miscItem.VinylItem;
import necesse.inventory.item.toolItem.axeToolItem.CustomAxeToolItem;
import necesse.inventory.item.toolItem.pickaxeToolItem.CustomPickaxeToolItem;
import necesse.inventory.item.toolItem.shovelToolItem.CustomShovelToolItem;
import necesse.inventory.item.trinketItem.SimpleTrinketItem;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.lootTable.presets.ToolsLootTable;
import necesse.inventory.lootTable.presets.TrinketsLootTable;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.*;
import necesse.level.gameObject.container.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;

import java.awt.*;
import java.util.Random;

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
    public static GameTexture aetherStaffPillars;
    public static HumanTexture AetherCavelingTexture;
    public static int TestShader;

    public static int AETHER_SAND;
    public static int SPACE_FLOOR;

    public static int AETHER_ROCK;
    public static int SPACE_ROCK;
    public static int UPGRADED_SPACE_ROCK;
    public static int SKY_CLOUD;

    public static int MYSTERYMAN_CONTAINER;

    public void init() {

        // Containers
        MYSTERYMAN_CONTAINER = ContainerRegistry.registerMobContainer((client, uniqueSeed, mob, content) -> new MysteryManContainerForm<>(client, new MysteryManContainer(client.getClient(), uniqueSeed, (MysteryManHumanMob) mob, content, (ShopContainerData)null)), (client, uniqueSeed, mob, content, serverObject) -> new MysteryManContainer(client, uniqueSeed, (MysteryManHumanMob) mob, content, (ShopContainerData)serverObject));

        // Music
        AetherMusic = MusicRegistry.registerMusic("aethermusic", "music/aethersurfacemusic",
                new StaticMessage("Aether"),
                new Color(41, 139, 191),
                new Color(45, 43, 161),
                new LocalMessage("itemtooltip", "fromdeathriteost"));

        StarfangedMusic = MusicRegistry.registerMusic("starfangeddestroyerbossmusic", "music/starfangeddestroyerbossmusic",
                new StaticMessage("Starfanged Destroyer"),
                new Color(44, 8, 74),
                new Color(218, 206, 37),
                new LocalMessage("itemtooltip", "fromdeathriteost"));

        // Journal Challenges
        DeathriteJournalChallenges.registerCore();

        // Journal Surface
        JournalEntry forestSurfaceJournal = JournalRegistry.getJournalEntry("forestsurface");
        forestSurfaceJournal.addEntryChallenges(DeathriteJournalChallenges.EOTG_REWARD_ID);

        // Tiles
        SKY_CLOUD = TileRegistry.registerTile("skycloudfloor", new SkyCloudTile(), 0, true);
        AETHER_SAND = TileRegistry.registerTile("aethersandfloor", new AetherSandTile(), 0, true);
        TileRegistry.registerTile("aetherstonefloor", new AetherStoneTile(), 0, true);
        SPACE_FLOOR = TileRegistry.registerTile("spacestonefloor", new SpaceStoneTile(), 0, true);

        // Rock Objects
        RockObject aetherRock;
        AETHER_ROCK = ObjectRegistry.registerObject("aetherrock", aetherRock = new AetherRock(), 50, true);
        SPACE_ROCK = ObjectRegistry.registerObject("spacerock", new SpaceRock(), 100, true);
        UPGRADED_SPACE_ROCK = ObjectRegistry.registerObject("upgradedspacerock", new UpgradedSpaceRock(), 100, true);

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

        // Workstations
        DeathriteSummonerObject.registerWorkstation();
        AethiumWorkstationObject.registerWorkstation();
//        ObjectRegistry.replaceObject("fallenworkstation", new FallenWorkstationUpgrade(), 200,true);
        ObjectRegistry.replaceObject("fallenanvil", new FallenAnvilUpgrade(), 140, true);
        ObjectRegistry.registerObject("aethiumanvil", new AethiumAnvilObject(), 216, true);

        // Biomes
        BiomeRegistry.registerBiome("sky", new SkyBiome().setGenerationWeight(1.25F), true);
        BiomeRegistry.registerBiome("aether", new AetherBiome().setGenerationWeight(1.25F), true);

        // Buffs
        BuffRegistry.registerBuff("starfrost", new StarfrostBuff());
        BuffRegistry.registerBuff("thetrinketofeverything", new TheTrinketofEverything());

        BuffRegistry.registerBuff("bladesharpener", new BladeSharpener());
        BuffRegistry.registerBuff("ridiumbladesharpener", new RidiumBladeSharpener());
        BuffRegistry.registerBuff("aetherbladesharpener", new AetherBladeSharpener());
        BuffRegistry.registerBuff("aethiumbladesharpener", new AethiumBladeSharpener());
        BuffRegistry.registerBuff("staritebladesharpener", new StariteBladeSharpener());
        BuffRegistry.registerBuff("xaeronbladesharpener", new XaeronBladeSharpener());
        BuffRegistry.registerBuff("crystalizedxaeronbladesharpener", new CrystalizedXaeronBladeSharpener());

        BuffRegistry.registerBuff("arrowhead", new Arrowhead());
        BuffRegistry.registerBuff("ridiumarrowheadbuff", new RidiumArrowheadBuff());
        BuffRegistry.registerBuff("aetherarrowhead", new AetherArrowhead());
        BuffRegistry.registerBuff("aethiumarrowhead", new AethiumArrowhead());
        BuffRegistry.registerBuff("staritearrowhead", new StariteArrowhead());
        BuffRegistry.registerBuff("xaeronarrowhead", new XaeronArrowhead());
        BuffRegistry.registerBuff("crystalizedxaeronarrowhead", new CrystalizedXaeronArrowhead());

        BuffRegistry.registerBuff("spellbook", new Spellbook());
        BuffRegistry.registerBuff("ridiumspellbookbuff", new RidiumSpellbook());
        BuffRegistry.registerBuff("aetherspellbook", new AetherSpellbook());
        BuffRegistry.registerBuff("aethiumspellbook", new AethiumSpellbook());
        BuffRegistry.registerBuff("staritespellbook", new StariteSpellbook());
        BuffRegistry.registerBuff("xaeronspellbook", new XaeronSpellbook());
        BuffRegistry.registerBuff("crystalizedxaeronspellbook", new CrystalizedXaeronSpellbook());

        BuffRegistry.registerBuff("staffcrystal", new StaffCrystal());
        BuffRegistry.registerBuff("aethercrystal", new AetherCrystal());
        BuffRegistry.registerBuff("aethiumcrystal", new AethiumCrystal());
        BuffRegistry.registerBuff("staritecrystal", new StariteCrystal());
        BuffRegistry.registerBuff("crystalizedxaeroncrystal", new CrystalizedXaeronCrystal());

        // Recipe Techs
        RecipeTechRegistry.registerTech("deathritesummoner", "deathritesummoner");
        RecipeTechRegistry.registerTech("aethiumworkstation", "aethiumworkstation");
        RecipeTechRegistry.registerTech("aethiumanvil", "aethiumanvil");

        // Ores
        ItemRegistry.registerItem("ridiumore", new RidiumOre(), 2, true);
        ItemRegistry.registerItem("aetherore", new AetherOre(), 2, true);
        ItemRegistry.registerItem("stariteore", new StariteOre(), 4, true);
        ItemRegistry.registerItem("xaeronore", new XaeronOre(), 6, true);

        // Shards
        ItemRegistry.registerItem("aethervoidshard", new AetherVoidShard(), 58, true);
        ItemRegistry.registerItem("aethiumvoidshard", new AethiumVoidShard(), 298, true);
        ItemRegistry.registerItem("staritevoidshard", new StariteVoidShard(), 1542, true);
        ItemRegistry.registerItem("crystalizedvoidshard", new CrystalizedVoidShard(), 7960, true);
        ItemRegistry.registerItem("everythingshard", new EverythingShard(), 159200, true);

        // Bars
        ItemRegistry.registerItem("ridiumbar", new RidiumBar(), 8, true);
        ItemRegistry.registerItem("aetherbar", new AetherBar(), 8, true);
        ItemRegistry.registerItem("aethiumbar", new AethiumBar(), 36, true);
        ItemRegistry.registerItem("starrybar", new StarryBar(), 52, true);
        ItemRegistry.registerItem("xaeronbar", new XaeronBar(), 24, true);
        ItemRegistry.registerItem("crystalizedxaeronbar", new CrystalizedXaeronBar(), 548, true);

        // Arrows
        ItemRegistry.registerItem("ridiumarrow", new RidiumArrow(), 8, true);
        ItemRegistry.registerItem("aetherarrow", new AetherArrow(), 8, true);
        ItemRegistry.registerItem("staritearrow", new StariteArrow(), 52, true);
        ItemRegistry.registerItem("crystalizedxaeronarrow", new CrystalizedXaeronArrow(), 548, true);

        // Weapons
        //Melee
        ItemRegistry.registerItem("devsword", new DevSword(), 9999, false);
        ItemRegistry.registerItem("ridiumsword", new RidiumSword(), 200, true);
        ItemRegistry.registerItem("aethiumsword", new AethiumSword(), 360, true);
        ItemRegistry.registerItem("starryfang", new StarryFang(), 770, true);
        ItemRegistry.registerItem("xaeronsword", new XaeronSword(), 240, true);
        ItemRegistry.registerItem("crystalizedxaeronsword", new CrystalizedXaeronSword(), 5480, true);
        ItemRegistry.registerItem("xaerondagger", new XaeronDagger(), 340, true);
        ItemRegistry.registerItem("ridiumglaive", new RidiumGlaive(), 128, true);
        ItemRegistry.registerItem("stariteglaive", new StariteGlaive(), 832, true);
        ItemRegistry.registerItem("xaeronglaive", new XaeronGlaive(), 384, true);
        ItemRegistry.registerItem("ridiumgreatsword", new RidiumGreatsword(), 160, true);
        ItemRegistry.registerItem("aethiumgreatsword", new AethiumGreatsword(), 720, true);
        ItemRegistry.registerItem("staritegreatsword", new StariteGreatsword(), 1040, true);
        ItemRegistry.registerItem("crystalizedxaerongreatsword", new CrystalizedXaeronGreatsword(), 10960, true);
        ItemRegistry.registerItem("aethiumspear", new AethiumSpear(), 540, true);
        ItemRegistry.registerItem("xaeronspear", new XaeronSpear(), 360, true);
        ItemRegistry.registerItem("crystalizedxaeronspear", new CrystalizedXaeronSpear(), 8220, true);

        ItemRegistry.registerItem("stariteboomerang", new StariteBoomerang(), 520, true);
        ItemRegistry.registerItem("crystalizedxaeronboomerang", new CrystalizedXaeronBoomerang(), 5480, true);

        //Ranged
        ItemRegistry.registerItem("ridiumbow", new RidiumBow(), 128, true);
        ItemRegistry.registerItem("aethiumbow", new AethiumBow(), 576, true);
        ItemRegistry.registerItem("staritebow", new StariteBow(), 832, true);

        //Magic
        ItemRegistry.registerItem("ridiumstaff", new RidiumStaff(), 410, true);
        ItemRegistry.registerItem("aetherstaff", new AetherStaff(), 410, true);
        ItemRegistry.registerItem("aethiumstaff", new AethiumStaff(), 2030, true);
        ItemRegistry.registerItem("staritestaff", new StariteStaff(), 8490, true);

        // Tools
        ItemRegistry.registerItem("ridiumpickaxe", new CustomPickaxeToolItem(400, 200, 11, 30, 50, 55, 1200, ToolsLootTable.tools), 128, true);
        ItemRegistry.registerItem("ridiumaxe", new CustomAxeToolItem(400, 200, 11, 30, 50, 55, 1200, ToolsLootTable.tools), 128, true);
        ItemRegistry.registerItem("ridiumshovel", new CustomShovelToolItem(400, 200, 11, 30, 50, 55, 1200, Rarity.RARE), 128, true);
        ItemRegistry.registerItem("aethiumpickaxe", new CustomPickaxeToolItem(450, 220, 12, 35, 55, 55, 1400, ToolsLootTable.tools), 576, true);
        ItemRegistry.registerItem("aethiumaxe", new CustomAxeToolItem(450, 220, 12, 35, 55, 55, 1400, ToolsLootTable.tools), 576, true);
        ItemRegistry.registerItem("aethiumshovel", new CustomShovelToolItem(450, 220, 12, 35, 55, 55, 1400, Rarity.EPIC), 576, true);
        ItemRegistry.registerItem("starrypickaxe", new CustomPickaxeToolItem(500, 250, 13, 40, 60, 55, 1600, ToolsLootTable.tools), 832, true);
        ItemRegistry.registerItem("starryaxe", new CustomAxeToolItem(500, 250, 13, 40, 60, 55, 1600, ToolsLootTable.tools), 832, true);
        ItemRegistry.registerItem("starryshovel", new CustomShovelToolItem(500, 250, 13, 40, 60, 55, 1600, Rarity.LEGENDARY), 832, true);
        ItemRegistry.registerItem("xaeronpickaxe", new CustomPickaxeToolItem(550, 300, 14, 45, 65, 60, 1800, ToolsLootTable.tools), 384, true);
        ItemRegistry.registerItem("xaeronaxe", new CustomAxeToolItem(550, 300, 14, 45, 65, 60, 1800, ToolsLootTable.tools), 384, true);
        ItemRegistry.registerItem("xaeronshovel", new CustomShovelToolItem(550, 300, 14, 45, 65, 60, 1800, Rarity.LEGENDARY), 384, true);

        // Items
        ItemRegistry.registerItem("aetherstone", new AetherStone(), 1.5F, true);
        ItemRegistry.registerItem("spacestone", new SpaceStone(), 2, true);
        ItemRegistry.registerItem("passedspirits", new PassedSpirits(), 2.5F, true);
        ItemRegistry.registerItem("moonessence", new MoonEssence(), 30, true);
        ItemRegistry.registerItem("sunessence", new SunEssence(), 30, true);
        ItemRegistry.registerItem("essenceofthegods", new EssenceOfTheGods(), 0, true);
        ItemRegistry.registerItem("deathrite_stardust", new Stardust(), 10, true);
        ItemRegistry.registerItem("skycore", new SkyCore(), 25, true);

        // Logs
        ItemRegistry.registerItem("aetherlog", (new MatItem(500, new String[]{"anylog"})).setItemCategory(new String[]{"materials", "logs"}), 2.0F, true);

        // Trinkets
        ItemRegistry.registerItem("thetrinketofeverything", new SimpleTrinketItem(Rarity.LEGENDARY, "thetrinketofeverything", 801268, TrinketsLootTable.trinkets).addDisables("bladesharpener", "ridiumbladesharpener", "aetherbladesharpener", "aethiumbladesharpener", "staritebladesharpener", "xaeronbladesharpener", "crystalizedxaeronbladesharpener", "arrowhead", "ridiumarrowhead", "aetherarrowhead", "aethiumarrowhead", "staritearrowhead", "xaeronarrowhead", "crystalizedxaeronarrowhead", "spellbook", "ridiumspellbook", "aetherspellbook", "aethiumspellbook", "staritespellbook", "xaeronspellbook", "crystalizedxaeronspellbook", "staffcrystal", "aethercrystal", "aethiumcrystal", "staritecrystal", "crystalizedxaeroncrystal"), 5268, true);

        ItemRegistry.registerItem("bladesharpener", new SimpleTrinketItem(Rarity.COMMON, "bladesharpener", 5, TrinketsLootTable.trinkets), 11, true);
        ItemRegistry.registerItem("ridiumbladesharpener", new SimpleTrinketItem(Rarity.EPIC, "ridiumbladesharpener", 10, TrinketsLootTable.trinkets).addDisables("bladesharpener", "aetherbladesharpener"), 27, true);
        ItemRegistry.registerItem("aetherbladesharpener", new SimpleTrinketItem(Rarity.EPIC, "aetherbladesharpener", 10, TrinketsLootTable.trinkets).addDisables("bladesharpener", "ridiumbladesharpener"), 27, true);
        ItemRegistry.registerItem("aethiumbladesharpener", new SimpleTrinketItem(Rarity.EPIC, "aethiumbladesharpener", 10, TrinketsLootTable.trinkets).addDisables("bladesharpener", "ridiumbladesharpener", "aetherbladesharpener"), 69, true);
        ItemRegistry.registerItem("staritebladesharpener", new SimpleTrinketItem(Rarity.LEGENDARY, "staritebladesharpener", 10, TrinketsLootTable.trinkets).addDisables("bladesharpener", "ridiumbladesharpener", "aetherbladesharpener", "aethiumbladesharpener"), 173, true);
        ItemRegistry.registerItem("xaeronbladesharpener", new SimpleTrinketItem(Rarity.LEGENDARY, "xaeronbladesharpener", 10, TrinketsLootTable.trinkets).addDisables("bladesharpener", "ridiumbladesharpener", "aetherbladesharpener", "aethiumbladesharpener", "staritebladesharpener"), 221, true);
        ItemRegistry.registerItem("crystalizedxaeronbladesharpener", new SimpleTrinketItem(Rarity.LEGENDARY, "crystalizedxaeronbladesharpener", 10, TrinketsLootTable.trinkets).addDisables("bladesharpener", "ridiumbladesharpener", "aetherbladesharpener", "aethiumbladesharpener", "staritebladesharpener", "xaeronbladesharpener"), 1317, true);

        ItemRegistry.registerItem("arrowhead", new SimpleTrinketItem(Rarity.COMMON, "arrowhead", 5, TrinketsLootTable.trinkets), 11, true);
        ItemRegistry.registerItem("ridiumarrowhead", new SimpleTrinketItem(Rarity.EPIC, "ridiumarrowheadbuff", 10, TrinketsLootTable.trinkets).addDisables("arrowhead", "aetherarrowhead"),5, true);
        ItemRegistry.registerItem("aetherarrowhead", new SimpleTrinketItem(Rarity.EPIC, "aetherarrowhead", 10, TrinketsLootTable.trinkets).addDisables("arrowhead", "ridiumarrowhead"), 27, true);
        ItemRegistry.registerItem("aethiumarrowhead", new SimpleTrinketItem(Rarity.EPIC, "aethiumarrowhead", 10, TrinketsLootTable.trinkets).addDisables("arrowhead", "ridiumarrowhead", "aetherarrowhead"), 69, true);
        ItemRegistry.registerItem("staritearrowhead", new SimpleTrinketItem(Rarity.LEGENDARY, "staritearrowhead", 10, TrinketsLootTable.trinkets).addDisables("arrowhead", "ridiumarrowhead", "aetherarrowhead", "aethiumarrowhead"), 173, true);
        ItemRegistry.registerItem("xaeronarrowhead", new SimpleTrinketItem(Rarity.LEGENDARY, "xaeronarrowhead", 10, TrinketsLootTable.trinkets).addDisables("arrowhead", "ridiumarrowhead", "aetherarrowhead", "aethiumarrowhead", "staritearrowhead"), 221, true);
        ItemRegistry.registerItem("crystalizedxaeronarrowhead", new SimpleTrinketItem(Rarity.LEGENDARY, "crystalizedxaeronarrowhead", 10, TrinketsLootTable.trinkets).addDisables("arrowhead", "ridiumarrowhead", "aetherarrowhead", "aethiumarrowhead", "staritearrowhead", "xaeronarrowhead"), 1317, true);

        ItemRegistry.registerItem("spellbook", new SimpleTrinketItem(Rarity.COMMON, "spellbook", 5, TrinketsLootTable.trinkets), 11, true);
        ItemRegistry.registerItem("ridiumspellbook", new SimpleTrinketItem(Rarity.EPIC, "ridiumspellbookbuff", 10, TrinketsLootTable.trinkets).addDisables("spellbook", "aetherspellbook"),5, true);
        ItemRegistry.registerItem("aetherspellbook", new SimpleTrinketItem(Rarity.EPIC, "aetherspellbook", 10, TrinketsLootTable.trinkets).addDisables("spellbook", "ridiumspellbook"), 27, true);
        ItemRegistry.registerItem("aethiumspellbook", new SimpleTrinketItem(Rarity.EPIC, "aethiumspellbook", 10, TrinketsLootTable.trinkets).addDisables("spellbook", "ridiumspellbook", "aetherspellbook"), 69, true);
        ItemRegistry.registerItem("staritespellbook", new SimpleTrinketItem(Rarity.LEGENDARY, "staritespellbook", 10, TrinketsLootTable.trinkets).addDisables("spellbook", "ridiumspellbook", "aetherspellbook", "aethiumspellbook"), 173, true);
        ItemRegistry.registerItem("xaeronspellbook", new SimpleTrinketItem(Rarity.LEGENDARY, "xaeronspellbook", 10, TrinketsLootTable.trinkets).addDisables("spellbook", "ridiumspellbook", "aetherspellbook", "aethiumspellbook", "staritespellbook"), 221, true);
        ItemRegistry.registerItem("crystalizedxaeronspellbook", new SimpleTrinketItem(Rarity.LEGENDARY, "crystalizedxaeronspellbook", 10, TrinketsLootTable.trinkets).addDisables("spellbook", "ridiumspellbook", "aetherspellbook", "aethiumspellbook", "staritespellbook", "xaeronspellbook"), 1317, true);

        ItemRegistry.registerItem("staffcrystal", new SimpleTrinketItem(Rarity.COMMON, "staffcrystal", 5, TrinketsLootTable.trinkets), 11, true);
        ItemRegistry.registerItem("aethercrystal", new SimpleTrinketItem(Rarity.EPIC, "aethercrystal", 10, TrinketsLootTable.trinkets).addDisables("staffcrystal"), 27, true);
        ItemRegistry.registerItem("aethiumcrystal", new SimpleTrinketItem(Rarity.EPIC, "aethiumcrystal", 10, TrinketsLootTable.trinkets).addDisables("staffcrystal", "aethercrystal"), 69, true);
        ItemRegistry.registerItem("staritecrystal", new SimpleTrinketItem(Rarity.LEGENDARY, "staritecrystal", 10, TrinketsLootTable.trinkets).addDisables("staffcrystal", "aethercrystal", "aethiumcrystal"), 173, true);
        ItemRegistry.registerItem("crystalizedxaeroncrystal", new SimpleTrinketItem(Rarity.LEGENDARY, "crystalizedxaeroncrystal", 10, TrinketsLootTable.trinkets).addDisables("staffcrystal", "aethercrystal", "aethiumcrystal", "staritecrystal"), 1317, true);

        // Armor
        ItemRegistry.registerItem("ridiumhelmet", new RidiumHelmetItem(), 64, true);
        ItemRegistry.registerItem("ridiumchestplate", new RidiumChestplateItem(), 80, true);
        ItemRegistry.registerItem("ridiumboots", new RidiumBootsItem(), 56, true);

        ItemRegistry.registerItem("aethiumhelmet", new AethiumHelmetItem(), 128, true);
        ItemRegistry.registerItem("aethiumchestplate", new AethiumChestplateItem(), 160, true);
        ItemRegistry.registerItem("aethiumboots", new AethiumBootsItem(), 112, true);

        ItemRegistry.registerItem("staritehelmet", new StariteHelmetItem(), 416, true);
        ItemRegistry.registerItem("staritechestplate", new StariteChestplateItem(), 520, true);
        ItemRegistry.registerItem("stariteboots", new StariteBootsItem(), 364, true);

        ItemRegistry.registerItem("xaeronhelmet", new XaeronHelmetItem(), 192, true);
        ItemRegistry.registerItem("xaeronchestplate", new XaeronChestplateItem(), 240, true);
        ItemRegistry.registerItem("xaeronboots", new XaeronBootsItem(), 168, true);

        ItemRegistry.registerItem("crystalizedxaeronhelmet", new CrystalizedXaeronHelmetItem(), 192, true);
        ItemRegistry.registerItem("crystalizedxaeronchestplate", new CrystalizedXaeronChestplateItem(), 240, true);
        ItemRegistry.registerItem("crystalizedxaeronboots", new CrystalizedXaeronBootsItem(), 168, true);

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
        ItemRegistry.registerItem("starryworm", new StarryWormSpawnItem(), 9450, true);

        // Music Vinyls
        ItemRegistry.registerItem(AetherMusic.getStringID() + "vinyl", new VinylItem(AetherMusic), 50, true, false);
        ItemRegistry.registerItem(StarfangedMusic.getStringID() + "vinyl", new VinylItem(StarfangedMusic), 50, true, false);

        // Mobs
        MobRegistry.registerMob("aetherspirit", AetherSpirit.class, true);
        MobRegistry.registerMob("aethercaveling", AetherCaveling.class, true);

        // NPCs
        MobRegistry.registerMob("mysterymanhuman", MysteryManHumanMob.class, true);
        SettlerRegistry.registerSettler("mysteryman", new MysteryManSettler());

        // Bosses
        MobRegistry.registerMob("starfangeddestroyer", StarfangedDestroyerHead.class, true, true);
        MobRegistry.registerMob("starfangeddestroyerbody", StarfangedDestroyerBody.class, false, true);
        MobRegistry.registerMob("starfangeddestroyertail", StarfangedDestroyerTail.class, false, true);
        MobRegistry.registerMob("enragedstarfangeddestroyer", EnragedStarfangedDestroyerHead.class, true, true);
        MobRegistry.registerMob("enragedstarfangeddestroyerbody", EnragedStarfangedDestroyerBody.class, false, true);
        MobRegistry.registerMob("enragedstarfangeddestroyertail", EnragedStarfangedDestroyerTail.class, false, true);

        // Projectiles
        ProjectileRegistry.registerProjectile("shootingstarprojectile", ShootingStarProjectile.class, "shootingstarprojectile", "shootingstarprojectile_shadow");
        ProjectileRegistry.registerProjectile("xaerondaggerprojectile", XaeronDaggerProjectile.class, "", "");
        ProjectileRegistry.registerProjectile("shootingstargreatswordprojectile", ShootingStarGreatswordProjectile.class, "shootingstarprojectile", "shootingstarprojectile_shadow");
        ProjectileRegistry.registerProjectile("stariteboomerang", StariteBoomerangProjectile.class, "", "");
        ProjectileRegistry.registerProjectile("crystalizedxaeronboomerang", CrystalizedXaeronBoomerangProjectile.class, "", "");
        ProjectileRegistry.registerProjectile("ridiumarrow", RidiumArrowProjectile.class, "", "");
        ProjectileRegistry.registerProjectile("aetherarrow", AetherArrowProjectile.class, "", "");
        ProjectileRegistry.registerProjectile("staritearrow", StariteArrowProjectile.class, "", "");
        ProjectileRegistry.registerProjectile("crystalizedxaeronarrow", CrystalizedXaeronArrowProjectile.class, "", "");
        ProjectileRegistry.registerProjectile("ridiumvisualbowarrow", RidiumArrowVisualProjectile.class, "", "");
        ProjectileRegistry.registerProjectile("aethervisualbowarrow", AetherArrowVisualProjectile.class, "", "");
        ProjectileRegistry.registerProjectile("shootingstarbowprojectile", ShootingStarStariteBowProjectile.class, "shootingstarprojectile", "shootingstarprojectile_shadow");
        ProjectileRegistry.registerProjectile("ridiummagicprojectile", RidiumMagicProjectile.class, "ridiummagicprojectile", "ridiummagicprojectile_shadow");
        ProjectileRegistry.registerProjectile("aethiumstaffridiumball", AethiumStaffRidiumBallProjectile.class, "aethiumstaffridiumball", "aethiumstaffridiumball_shadow");
        ProjectileRegistry.registerProjectile("aethiumstaffaethershard", AethiumStaffAetherShardProjectile.class, "aethiumstaffaethershard", "aethiumstaffaethershard_shadow");

        // Levels
//        LevelRegistry.registerLevel("aethersky", AetherSkyLevel.class);
//        LevelRegistry.registerLevel("aetherspace", AetherSpaceLevel.class);

        // Events
        LevelEventRegistry.registerEvent("aetherstaff", AetherStaffEvent.class);
    }

    public void initResources() {
        // Sometimes your textures will have a black or other outline unintended under rotation or scaling
        // This is caused by alpha blending between transparent pixels and the edge
        // To fix this, run the preAntialiasTextures gradle task
        // It will process your textures and save them again with a fixed alpha edge color

        AetherSpirit.texture = GameTexture.fromFile("mobs/aetherspirit");
        aetherStaffPillars = GameTexture.fromFile("particles/aetherstaff");
        StarfangedDestroyerTexture = GameTexture.fromFile("mobs/starfangeddestroyer");
        AetherCavelingTexture = new HumanTexture(GameTexture.fromFile("mobs/aethercaveling"), GameTexture.fromFile("mobs/aethercavelingarms_front"), GameTexture.fromFile("mobs/aethercavelingarms_back"));
        TestShader = ShaderLoader.loadFragmentShader("Shader");
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

        LootTablePresets.startChest.items.addAll(new LootItemList(new LootItem("knightsheart", 250)));

        // Items
        Recipes.registerModRecipe(new Recipe(
                "knightsheart",
                1,
                RecipeTechRegistry.getTech("deathritesummoner"),
                new Ingredient[]{
                        new Ingredient("passedspirits", 2),
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

        // Ammo
        Recipes.registerModRecipe(new Recipe(
                "ridiumarrow",
                100,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aetherarrow",
                100,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("aetherbar", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritearrow",
                100,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronarrow",
                100,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("crystalizedxaeronbar", 1)
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
                "starryfang",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 10),
                        new Ingredient("darkmatter", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronsword",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("xaeronbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaerondagger",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("carapacedagger", 1),
                        new Ingredient("xaeronbar", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumglaive",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 16)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "stariteglaive",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 16)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronglaive",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("xaeronbar", 16)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumgreatsword",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumgreatsword",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritegreatsword",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumspear",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronspear",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("xaeronbar", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronspear",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("crystalizedxaeronbar", 15)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaerongreatsword",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("crystalizedxaeronbar", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "stariteboomerang",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronboomerang",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("crystalizedxaeronbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumbow",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 16)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumbow",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 16)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritebow",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 16)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumstaff",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 15),
                        new Ingredient("aethervoidshard", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aetherstaff",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("aetherbar", 15),
                        new Ingredient("aethervoidshard", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumstaff",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 15),
                        new Ingredient("aethiumvoidshard", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritestaff",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 15),
                        new Ingredient("staritevoidshard", 5)
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
                "starrypickaxe",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 16),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "starryaxe",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 16),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "starryshovel",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 16),
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
                RecipeTechRegistry.FALLEN_ANVIL,
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
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("aetherbar", 5),
                        new Ingredient("glass", 10),
                        new Ingredient("glacialbar", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethervoidshard",
                5,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("aetherbar", 1),
                        new Ingredient("voidshard", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumvoidshard",
                5,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("aethervoidshard", 5),
                        new Ingredient("ridiumbar", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritevoidshard",
                5,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("aethiumvoidshard", 5),
                        new Ingredient("ridiumbar", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedvoidshard",
                5,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("staritevoidshard", 5),
                        new Ingredient("crystalscale", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "everythingshard",
                5,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("crystalizedvoidshard", 20)
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
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumchestplate",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumboots",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("aethiumbar", 7)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritehelmet",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritechestplate",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "stariteboots",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("starrybar", 7)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronhelmet",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("xaeronbar", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronchestplate",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("xaeronbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronboots",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("xaeronbar", 7)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronhelmet",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("crystalizedxaeronbar", 8)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronchestplate",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("crystalizedxaeronbar", 10)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronboots",
                1,
                RecipeTechRegistry.getTech("aethiumanvil"),
                new Ingredient[]{
                        new Ingredient("crystalizedxaeronbar", 7)
                }
        ));

        // Trinkets
        Recipes.registerModRecipe(new Recipe(
                "bladesharpener",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ironbar", 2)
                }
        ));

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
                "staritebladesharpener",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("aethiumbladesharpener", 1),
                        new Ingredient("starrybar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronbladesharpener",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("staritebladesharpener", 1),
                        new Ingredient("xaeronbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronbladesharpener",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("xaeronbladesharpener", 1),
                        new Ingredient("crystalizedxaeronbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "arrowhead",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ironbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumarrowhead",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 2),
                        new Ingredient("arrowhead", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aetherarrowhead",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("aetherbar", 2),
                        new Ingredient("arrowhead", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumarrowhead",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("ridiumarrowhead", 1),
                        new Ingredient("aetherarrowhead", 1),
                        new Ingredient("knightsheart", 1),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritearrowhead",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("aethiumarrowhead", 1),
                        new Ingredient("starrybar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronarrowhead",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("staritearrowhead", 1),
                        new Ingredient("xaeronbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronarrowhead",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("xaeronarrowhead", 1),
                        new Ingredient("crystalizedxaeronbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "spellbook",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("book", 1),
                        new Ingredient("voidshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "ridiumspellbook",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 2),
                        new Ingredient("spellbook", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aetherspellbook",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("aetherbar", 2),
                        new Ingredient("spellbook", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumspellbook",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("ridiumspellbook", 1),
                        new Ingredient("aetherspellbook", 1),
                        new Ingredient("knightsheart", 1),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritespellbook",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("aethiumspellbook", 1),
                        new Ingredient("starrybar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "xaeronspellbook",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("staritespellbook", 1),
                        new Ingredient("xaeronbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeronspellbook",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("xaeronspellbook", 1),
                        new Ingredient("crystalizedxaeronbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staffcrystal",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("voidshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethercrystal",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("aetherbar", 2),
                        new Ingredient("staffcrystal", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "aethiumcrystal",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("ridiumbar", 2),
                        new Ingredient("aethercrystal", 1),
                        new Ingredient("knightsheart", 1),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "staritecrystal",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("aethiumcrystal", 1),
                        new Ingredient("starrybar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedxaeroncrystal",
                1,
                RecipeTechRegistry.FALLEN_ALCHEMY,
                new Ingredient[]{
                        new Ingredient("staritecrystal", 1),
                        new Ingredient("crystalizedxaeronbar", 2)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "thetrinketofeverything",
                1,
                RecipeTechRegistry.getTech("aethiumworkstation"),
                new Ingredient[]{
                        new Ingredient("crystalizedxaeronbladesharpener", 1),
                        new Ingredient("crystalizedxaeronarrowhead", 1),
                        new Ingredient("crystalizedxaeronspellbook", 1),
                        new Ingredient("crystalizedxaeroncrystal", 1),
                        new Ingredient("everythingshard", 5)
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
