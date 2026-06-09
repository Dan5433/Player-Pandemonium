package me.abtu.game.entity.player;

import com.jogamp.newt.event.KeyEvent;
import me.abtu.Main;
import me.abtu.game.entity.Entity;
import me.abtu.game.entity.player.abilities.Ability;
import me.abtu.game.entity.player.abilities.PrimaryAbility;
import me.abtu.game.environment.Platform;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.function.Consumer;


public class Player extends Entity {
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

    protected boolean isOnPlatform = false;
    protected int coyoteFrames = COYOTE_FRAMES;

    protected int xInput, lastXInput;
    protected final Ability primaryAbility, secondaryAbility;

    protected Consumer<KeyEvent> keyPressListener, keyReleaseListener;
    protected boolean leftKeyDown, rightKeyDown, jumpKeyDown, primaryKeyDown, secondaryKeyDown;

    protected float health = 100f;


    public Player(int[] keybinds, float horizontalFraction) {
        super(0, 0, 20, 50);
        left = keybinds[0];
        right = keybinds[1];
        jump = keybinds[2];
        primary = keybinds[3];
        secondary = keybinds[4];

        x = PApplet.lerp(width / 2f, GraphicsBuffer.REFERENCE_WIDTH - width / 2f, horizontalFraction);
        y = GraphicsBuffer.REFERENCE_HEIGHT - height / 2f;

        velocity = new PVector(0, 1);

        keyPressListener = this::keyPressed;
        keyReleaseListener = this::keyReleased;

        primaryAbility = new PrimaryAbility();
        secondaryAbility = null;
    }

    public void draw(PGraphics graphics) {
        graphics.rectMode(PConstants.CENTER);
        graphics.strokeWeight(1);
        graphics.stroke(0);
        graphics.fill(255, 0, 0);
        graphics.rect(x, y, width, height);
    }

    public void update(Main main) {
        float deltaTimeSeconds = main.getDeltaTime() / 1000f;

        updateVelocity(deltaTimeSeconds);

        x += velocity.x;
        x = Math.clamp(x, width / 2f, GraphicsBuffer.REFERENCE_WIDTH - width / 2f);

        float previousY = y;
        y += velocity.y;
        y = Math.clamp(y, -height / 2f, GraphicsBuffer.REFERENCE_HEIGHT - height / 2f);

        platformCheck(main.getArena().getPlatforms(), previousY);

        coyoteFrames--;
        if (isOnPlatform)
            coyoteFrames = COYOTE_FRAMES;

        updateAbilities(main, deltaTimeSeconds);
    }

    private void platformCheck(Platform[] platforms, float previousY) {
        for (Platform platform : platforms) {
            if (platform.canObjectStandOn(x, x + width / 2f, y + height / 2f, previousY + height / 2f, velocity.y)) {
                isOnPlatform = true;

                //set y to platform top
                y = platform.getTopSurfaceY() - height / 2f;
                return;
            }
        }

        isOnPlatform = false;
    }

    private void updateAbilities(Main main, float deltaTimeSeconds) {
        primaryAbility.update(deltaTimeSeconds);

        if (primaryKeyDown)
            primaryAbility.tryUseAbility(this, main);
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
            lastXInput = xInput;
        }

        if (keyCode == right) {
            rightKeyDown = true;
            xInput = 1;
            lastXInput = xInput;
        }

        if (keyCode == jump) {
            jumpKeyDown = true;
        }

        if (keyCode == primary)
            primaryKeyDown = true;

        if (keyCode == secondary)
            secondaryKeyDown = true;
    }

    private void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (keyCode == left)
            leftKeyDown = false;

        if (keyCode == right)
            rightKeyDown = false;

        if (keyCode == jump)
            jumpKeyDown = false;

        if (keyCode == primary)
            primaryKeyDown = false;

        if (keyCode == secondary)
            secondaryKeyDown = false;


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
        return y < GraphicsBuffer.REFERENCE_HEIGHT - height / 2f && !isOnPlatform;
    }

    public Consumer<KeyEvent> getKeyPressListener() {
        return keyPressListener;
    }

    public Consumer<KeyEvent> getKeyReleaseListener() {
        return keyReleaseListener;
    }

    public int getLastXInput() {
        return lastXInput;
    }

    public PVector getTopLeftEdge() {
        return new PVector(x - width / 2f, y - height / 2f);
    }

    public PVector getBottomRightEdge() {
        return new PVector(x + width / 2f, y + height / 2f);
    }

    public void dealDamage(float damage) {
        health -= damage;
        if (health <= 0)
            System.out.println("Dead");
    }
}
