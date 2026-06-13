package me.abtu.game.entity.player;

import com.jogamp.newt.event.KeyEvent;
import me.abtu.Main;
import me.abtu.audio.SoundManager;
import me.abtu.game.entity.PhysicsEntity;
import me.abtu.game.entity.player.abilities.Ability;
import me.abtu.game.entity.player.abilities.PrimaryAbility;
import me.abtu.game.entity.player.abilities.SecondaryAbility;
import me.abtu.game.environment.Platform;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.util.Color;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.util.function.Consumer;


public class Player extends PhysicsEntity {
    //unscaled
    protected static final float JUMP_FORCE = 12.5f;
    protected static final int COYOTE_FRAMES = 5;
    //scaled for delta time
    protected static final float ACCELERATION = 12.5f;

    protected final int left, right, jump, primary, secondary;

    protected boolean isOnPlatform = false;
    protected int coyoteFrames = COYOTE_FRAMES; //small numbers of frames to let players jump slightly after they are already in air

    protected int xInput, lastXInput; //last x input is the last non-zero input; determines which way player is facing
    protected final Ability primaryAbility, secondaryAbility;

    protected Consumer<KeyEvent> keyPressListener, keyReleaseListener;
    protected boolean leftKeyDown, rightKeyDown, jumpKeyDown, primaryKeyDown, secondaryKeyDown;

    protected float maxHealth = 100f;
    protected float health = maxHealth;
    protected final Runnable deathEventListener;
    protected final SoundFile hurtSound;


    public Player(int[] keybinds, float horizontalFraction, Runnable deathEventListener, Main main) {
        super(0, 0, 20, 50);
        left = keybinds[0];
        right = keybinds[1];
        jump = keybinds[2];
        primary = keybinds[3];
        secondary = keybinds[4];

        //spread out players at the bottom of the screen
        //horizontal fraction depends on each player's number/index
        x = PApplet.lerp(width / 2f, GraphicsBuffer.REFERENCE_WIDTH - width / 2f, horizontalFraction);
        y = GraphicsBuffer.REFERENCE_HEIGHT - height / 2f;

        keyPressListener = this::keyPressed;
        keyReleaseListener = this::keyReleased;

        SoundManager soundManager = main.getSoundManager();
        primaryAbility = new PrimaryAbility(soundManager.throwing);
        secondaryAbility = new SecondaryAbility(soundManager.fireball);

        hurtSound = soundManager.hit;
        this.deathEventListener = deathEventListener;
    }

    public void draw(PGraphics graphics) {
        if (isDead())
            return;

        graphics.rectMode(PConstants.CENTER);
        graphics.strokeWeight(1);
        graphics.stroke(0);
        graphics.fill(Color.RED.hex());
        graphics.rect(x, y, width, height);
    }

    @Override
    public void updateInternal(Main main) {
        //dont let players go off screen
        x = Math.clamp(x, width / 2f, GraphicsBuffer.REFERENCE_WIDTH - width / 2f);
        y = Math.clamp(y, -height / 2f, GraphicsBuffer.REFERENCE_HEIGHT - height / 2f);

        platformCheck(main.getArena().getPlatforms());

        //update coyote time
        coyoteFrames--;
        if (isOnPlatform)
            coyoteFrames = COYOTE_FRAMES;

        final float deltaTimeSeconds = main.getDeltaTime() / 1000f;
        updateAbilities(main, deltaTimeSeconds);
    }

    private void platformCheck(Platform[] platforms) {
        final float leftEdge = x - width / 2f;
        final float rightEdge = x + width / 2f;
        final float bottomEdge = y + height / 2f;
        final float previousFrameBottomEdge = previousFrameY + height / 2f;

        //check if player should be on a platform if player was above it last frame and is now at or below it
        for (Platform platform : platforms) {
            if (platform.canObjectStandOn(leftEdge, rightEdge,
                    bottomEdge, previousFrameBottomEdge, velocity.y)) {
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
        secondaryAbility.update(deltaTimeSeconds);

        if (primaryKeyDown)
            primaryAbility.tryUseAbility(this, main);

        if (secondaryKeyDown)
            secondaryAbility.tryUseAbility(this, main);
    }

    @Override
    protected void updateVelocity(float deltaTimeSeconds) {
        //move player on input
        if (!shouldApplyFriction())
            velocity.x += xInput * ACCELERATION * deltaTimeSeconds;

        super.updateVelocity(deltaTimeSeconds);

        //jump if not in air
        if (jumpKeyDown && !isInAir())
            jump();
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
        //ensure smooth movement when both keys are pressed by prioritizing last one held
        if (leftKeyDown)
            xInput = -1;
        if (rightKeyDown)
            xInput = 1;

        if (!leftKeyDown && !rightKeyDown)
            xInput = 0;
    }

    private void jump() {
        velocity.y = -JUMP_FORCE;
        coyoteFrames = 0; //reset coyote time to prevent extra jumps
    }

    public void cleanup(Main main) {
        //remove event listeners from main to prevent leaking and keep event clean
        main.removeKeyPressEventListener(keyPressListener);
        main.removeKeyReleaseEventListener(keyReleaseListener);
    }

    @Override
    protected boolean isInAir() {
        return y < GraphicsBuffer.REFERENCE_HEIGHT - height / 2f && !isOnPlatform && coyoteFrames <= 0;
    }

    @Override
    protected boolean shouldApplyFriction() {
        return xInput == 0;
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
            deathEventListener.run();

        hurtSound.play();
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void resetForRematch(float horizontalFraction) {
        health = maxHealth;

        x = PApplet.lerp(width / 2f, GraphicsBuffer.REFERENCE_WIDTH - width / 2f, horizontalFraction);
        y = GraphicsBuffer.REFERENCE_HEIGHT - height / 2f;
    }

    public boolean isDead() {
        return health <= 0;
    }

    @Override
    protected boolean shouldUpdate() {
        return !isDead();
    }
}
