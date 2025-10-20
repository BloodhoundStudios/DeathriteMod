//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package deathrite.Containers;

import java.awt.Rectangle;
import java.util.LinkedList;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.input.Control;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.window.GameWindow;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.fairType.TypeParsers;
import necesse.gfx.fairType.parsers.TypeParser;
import necesse.gfx.forms.ContainerComponent;
import necesse.gfx.forms.Form;
import necesse.gfx.forms.components.FormButtonToggle;
import necesse.gfx.forms.components.FormContentIconToggleButton;
import necesse.gfx.forms.components.FormFairTypeLabel;
import necesse.gfx.forms.components.FormFlow;
import necesse.gfx.forms.components.FormInputSize;
import necesse.gfx.forms.components.localComponents.FormLocalLabel;
import necesse.gfx.forms.components.localComponents.FormLocalTextButton;
import necesse.gfx.forms.presets.containerComponent.mob.ShopContainerForm;
import necesse.gfx.gameFont.FontOptions;
import necesse.gfx.ui.ButtonColor;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.FireworkPlaceableItem;
import necesse.inventory.item.placeableItem.FireworkPlaceableItem.FireworkColor;
import necesse.inventory.item.placeableItem.FireworkPlaceableItem.FireworkCrackle;
import necesse.inventory.item.placeableItem.FireworkPlaceableItem.FireworksShape;

public class MysteryManContainerForm<T extends MysteryManContainer> extends ShopContainerForm<T> {
    public Form fireworkForm;
    public LinkedList<FormContentIconToggleButton> shapeButtons;
    public LinkedList<FormContentIconToggleButton> colorButtons;
    public LinkedList<FormContentIconToggleButton> crackleButtons;
    public FireworkPlaceableItem.FireworksShape selectedShape;
    public FireworkPlaceableItem.FireworkColor selectedColor;
    public FireworkPlaceableItem.FireworkCrackle selectedCrackle;
    public int costY;
    public FormFairTypeLabel costLabel;
    public FormLocalTextButton buyButton;
    public FormLocalTextButton backButton;

