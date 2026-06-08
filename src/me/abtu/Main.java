package me.abtu;

import me.abtu.game.entity.Entity;
import me.abtu.game.entity.player.Player;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import me.abtu.graphics.game.EntityGraphics;
import me.abtu.graphics.game.GameArena;
import me.abtu.graphics.ui.PauseMenu;
import me.abtu.graphics.ui.PlayerMenu;
import me.abtu.graphics.ui.TitleScreen;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.event.KeyEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

public final class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    public void settings() {
        size(GraphicsBuffer.REFERENCE_WIDTH, GraphicsBuffer.REFERENCE_HEIGHT, P2D);
        fullScreen(P2D);
        noSmooth();
    }


    //events
    private final ArrayList<Consumer<com.jogamp.newt.event.KeyEvent>> keyPressEventListeners = new ArrayList<>();
    private final ArrayList<Consumer<com.jogamp.newt.event.KeyEvent>> keyReleaseEventListeners = new ArrayList<>();

    // fonts
    private PFont pixelbit, jersey;

    private final ArrayList<Entity> entities = new ArrayList<>();
    private GameArena arena;
    private PImage cachedArena;

    //control values
    private State state = State.MENU;
    private float deltaTime = 0;

    //gameplay
    private Player[] players;
    //graphics
    private GraphicsBuffer ui, entityGraphics, pauseMenu;


    public void setup() {
        loadFonts();

        initializeGraphics();
    }

    private void initializeGraphics() {
        ui = new TitleScreen(this, NEAREST_NEIGHBOR);
        pauseMenu = new PauseMenu(this, NEAREST_NEIGHBOR);
    }

    public void draw() {
        deltaTime = (System.nanoTime() - frameRateLastNanos) / 1_000_000f;
        background(255);

        if (state == State.GAME)
            gameUpdate();


        //draw graphics
        if (entityGraphics != null)
            image(entityGraphics.getGraphicsImage(this), 0, 0);

        if (arena != null)
            image(cachedArena, 0, 0);

        if (ui != null)
            image(ui.getGraphicsImage(this), 0, 0);

        if (state == State.PAUSED)
            image(pauseMenu.getGraphicsImage(this), 0, 0);
    }

    public void keyPressed(KeyEvent event) {
        //prevent quitting via escape key
        if (event.getKeyCode() == ESC) {
            togglePause(null);
            key = 0;
        }


        for (Consumer<com.jogamp.newt.event.KeyEvent> listener : keyPressEventListeners)
            listener.accept((com.jogamp.newt.event.KeyEvent) event.getNative());
    }

    public void keyReleased(KeyEvent event) {
        for (Consumer<com.jogamp.newt.event.KeyEvent> listener : keyReleaseEventListeners)
            listener.accept((com.jogamp.newt.event.KeyEvent) event.getNative());
    }

    private void gameUpdate() {
        for (Entity entity : new ArrayList<>(entities))
            entity.update(this);
    }

    @SuppressWarnings("unused")
    public void openPlayerMenu(Button button) {
        ui = new PlayerMenu(this, NEAREST_NEIGHBOR);
    }

    @SuppressWarnings("unused")
    public void startGame(Button button) {
        PlayerMenu playerMenu = (PlayerMenu) ui;
        players = playerMenu.getPlayers(this);
        Collections.addAll(entities, players);

        playerMenu.cleanup(this);

        ui = null;
        arena = new GameArena(this, NEAREST_NEIGHBOR);
        cachedArena = arena.getGraphicsImage(this);
        entityGraphics = new EntityGraphics(this, NEAREST_NEIGHBOR);
        state = State.GAME;
    }

    @SuppressWarnings("unused")
    public void togglePause(Button button) {
        if (state == State.GAME)
            state = State.PAUSED;
        else if (state == State.PAUSED)
            state = State.GAME;
    }

    @SuppressWarnings("unused")
    public void exitToTitleScreen(Button button) {
        for (Player player : players)
            player.cleanup(this);

        ui = new TitleScreen(this, NEAREST_NEIGHBOR);
        state = State.MENU;
    }

    public void addKeyPressEventListener(Consumer<com.jogamp.newt.event.KeyEvent> listener) {
        keyPressEventListeners.add(listener);
    }

    public void removeKeyPressEventListener(Consumer<com.jogamp.newt.event.KeyEvent> listener) {
        keyPressEventListeners.remove(listener);
    }

    public void addKeyReleaseEventListener(Consumer<com.jogamp.newt.event.KeyEvent> listener) {
        keyReleaseEventListeners.add(listener);
    }

    public void removeKeyReleaseEventListener(Consumer<com.jogamp.newt.event.KeyEvent> listener) {
        keyReleaseEventListeners.remove(listener);
    }

    private void loadFonts() {
        pixelbit = createFont("fonts/Pixelbit.ttf", 16, false);
        jersey = createFont("fonts/jersey10/Jersey10-Regular.ttf", 20, false);
    }

    public PFont getTitleFont() {
        return jersey;
    }

    public PFont getDefaultFont() {
        return pixelbit;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    private enum State {
        MENU,
        GAME,
        PAUSED
    }

    public GameArena getArena() {
        return arena;
    }
}