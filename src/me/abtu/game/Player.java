package me.abtu.game;

import com.jogamp.newt.event.KeyEvent;
import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Player {
    protected final int left, right, jump, primary, secondary;
    protected final float width = 10;
    protected final float height = 20;

    protected float x, y;
    protected int xInput;
    protected boolean leftKeyDown, rightKeyDown;


    public Player(int[] keybinds) {
        this.left = keybinds[0];
        this.right = keybinds[1];
        this.jump = keybinds[2];
        this.primary = keybinds[3];
        this.secondary = keybinds[4];
    }

    public void draw(PGraphics graphics) {
        graphics.rectMode(PConstants.CORNER);
        graphics.fill(255, 0, 0);
        graphics.rect(x, y, width, height);
    }

    public void update(Main main) {
        x += xInput * main.getDeltaTime();
        x = Math.clamp(x, 0, GraphicsBuffer.REFERENCE_WIDTH - width);
    }

    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (keyCode == left) {
            leftKeyDown = true;
            xInput = -1;
        }

        if (keyCode == right) {
            rightKeyDown = true;
            xInput = 1;
        }


        if (keyCode == jump) {
        } //jump
        if (keyCode == primary) {
        } //primary
        if (keyCode == secondary) {
        } //secondary
    }

    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (keyCode == left)
            leftKeyDown = false;

        if (keyCode == right)
            rightKeyDown = false;

        if (leftKeyDown)
            xInput = -1;
        if (rightKeyDown)
            xInput = 1;

        if (!leftKeyDown && !rightKeyDown)
            xInput = 0;
    }
}
