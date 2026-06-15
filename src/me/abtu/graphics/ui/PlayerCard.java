package me.abtu.graphics.ui;


import com.jogamp.newt.event.KeyEvent;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import me.abtu.util.Color;
import me.abtu.util.NewtKeyEvent;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.function.Consumer;

public class PlayerCard {
    private static final String LEFT_KEY_TAG = "left";
    private static final String RIGHT_KEY_TAG = "right";
    private static final String JUMP_KEY_TAG = "jump";
    private static final String PRIMARY_KEY_TAG = "primary";
    private static final String SECONDARY_KEY_TAG = "secondary";

    private final PlayerMenu playerMenu;

    private static final float CARD_MARGIN = GraphicsBuffer.REFERENCE_WIDTH / 64f;
    private static final float CARD_WIDTH = GraphicsBuffer.REFERENCE_WIDTH / 5f;
    private static final float CARD_HEIGHT = GraphicsBuffer.REFERENCE_HEIGHT / 2.4f;

    private final Button leftKeybindButton, rightKeybindButton, jumpKeybindButton, primaryKeybindButton, secondaryKeybindButton;
    private final Consumer<KeyEvent> keybindEventListener;
    private int left, right, jump, primary, secondary;
    private Button listeningKeybindButton;

    private final Button switchColorButton;
    private int color;