    public MysteryManContainerForm(Client client, T container, int width, int height, int maxExpeditionsHeight) {
        super(client, container, width, height, maxExpeditionsHeight);
        this.shapeButtons = new LinkedList();
        this.colorButtons = new LinkedList();
        this.crackleButtons = new LinkedList();
        this.selectedShape = null;
        this.selectedColor = null;
        this.selectedCrackle = null;
        this.fireworkForm = (Form)this.addComponent(new Form("firework", width, 360));
        FormFlow fireworkFlow = new FormFlow(5);
        this.fireworkForm.addComponent(new FormLocalLabel("ui", "alchemistfirework", new FontOptions(20), -1, 5, fireworkFlow.next(30)));
        int buttonWidth = 32;
        int buttonPadding = 8;
        int buttonsPerRow = width / (buttonWidth + buttonPadding);
        this.fireworkForm.addComponent((FormLocalLabel)fireworkFlow.nextY(new FormLocalLabel("ui", "fireworkshape", new FontOptions(16), -1, 5, 0, this.fireworkForm.getWidth() - 10), 4));
        int button = 0;
        FormContentIconToggleButton iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_random, new GameMessage[]{new LocalMessage("itemtooltip", "fireworkrandom")}));
        this.shapeButtons.add(iconButton);
        iconButton.setToggled(true);
        iconButton.onToggled((e) -> {
            this.shapeButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedShape == null) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedShape = null;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_sphere, new GameMessage[]{FireworksShape.Sphere.displayName}));
        this.shapeButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.shapeButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedShape == FireworksShape.Sphere) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedShape = FireworksShape.Sphere;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_splash, new GameMessage[]{FireworksShape.Splash.displayName}));
        this.shapeButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.shapeButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedShape == FireworksShape.Splash) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedShape = FireworksShape.Splash;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_disc, new GameMessage[]{FireworksShape.Disc.displayName}));
        this.shapeButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.shapeButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedShape == FireworksShape.Disc) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedShape = FireworksShape.Disc;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_star, new GameMessage[]{FireworksShape.Star.displayName}));
        this.shapeButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.shapeButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedShape == FireworksShape.Star) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedShape = FireworksShape.Star;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_heart, new GameMessage[]{FireworksShape.Heart.displayName}));
        this.shapeButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.shapeButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedShape == FireworksShape.Heart) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedShape = FireworksShape.Heart;
            this.updateCost();
        });
        ++button;
        int rows = (int)Math.ceil((double)((float)button / (float)buttonsPerRow));
        fireworkFlow.next(rows * (buttonWidth + buttonPadding));
        this.fireworkForm.addComponent((FormLocalLabel)fireworkFlow.nextY(new FormLocalLabel("ui", "fireworkcolor", new FontOptions(16), -1, 5, 0, this.fireworkForm.getWidth() - 10), 4));
        button = 0;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_random, new GameMessage[]{new LocalMessage("itemtooltip", "fireworkrandom")}));
        this.colorButtons.add(iconButton);
        iconButton.setToggled(true);
        iconButton.onToggled((e) -> {
            this.colorButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedColor == null) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedColor = null;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_confetti, new GameMessage[]{FireworkColor.Confetti.displayName}));
        this.colorButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.colorButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedColor == FireworkColor.Confetti) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedColor = FireworkColor.Confetti;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_flame, new GameMessage[]{FireworkColor.Flame.displayName}));
        this.colorButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.colorButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedColor == FireworkColor.Flame) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedColor = FireworkColor.Flame;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_red, new GameMessage[]{FireworkColor.Red.displayName}));
        this.colorButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.colorButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedColor == FireworkColor.Red) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedColor = FireworkColor.Red;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_green, new GameMessage[]{FireworkColor.Green.displayName}));
        this.colorButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.colorButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedColor == FireworkColor.Green) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedColor = FireworkColor.Green;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_blue, new GameMessage[]{FireworkColor.Blue.displayName}));
        this.colorButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.colorButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedColor == FireworkColor.Blue) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedColor = FireworkColor.Blue;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_pink, new GameMessage[]{FireworkColor.Pink.displayName}));
        this.colorButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.colorButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedColor == FireworkColor.Pink) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedColor = FireworkColor.Pink;
            this.updateCost();
        });
        ++button;
        rows = (int)Math.ceil((double)((float)button / (float)buttonsPerRow));
        fireworkFlow.next(rows * (buttonWidth + buttonPadding));
        this.fireworkForm.addComponent((FormLocalLabel)fireworkFlow.nextY(new FormLocalLabel("ui", "fireworkcrackle", new FontOptions(16), -1, 5, 0, this.fireworkForm.getWidth() - 10), 4));
        button = 0;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_random, new GameMessage[]{new LocalMessage("itemtooltip", "fireworkrandom")}));
        this.crackleButtons.add(iconButton);
        iconButton.setToggled(true);
        iconButton.onToggled((e) -> {
            this.crackleButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedCrackle == null) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedCrackle = null;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_crackle, new GameMessage[]{FireworkCrackle.Crackle.displayName}));
        this.crackleButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.crackleButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedCrackle == FireworkCrackle.Crackle) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedCrackle = FireworkCrackle.Crackle;
            this.updateCost();
        });
        ++button;
        iconButton = (FormContentIconToggleButton)this.fireworkForm.addComponent(new FormContentIconToggleButton(button % buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, fireworkFlow.next() + button / buttonsPerRow * (buttonWidth + buttonPadding) + buttonPadding / 2, FormInputSize.SIZE_32, ButtonColor.BASE, this.getInterfaceStyle().firework_nocrackle, new GameMessage[]{FireworkCrackle.NoCrackle.displayName}));
        this.crackleButtons.add(iconButton);
        iconButton.onToggled((e) -> {
            this.crackleButtons.stream().filter((b) -> b != e.from).forEach((b) -> b.setToggled(false));
            if (this.selectedCrackle == FireworkCrackle.NoCrackle) {
                ((FormButtonToggle)e.from).setToggled(true);
            }

            this.selectedCrackle = FireworkCrackle.NoCrackle;
            this.updateCost();
        });
        ++button;
        rows = (int)Math.ceil((double)((float)button / (float)buttonsPerRow));
        fireworkFlow.next(rows * (buttonWidth + buttonPadding));
        this.costY = fireworkFlow.next() + 5;
        this.costLabel = (FormFairTypeLabel)this.fireworkForm.addComponent(new FormFairTypeLabel("", 5, 0), -100);
        fireworkFlow.next(30);
        int halfFireworkWidth = this.fireworkForm.getWidth() / 2;
        this.buyButton = (FormLocalTextButton)this.fireworkForm.addComponent(new FormLocalTextButton("ui", "buybutton", 4, 0, halfFireworkWidth - 6));
        this.buyButton.onClicked((e) -> {
            int amount = 1;
            if (Control.CRAFT_10.isDown()) {
                amount = 10;
            } else if (Control.CRAFT_ALL.isDown()) {
                amount = 65535;
            }

            Packet content = new Packet();
            PacketWriter writer = new PacketWriter(content);
            this.getFireworkData().writePacket(writer);
            writer.putNextShortUnsigned(amount);
            container.buyFireworkButton.runAndSend(content);
            e.preventDefault();
        });
        this.buyButton.acceptMouseRepeatEvents = true;
        this.backButton = (FormLocalTextButton)this.fireworkForm.addComponent(new FormLocalTextButton("ui", "backbutton", halfFireworkWidth + 2, 0, halfFireworkWidth - 6));
        this.backButton.onClicked((e) -> this.makeCurrent(this.dialogueForm));
        this.fireworkForm.setHeight(fireworkFlow.next());
        this.updateCost();
    }

    public MysteryManContainerForm(Client client, T container) {
        this(client, container, 408, defaultHeight, defaultHeight);
    }

    protected void addShopDialogueOptions() {
        super.addShopDialogueOptions();
        boolean valid = false;
        if (((MysteryManContainer)this.container).sellingItems != null && !((MysteryManContainer)this.container).sellingItems.isEmpty()) {
            valid = true;
        } else if (((MysteryManContainer)this.container).buyingItems != null && !((MysteryManContainer)this.container).buyingItems.isEmpty()) {
            valid = true;
        }

        if (valid) {
            this.dialogueForm.addDialogueOption(new LocalMessage("ui", "alchemstwantfirework"), () -> this.makeCurrent(this.fireworkForm));
        }

    }

    public void updateCost() {
        GNDItemMap fireworkData = this.getFireworkData();
        int cost = ((MysteryManContainer)this.container).getFireworksCost(fireworkData);
        InventoryItem item = new InventoryItem("fireworkrocket");
        item.setGndData(fireworkData);
        FormFlow flow = new FormFlow(this.costY);
        this.costLabel.setText(new LocalMessage("ui", "alchemistfireworkcost", new Object[]{"cost", cost, "firework", TypeParsers.getItemParseString(item)}));
        FontOptions fontOptions = (new FontOptions(16)).color(this.getInterfaceStyle().activeTextColor);
        this.costLabel.setFontOptions(fontOptions);
        this.costLabel.setParsers(new TypeParser[]{TypeParsers.GAME_COLOR, TypeParsers.InputIcon(fontOptions), TypeParsers.ItemIcon(16)});
        flow.nextY(this.costLabel, 5);
        int buttonY = flow.next(40);
        this.buyButton.setY(buttonY);
        this.backButton.setY(buttonY);
        this.fireworkForm.setHeight(flow.next());
        this.updateCanBuy();
        ContainerComponent.setPosFocus(this.fireworkForm);
    }

    public void updateCanBuy() {
        this.buyButton.setActive(((MysteryManContainer)this.container).canBuyFirework(this.getFireworkData()));
    }

    public GNDItemMap getFireworkData() {
        GNDItemMap gndData = new GNDItemMap();
        (new FireworkPlaceableItem.FireworkItemCreator()).shape(this.selectedShape).color(this.selectedColor).crackle(this.selectedCrackle).applyToData(gndData);
        return gndData;
    }

    public void draw(TickManager tickManager, PlayerMob perspective, Rectangle renderBox) {
        if (this.isCurrent(this.fireworkForm)) {
            this.updateCanBuy();
        }

        super.draw(tickManager, perspective, renderBox);
    }

    public void onWindowResized(GameWindow window) {
        super.onWindowResized(window);
        ContainerComponent.setPosFocus(this.fireworkForm);
    }
}
