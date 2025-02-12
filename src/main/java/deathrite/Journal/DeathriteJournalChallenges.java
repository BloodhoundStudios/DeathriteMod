package deathrite.Journal;

import necesse.engine.journal.JournalChallenge;
import necesse.engine.journal.MultiJournalChallenge;
import necesse.engine.registries.JournalChallengeRegistry;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;

public class DeathriteJournalChallenges {

    public static LootTable DEATHRITE_EOTG_REWARD = new LootTable((new LootItemList(new LootItem("stardust", 10))).setCustomListName("item", "stardust"));
    public static int EOTG_REWARD_ID;
    public static int CRAFT_ESSENCE_OF_THE_GODS_ID;

    public static void registerCore() {
        CRAFT_ESSENCE_OF_THE_GODS_ID = registerChallenge("craftessenceofthegods", new CraftEssenceOfTheGodsJournalChallenge());
        EOTG_REWARD_ID = registerChallenge("messagefromthestarschallenge", (new MultiJournalChallenge(CRAFT_ESSENCE_OF_THE_GODS_ID)).setReward(DEATHRITE_EOTG_REWARD));
    }

    public static int registerChallenge(String stringID, JournalChallenge journalChallenge) {
        return JournalChallengeRegistry.registerChallenge(stringID, journalChallenge);
    }
}