    public PlayerCard(int jump, int left, int right, int primary, int secondary, PlayerMenu playerMenu) {
        this.left = left;
        this.right = right;
        this.jump = jump;
        this.primary = primary;
        this.secondary = secondary;
        this.playerMenu = playerMenu;

        final int xOffset = 90;
        final float buttonWidth = GraphicsBuffer.REFERENCE_WIDTH / 10f;
        this.leftKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, this::pressKeybindButton)
                .text(getLeftKeyText())
                .tag(LEFT_KEY_TAG)
                .build();
        this.rightKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, this::pressKeybindButton)
                .text(getRightKeyText())
                .tag(RIGHT_KEY_TAG)
                .build();
        this.jumpKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, this::pressKeybindButton)
                .text(getJumpKeyText())
                .tag(JUMP_KEY_TAG)
                .build();
        this.primaryKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, this::pressKeybindButton)
                .text(getPrimaryKeyText())
                .tag(PRIMARY_KEY_TAG)
                .build();
        this.secondaryKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, this::pressKeybindButton)
                .text(getSecondaryKeyText())
                .tag(SECONDARY_KEY_TAG)
                .build();

        color = playerMenu.pollAvailableColor();
        final float colorButtonSize = 20f;
        this.switchColorButton = new Button.Builder(CARD_WIDTH / 2f, GraphicsBuffer.SMALL_TEXT_SIZE, colorButtonSize, colorButtonSize,
                PConstants.CENTER, this::switchColor)
                .hoverExpand(1)
                .buttonColors(color, color, color, color)
                .build();

        keybindEventListener = this::listenForKeybind;
    }

    public PlayerCard(PlayerMenu playerMenu) {
        this(0, 0, 0, 0, 0, playerMenu);
    }

    private void switchColor(Button button) {
        playerMenu.addAvailableColor(color);
        color = playerMenu.pollAvailableColor();

        button.changeColors(color, color, color, color);
    }

    private void pressKeybindButton(Button button) {
        if (listeningKeybindButton != null) {
            updateListeningButtonText();
        }

        //undo listen if pressing same button
        if (listeningKeybindButton == button) {
            clearListeningButton();
            return;
        }

        playerMenu.clearListeningButtons();

        button.changeText("...");
        listeningKeybindButton = button;
    }

    private void listenForKeybind(KeyEvent event) {
        if (listeningKeybindButton == null)
            return;

        int keyCode = event.getKeyCode();
        if (!playerMenu.canBindKey(keyCode))
            return;

        switch (listeningKeybindButton.getTag()) {
            case LEFT_KEY_TAG -> {
                left = keyCode;
                leftKeybindButton.changeText(getLeftKeyText());
            }
            case RIGHT_KEY_TAG -> {
                right = keyCode;
                rightKeybindButton.changeText(getRightKeyText());
            }
            case JUMP_KEY_TAG -> {
                jump = keyCode;
                jumpKeybindButton.changeText(getJumpKeyText());
            }
            case PRIMARY_KEY_TAG -> {
                primary = keyCode;
                primaryKeybindButton.changeText(getPrimaryKeyText());
            }
            case SECONDARY_KEY_TAG -> {
                secondary = keyCode;
                secondaryKeybindButton.changeText(getSecondaryKeyText());
            }
        }

        clearListeningButton();
        playerMenu.updateStartButtonState();
    }

    public void draw(PGraphics graphics, float mouseX, float mouseY, boolean mousePressed, int index, int playerCount) {
        float offset = index - (playerCount - 1) / 2f;
        float cardX = GraphicsBuffer.HALF_WIDTH + offset * (CARD_WIDTH + CARD_MARGIN);
        graphics.fill(Color.WHITE.hex());
        graphics.rect(cardX, GraphicsBuffer.HALF_HEIGHT, CARD_WIDTH, CARD_HEIGHT);

        graphics.pushMatrix(); //save transformations before card drawing

        PVector buttonTranslate = new PVector();
        graphics.translate(cardX, GraphicsBuffer.HALF_HEIGHT - CARD_HEIGHT / 2f);
        buttonTranslate.add(new PVector(cardX, GraphicsBuffer.HALF_HEIGHT - CARD_HEIGHT / 2f));

        //draw player keybinds
        {
            graphics.fill(0);
            graphics.textSize(GraphicsBuffer.SMALL_TEXT_SIZE);

            final float textMargin = 3;
            graphics.textAlign(PConstants.CENTER, PConstants.TOP);
            graphics.text("Player " + (index + 1), 0, textMargin);

            graphics.translate(-CARD_WIDTH / 2f, GraphicsBuffer.SMALL_TEXT_SIZE);
            buttonTranslate.add(new PVector(-CARD_WIDTH / 2f, GraphicsBuffer.SMALL_TEXT_SIZE));
            graphics.textAlign(PConstants.LEFT, PConstants.TOP);

            graphics.translate(textMargin, textMargin + GraphicsBuffer.SMALL_TEXT_SIZE);
            buttonTranslate.add(new PVector(textMargin, textMargin + GraphicsBuffer.SMALL_TEXT_SIZE));
            graphics.text("Left:", 0, 0);
            leftKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, mousePressed);
            leftKeybindButton.draw(graphics);

            graphics.translate(0, GraphicsBuffer.SMALL_TEXT_SIZE);
            buttonTranslate.y += GraphicsBuffer.SMALL_TEXT_SIZE;
            graphics.text("Right:", 0, 0);
            rightKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, mousePressed);
            rightKeybindButton.draw(graphics);

            graphics.translate(0, GraphicsBuffer.SMALL_TEXT_SIZE);
            buttonTranslate.y += GraphicsBuffer.SMALL_TEXT_SIZE;
            graphics.text("Jump:", 0, 0);
            jumpKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, mousePressed);
            jumpKeybindButton.draw(graphics);

            graphics.translate(0, GraphicsBuffer.SMALL_TEXT_SIZE);
            buttonTranslate.y += GraphicsBuffer.SMALL_TEXT_SIZE;
            graphics.text("Primary:", 0, 0);
            primaryKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, mousePressed);
            primaryKeybindButton.draw(graphics);

            graphics.translate(0, GraphicsBuffer.SMALL_TEXT_SIZE);
            buttonTranslate.y += GraphicsBuffer.SMALL_TEXT_SIZE;
            graphics.text("Secondary:", 0, 0);
            secondaryKeybindButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, mousePressed);
            secondaryKeybindButton.draw(graphics);
        }

        graphics.translate(0, GraphicsBuffer.SMALL_TEXT_SIZE);
        buttonTranslate.y += GraphicsBuffer.SMALL_TEXT_SIZE;
        switchColorButton.update(mouseX - buttonTranslate.x, mouseY - buttonTranslate.y, mousePressed);
        switchColorButton.draw(graphics);

        graphics.popMatrix(); //restore previous transformations
    }

    public int[] getKeybinds() {
        return new int[]{left, right, jump, primary, secondary};
    }

    public String getLeftKeyText() {
        return NewtKeyEvent.getKeyCodeText(left);
    }

    public String getRightKeyText() {
        if (right == -1)
            return "Unset";

        return NewtKeyEvent.getKeyCodeText(right);
    }

    public String getJumpKeyText() {
        return NewtKeyEvent.getKeyCodeText(jump);
    }

    public String getPrimaryKeyText() {
        return NewtKeyEvent.getKeyCodeText(primary);
    }

    public String getSecondaryKeyText() {
        return NewtKeyEvent.getKeyCodeText(secondary);
    }

    public void clearListeningButton() {
        if (listeningKeybindButton == null)
            return;

        updateListeningButtonText();
        listeningKeybindButton = null;
    }

    private void updateListeningButtonText() {
        switch (listeningKeybindButton.getTag()) {
            case LEFT_KEY_TAG -> leftKeybindButton.changeText(getLeftKeyText());
            case RIGHT_KEY_TAG -> rightKeybindButton.changeText(getRightKeyText());
            case JUMP_KEY_TAG -> jumpKeybindButton.changeText(getJumpKeyText());
            case PRIMARY_KEY_TAG -> primaryKeybindButton.changeText(getPrimaryKeyText());
            case SECONDARY_KEY_TAG -> secondaryKeybindButton.changeText(getSecondaryKeyText());
        }
    }

    public Consumer<KeyEvent> getKeybindEventListener() {
        return keybindEventListener;
    }

    public int getColor() {
        return color;
    }
}
