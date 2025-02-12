package deathrite.Journal;

import necesse.engine.journal.CraftItemJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.inventory.recipe.Recipe;

public class CraftEssenceOfTheGodsJournalChallenge extends CraftItemJournalChallenge {
    public CraftEssenceOfTheGodsJournalChallenge() {
        super("essenceofthegods");
    }

    public void onCraftedRecipe(ServerClient serverClient, Recipe recipe, int amount) {
        super.onCraftedRecipe(serverClient, recipe, 1);
    }
}
