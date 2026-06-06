package me.abtu.graphics.ui;


import com.jogamp.newt.event.KeyEvent;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import me.abtu.util.NewtKeyEvent;
import processing.core.PConstants;

import java.util.function.Consumer;
import java.util.function.Function;

public class PlayerCard {
    private static final String LEFT_KEY_TAG = "left";
    private static final String RIGHT_KEY_TAG = "right";
    private static final String JUMP_KEY_TAG = "jump";
    private static final String PRIMARY_KEY_TAG = "primary";
    private static final String SECONDARY_KEY_TAG = "secondary";
    private final Runnable onPressBindButton, onKeybindListenEvent;
    private final Function<Integer, Boolean> canBindKey;

    private final Button leftKeybindButton, rightKeybindButton, jumpKeybindButton, primaryKeybindButton, secondaryKeybindButton;
    private final Consumer<KeyEvent> keybindEventListener;
    private int left, right, jump, primary, secondary;
    private Button listeningKeybindButton;

    public PlayerCard(int jump, int left, int right, int primary, int secondary, Runnable onPressBindButton, Function<Integer, Boolean> canBindKey, Runnable onKeybindListenEvent) {
        this.left = left;
        this.right = right;
        this.jump = jump;
        this.primary = primary;
        this.secondary = secondary;
        this.onPressBindButton = onPressBindButton;
        this.canBindKey = canBindKey;
        this.onKeybindListenEvent = onKeybindListenEvent;

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

        keybindEventListener = this::listenForKeybind;
    }

    public PlayerCard(Runnable onPressBindButton, Function<Integer, Boolean> canBindKey, Runnable onKeybindListenEvent) {
        this(0, 0, 0, 0, 0, onPressBindButton, canBindKey, onKeybindListenEvent);
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

        onPressBindButton.run();

        button.changeText("...");
        listeningKeybindButton = button;
    }

    private void listenForKeybind(KeyEvent event) {
        if (listeningKeybindButton == null)
            return;

        int keyCode = event.getKeyCode();
        if (!canBindKey.apply(keyCode))
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
        onKeybindListenEvent.run();
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

    public Consumer<KeyEvent> getKeybindEventListener() {
        return keybindEventListener;
    }
}
