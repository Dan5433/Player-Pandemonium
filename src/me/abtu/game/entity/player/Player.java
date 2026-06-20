package me.abtu.game.entity.player;

import com.jogamp.newt.event.KeyEvent;
import me.abtu.Main;
import me.abtu.game.entity.PlatformerEntity;
import me.abtu.game.entity.player.abilities.Ability;
import me.abtu.game.entity.player.abilities.PrimaryAbility;
import me.abtu.game.entity.player.abilities.SecondaryAbility;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.util.SoundManager;
import processing.core.*;
import processing.sound.SoundFile;

import java.util.function.Consumer;


public class Player extends PlatformerEntity {
    private static final float MAX_HEALTH = 100f;
    //unscaled
    protected static final int COYOTE_FRAMES = 3;
    //scaled by delta time
    protected static final float ACCELERATION = 300.5f;
    protected static final float JUMP_FORCE = 525.5f;

    protected final PImage spriteLeft, spriteRight;
    protected final int left, right, jump, primary, secondary;

    protected boolean isOnPlatform = false;
    protected int coyoteFrames = COYOTE_FRAMES; //small numbers of frames to let players jump slightly after they are already in air
    protected int doubleJumpsTotal; //how many times player can jump in air
    protected int doubleJumpsCounter;

    protected int xInput, lastXInput; //last x input is the last non-zero input; determines which way player is facing
    protected float acceleration = ACCELERATION;
    protected final Ability primaryAbility, secondaryAbility;

    protected Consumer<KeyEvent> keyPressListener, keyReleaseListener;
    protected boolean leftKeyDown, rightKeyDown, jumpKeyDown, primaryKeyDown, secondaryKeyDown;

    protected float maxHealth = MAX_HEALTH;
    protected float health = maxHealth;
    protected final Runnable deathEventListener;
    protected final SoundFile hurtSound;


    public Player(int[] keybinds, float horizontalFraction, Runnable deathEventListener, PImage spriteLeft, PImage spriteRight) {
        super(0, 0, spriteRight.width, spriteRight.height);
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

        hurtSound = SoundManager.getHit();
        this.deathEventListener = deathEventListener;

        primaryAbility = new PrimaryAbility(SoundManager.getThrowing());
        secondaryAbility = new SecondaryAbility(SoundManager.getFireball());

        this.spriteLeft = spriteLeft;
        this.spriteRight = spriteRight;
        lastXInput = horizontalFraction > 0.5f ? -1 : 1; //set facing direction based on x position
    }

    public void draw(PGraphics graphics) {
        if (!shouldUpdate())
            return;

        PImage sprite = lastXInput > 0 ? spriteRight : spriteLeft; //flips image based on facing direction
        graphics.imageMode(PConstants.CENTER);
        graphics.image(sprite, x, y, width, height);
    }

    @Override
    public void updateInternal(Main main) {
        super.updateInternal(main);

        //update coyote time
        coyoteFrames--;
        if (isOnPlatform)
            coyoteFrames = COYOTE_FRAMES;

        //update double jumps
        if (!isInAir())
            doubleJumpsCounter = doubleJumpsTotal;

        final float deltaTimeSeconds = main.getDeltaTime() / 1000f;
        updateAbilities(main, deltaTimeSeconds);
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
            velocity.x += xInput * acceleration * deltaTimeSeconds;

        super.updateVelocity(deltaTimeSeconds);

        //jump if not in air
        if (jumpKeyDown && !isInAir())
            jump();
    }

    private void keyPressed(KeyEvent event) {
        if (!shouldUpdate())
            return;

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
            if (isInAir() && doubleJumpsCounter > 0)
                jump();

            jumpKeyDown = true;
        }


        if (keyCode == primary)
            primaryKeyDown = true;

        if (keyCode == secondary)
            secondaryKeyDown = true;
    }

    private void keyReleased(KeyEvent event) {
        if (!shouldUpdate())
            return;

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
        if (leftKeyDown) {
            xInput = -1;
            lastXInput = xInput;
        }
        if (rightKeyDown) {
            xInput = 1;
            lastXInput = xInput;
        }

        if (!leftKeyDown && !rightKeyDown)
            xInput = 0;
    }

    private void jump() {
        velocity.y = -JUMP_FORCE;
        coyoteFrames = 0; //reset coyote time to prevent extra jumps

        if (isInAir())
            doubleJumpsCounter--;
    }

    public void cleanup(Main main) {
        //remove event listeners from main to prevent leaking and keep event clean
        main.removeKeyPressEventListener(keyPressListener);
        main.removeKeyReleaseEventListener(keyReleaseListener);
    }

    @Override
    protected boolean isInAir() {
        return super.isInAir() && coyoteFrames <= 0;
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
        maxHealth = MAX_HEALTH; //reset bonus max health
        health = maxHealth;

        velocity = new PVector(0, 0);
        //reset speed item effects
        acceleration = ACCELERATION;
        maxHorizontalVelocity = MAX_HORIZONTAL_VELOCITY;

        x = PApplet.lerp(width / 2f, GraphicsBuffer.REFERENCE_WIDTH - width / 2f, horizontalFraction);
        y = GraphicsBuffer.REFERENCE_HEIGHT - height / 2f;
        lastXInput = horizontalFraction > 0.5f ? -1 : 1; //set facing direction based on x position

        //reset double jumps
        doubleJumpsTotal = 0;
        doubleJumpsCounter = 0;

        //reset lower cooldowns items
        primaryAbility.resetCooldown();
        secondaryAbility.resetCooldown();
    }

    public boolean isDead() {
        return health <= 0;
    }

    @Override
    protected boolean shouldUpdate() {
        return !isDead();
    }

    public void heal(float healAmount) {
        health += healAmount;
        if (health > maxHealth)
            health = maxHealth;
    }

    public Ability[] getAbilities() {
        return new Ability[]{primaryAbility, secondaryAbility};
    }

    public void addDoubleJump() {
        doubleJumpsTotal++;
    }

    public void addMaxHealth(int bonus) {
        maxHealth += bonus;
    }

    public void multiplySpeed(float accelerationMultiplier, float maxSpeedMultiplier) {
        acceleration *= accelerationMultiplier;
        maxHorizontalVelocity *= maxSpeedMultiplier;
    }
}
