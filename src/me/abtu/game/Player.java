package me.abtu.game;


import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import processing.core.PConstants;

import java.awt.event.KeyEvent;

public class Player {
    private static final String LEFT_KEY_TAG = "left";
    private static final String RIGHT_KEY_TAG = "right";
    private static final String JUMP_KEY_TAG = "jump";
    private static final String PRIMARY_KEY_TAG = "primary";
    private static final String SECONDARY_KEY_TAG = "secondary";
    private final Runnable onPressBindButton;

    private final Button leftKeybindButton, rightKeybindButton, jumpKeybindButton, primaryKeybindButton, secondaryKeybindButton;
    private int left, right, jump, primary, secondary;
    private Button listeningKeybindButton;

    public Player(int jump, int left, int right, int primary, int secondary, Runnable onPressBindButton) {
        this.left = left;
        this.right = right;
        this.jump = jump;
        this.primary = primary;
        this.secondary = secondary;
        this.onPressBindButton = onPressBindButton;

        final int xOffset = 90;
        final float buttonWidth = GraphicsBuffer.REFERENCE_WIDTH / 16f;
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
    }

    public Player(Runnable onPressBindButton) {
        this(-1, -1, -1, -1, -1, onPressBindButton);
    }

    private void pressKeybindButton(Button button) {
        onPressBindButton.run();

        if (listeningKeybindButton != null) {
            updateListeningButtonText();
        }

        //undo listen if pressing same button
        if (listeningKeybindButton == button) {
            clearListeningButton();
            return;
        }

        button.changeText("...");
        listeningKeybindButton = button;
    }

    public void listenForKeybind(processing.event.KeyEvent event) {
        if (listeningKeybindButton == null)
            return;

        switch (listeningKeybindButton.getTag()) {
            case LEFT_KEY_TAG -> {
                left = event.getKeyCode();
                leftKeybindButton.changeText(getLeftKeyText());
            }
            case RIGHT_KEY_TAG -> {
                right = event.getKeyCode();
                rightKeybindButton.changeText(getRightKeyText());
            }
            case JUMP_KEY_TAG -> {
                jump = event.getKeyCode();
                jumpKeybindButton.changeText(getJumpKeyText());
            }
            case PRIMARY_KEY_TAG -> {
                primary = event.getKeyCode();
                primaryKeybindButton.changeText(getPrimaryKeyText());
            }
            case SECONDARY_KEY_TAG -> {
                secondary = event.getKeyCode();
                secondaryKeybindButton.changeText(getSecondaryKeyText());
            }
        }

        clearListeningButton();
    }

    public String getLeftKeyText() {
        return left != -1 ? KeyEvent.getKeyText(left) : "Unset";
    }

    public String getRightKeyText() {
        return right != -1 ? KeyEvent.getKeyText(right) : "Unset";
    }

    public String getJumpKeyText() {
        return jump != -1 ? KeyEvent.getKeyText(jump) : "Unset";
    }

    public String getPrimaryKeyText() {
        return primary != -1 ? KeyEvent.getKeyText(primary) : "Unset";
    }

    public String getSecondaryKeyText() {
        return secondary != -1 ? KeyEvent.getKeyText(secondary) : "Unset";
    }

    public Button getLeftKeybindButton() {
        return leftKeybindButton;
    }

    public Button getRightKeybindButton() {
        return rightKeybindButton;
    }

    public Button getJumpKeybindButton() {
        return jumpKeybindButton;
    }

    public Button getPrimaryKeybindButton() {
        return primaryKeybindButton;
    }

    public Button getSecondaryKeybindButton() {
        return secondaryKeybindButton;
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
}
