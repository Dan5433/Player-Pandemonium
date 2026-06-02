package me.abtu.game;


import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import processing.core.PConstants;

import java.awt.event.KeyEvent;

public class Player {
    public static final Player PLAYER_ONE = new Player(KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D,
            KeyEvent.VK_Q, KeyEvent.VK_E);
    public static final Player PLAYER_TWO = new Player(KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
            KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL);

    private final int left, right, jump, primary, secondary;

    private final Button leftKeybindButton, rightKeybindButton, jumpKeybindButton, primaryKeybindButton, secondaryKeybindButton;

    public Player(int jump, int left, int right, int primary, int secondary) {
        this.left = left;
        this.right = right;
        this.jump = jump;
        this.primary = primary;
        this.secondary = secondary;

        final int xOffset = 90;
        final float buttonWidth = GraphicsBuffer.REFERENCE_WIDTH / 16f;
        this.leftKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, null)
                .text(getLeftKeyText())
                .build();
        this.rightKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, null)
                .text(getRightKeyText())
                .build();
        this.jumpKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, null)
                .text(getJumpKeyText())
                .build();
        this.primaryKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, null)
                .text(getPrimaryKeyText())
                .build();
        this.secondaryKeybindButton = new Button.Builder(xOffset, GraphicsBuffer.SMALL_TEXT_SIZE / 4f, buttonWidth, GraphicsBuffer.SMALL_TEXT_SIZE,
                PConstants.CENTER, null)
                .text(getSecondaryKeyText())
                .build();
    }

    public Player() {
        this(-1, -1, -1, -1, -1);
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
}
