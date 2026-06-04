package me.abtu.game;

import com.jogamp.newt.event.KeyEvent;
import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.function.Consumer;


public class Player {
    protected static final float MAX_SPEED = 10;
    protected static final float ACCELERATION = 0.5f;
    protected static final float FRICTION = 0.1f;
    protected static final float GRAVITY = 0.3f;

    protected final int left, right, jump, primary, secondary;
    protected final float width = 10;
    protected final float height = 20;

    protected float x, y;
    protected int xInput;
    protected boolean leftKeyDown, rightKeyDown;
    protected PVector velocity = new PVector(0, 1);

    protected Consumer<KeyEvent> keyPressListener, keyReleaseListener;


    public Player(int[] keybinds) {
        this.left = keybinds[0];
        this.right = keybinds[1];
        this.jump = keybinds[2];
        this.primary = keybinds[3];
        this.secondary = keybinds[4];

        keyPressListener = this::keyPressed;
        keyReleaseListener = this::keyReleased;
    }

    public void draw(PGraphics graphics) {
        graphics.rectMode(PConstants.CORNER);
        graphics.fill(255, 0, 0);
        graphics.rect(x, y, width, height);
    }

    public void update(Main main) {
        if (xInput != 0) {
            velocity.x += xInput * ACCELERATION;
        } else {
            velocity.x *= 1 - FRICTION;
            if (Math.abs(velocity.x) < 0.01f)
                velocity.x = 0;
        }
        velocity.x = Math.clamp(velocity.x, -MAX_SPEED, MAX_SPEED);

        velocity.y *= 1 + GRAVITY;
        velocity.y = Math.clamp(velocity.y, -MAX_SPEED, MAX_SPEED);

        x += velocity.x;
        x = Math.clamp(x, 0, GraphicsBuffer.REFERENCE_WIDTH - width);

        y += velocity.y;
        y = Math.clamp(y, 0, GraphicsBuffer.REFERENCE_HEIGHT - height);
    }

    private void keyPressed(KeyEvent event) {
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

    private void keyReleased(KeyEvent event) {
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

    public Consumer<KeyEvent> getKeyPressListener() {
        return keyPressListener;
    }

    public Consumer<KeyEvent> getKeyReleaseListener() {
        return keyReleaseListener;
    }
}
