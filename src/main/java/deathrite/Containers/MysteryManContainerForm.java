//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Containers;

import java.awt.Rectangle;
import java.util.LinkedList;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.window.GameWindow;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.forms.ContainerComponent;
import necesse.gfx.forms.Form;
import necesse.gfx.forms.components.FormContentIconToggleButton;
import necesse.gfx.forms.components.FormFairTypeLabel;
import necesse.gfx.forms.components.FormFlow;
import necesse.gfx.forms.components.localComponents.FormLocalLabel;
import necesse.gfx.forms.components.localComponents.FormLocalTextButton;
import necesse.gfx.forms.presets.containerComponent.mob.ShopContainerForm;
import necesse.gfx.gameFont.FontOptions;

public class MysteryManContainerForm<T extends MysteryManContainer> extends ShopContainerForm<T> {
    public Form mysterymanForm;
    public int costY;
    public FormFairTypeLabel costLabel;
    public FormLocalTextButton buyButton;
    public FormLocalTextButton backButton;

    public int additionalhalfWidth = 50;

    public MysteryManContainerForm(Client client, T container, int width, int height, int maxExpeditionsHeight) {
        super(client, container, width, height, maxExpeditionsHeight);
        this.mysterymanForm = (Form)this.addComponent(new Form("giveitem", width, 360));
        FormFlow giveFlow = new FormFlow(5);
        this.mysterymanForm.addComponent(new FormLocalLabel("ui", "mysterymanstardust", new FontOptions(20), -1, 5, giveFlow.next(30)));
        this.costY = giveFlow.next() + 5;
        this.costLabel = (FormFairTypeLabel)this.mysterymanForm.addComponent(new FormFairTypeLabel("", 5, 0), -100);
        giveFlow.next(30);
        int halfWidth = this.mysterymanForm.getWidth() / 2;
        this.buyButton = (FormLocalTextButton)this.mysterymanForm.addComponent(new FormLocalTextButton("ui", "givebutton", 4, 0, halfWidth + additionalhalfWidth - 6));
        this.buyButton.onClicked((e) -> {
            int amount = 10;
            Packet content = new Packet();
            PacketWriter writer = new PacketWriter(content);
            writer.putNextShortUnsigned(amount);
            container.giveButton.runAndSend(content);
            e.preventDefault();
        });
        this.buyButton.acceptMouseRepeatEvents = true;
        this.backButton = (FormLocalTextButton)this.mysterymanForm.addComponent(new FormLocalTextButton("ui", "backbutton", halfWidth + 2 + additionalhalfWidth, 0, halfWidth - additionalhalfWidth - 6));
        this.backButton.onClicked((e) -> this.makeCurrent(this.dialogueForm));
        this.mysterymanForm.setHeight(giveFlow.next());
    }

    public MysteryManContainerForm(Client client, T container) {
        this(client, container, 408, defaultHeight, defaultHeight);
    }

    protected void addShopDialogueOptions() {
        super.addShopDialogueOptions();
        this.dialogueForm.addDialogueOption(new LocalMessage("ui", "mysterymanwantitem"), () -> this.makeCurrent(this.mysterymanForm));
    }

    public void draw(TickManager tickManager, PlayerMob perspective, Rectangle renderBox) {
        super.draw(tickManager, perspective, renderBox);
    }

    public void onWindowResized(GameWindow window) {
        super.onWindowResized(window);
        ContainerComponent.setPosFocus(this.mysterymanForm);
    }
}
