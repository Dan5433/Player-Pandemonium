package me.abtu.game.player;

import com.jogamp.newt.event.KeyEvent;
import me.abtu.Main;
import me.abtu.game.environment.Platform;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.function.Consumer;


public class Player {
    //unscaled
    protected static final float MAX_HORIZONTAL_VELOCITY = 5.5f;
    protected static final float TERMINAL_VELOCITY = 10f;
    protected static final float JUMP_FORCE = 12.5f;
    protected static final int COYOTE_FRAMES = 5;
    //scaled for delta time
    protected static final float ACCELERATION = 12.5f;
    protected static final float FRICTION = 7.5f;
    protected static final float GRAVITY = 0.75f;

    protected final int left, right, jump, primary, secondary;
    protected final float width = 20;
    protected final float height = 50;

    protected float x, y;
    protected PVector velocity = new PVector(0, 1);

    protected boolean isOnPlatform = false;
    protected int coyoteFrames = COYOTE_FRAMES;

    protected int xInput;
    protected boolean leftKeyDown, rightKeyDown, jumpKeyDown;

    protected Consumer<KeyEvent> keyPressListener, keyReleaseListener;


    public Player(int[] keybinds, float horizontalFraction) {
        this.left = keybinds[0];
        this.right = keybinds[1];
        this.jump = keybinds[2];
        this.primary = keybinds[3];
        this.secondary = keybinds[4];

        this.x = PApplet.lerp(0, GraphicsBuffer.REFERENCE_WIDTH - width, horizontalFraction);
        this.y = GraphicsBuffer.REFERENCE_HEIGHT - height;

        keyPressListener = this::keyPressed;
        keyReleaseListener = this::keyReleased;
    }

    public void draw(PGraphics graphics) {
        graphics.rectMode(PConstants.CORNER);
        graphics.strokeWeight(0.5f);
        graphics.stroke(0);
        graphics.fill(255, 0, 0);
        graphics.rect(x, y, width, height);
    }

    public void update(Main main) {
        float deltaTimeSeconds = main.getDeltaTime() / 1000f;

        updateVelocity(deltaTimeSeconds);

        x += velocity.x;
        x = Math.clamp(x, 0, GraphicsBuffer.REFERENCE_WIDTH - width);

        float previousY = y;
        y += velocity.y;
        y = Math.clamp(y, -height, GraphicsBuffer.REFERENCE_HEIGHT - height);

        platformCheck(main.getArena().getPlatforms(), previousY);

        coyoteFrames--;
        if (isOnPlatform)
            coyoteFrames = COYOTE_FRAMES;
    }

    private void platformCheck(Platform[] platforms, float previousY) {
        for (Platform platform : platforms) {
            if (platform.canObjectStandOn(x, x + width, y + height, previousY + height, velocity.y)) {
                isOnPlatform = true;

                //set y to platform top
                y = platform.getTopSurfaceY() - height;
                return;
            }
        }

        isOnPlatform = false;
    }

    private void updateVelocity(float deltaTimeSeconds) {
        //update x velocity
        if (xInput != 0)
            velocity.x += xInput * ACCELERATION * deltaTimeSeconds;
        else {
            float frictionFactor = 1 - FRICTION * deltaTimeSeconds;
            frictionFactor = Math.clamp(frictionFactor, 0, 1); //ensure friction doesnt cause velocity to invert on high delta time values
            velocity.x *= frictionFactor;

            //set velocity to 0 if very small
            if (Math.abs(velocity.x) < 0.01f)
                velocity.x = 0;
        }
        velocity.x = Math.clamp(velocity.x, -MAX_HORIZONTAL_VELOCITY, MAX_HORIZONTAL_VELOCITY);

        //update y velocity
        if (isInAir() && coyoteFrames <= 0) {
            velocity.y += 1 + GRAVITY * deltaTimeSeconds;
        } else {
            velocity.y = 0;

            if (jumpKeyDown)
                jump();
        }
        velocity.y = Math.clamp(velocity.y, -JUMP_FORCE, TERMINAL_VELOCITY);
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
            jumpKeyDown = true;
        }

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

        if (keyCode == jump)
            jumpKeyDown = false;

        //set input to respective direction if another key is down
        if (leftKeyDown)
            xInput = -1;
        if (rightKeyDown)
            xInput = 1;

        if (!leftKeyDown && !rightKeyDown)
            xInput = 0;
    }

    private void jump() {
        velocity.y = -JUMP_FORCE;
        coyoteFrames = 0;
    }

    public void cleanup(Main main) {
        main.removeKeyPressEventListener(keyPressListener);
        main.removeKeyReleaseEventListener(keyReleaseListener);
    }

    private boolean isInAir() {
        return y < GraphicsBuffer.REFERENCE_HEIGHT - height && !isOnPlatform;
    }

    public Consumer<KeyEvent> getKeyPressListener() {
        return keyPressListener;
    }

    public Consumer<KeyEvent> getKeyReleaseListener() {
        return keyReleaseListener;
    }
}
