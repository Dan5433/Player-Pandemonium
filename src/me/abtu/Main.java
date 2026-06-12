package me.abtu;

import me.abtu.game.entity.Entity;
import me.abtu.game.entity.player.Player;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import me.abtu.graphics.game.EntityGraphics;
import me.abtu.graphics.game.GameArena;
import me.abtu.graphics.game.PlayerHealth;
import me.abtu.graphics.game.WinScreen;
import me.abtu.graphics.ui.PauseMenu;
import me.abtu.graphics.ui.PlayerMenu;
import me.abtu.graphics.ui.TitleScreen;
import processing.core.PApplet;
import processing.core.PFont;
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

    //control values
    private State state = State.MENU;
    private float deltaTime = 0;

    //gameplay
    private Player[] players;
    //graphics
    private GraphicsBuffer ui, entityGraphics, pauseMenu, winScreen;


    public void setup() {
//        frameRate(5);
        loadFonts();

        initializeGraphics();
    }

    private void initializeGraphics() {
        ui = new TitleScreen(this, JAVA2D);
        pauseMenu = new PauseMenu(this, JAVA2D);
    }

    public void draw() {
        deltaTime = (System.nanoTime() - frameRateLastNanos) / 1_000_000f;
        background(255);

        if (state == State.GAME)
            gameUpdate();


        //draw graphics
        if (entityGraphics != null)
            entityGraphics.render(this);

        if (arena != null)
            arena.render(this);

        if (ui != null)
            ui.render(this);

        if (state == State.PAUSED)
            pauseMenu.render(this);

        if (state == State.MATCH_WIN)
            winScreen.render(this);
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
    public void quit(Button button) {
        System.exit(0);
    }

    @SuppressWarnings("unused")
    public void openPlayerMenu(Button button) {
        ui = new PlayerMenu(this, JAVA2D);
    }

    @SuppressWarnings("unused")
    public void startGame(Button button) {
        PlayerMenu playerMenu = (PlayerMenu) ui;
        players = playerMenu.getPlayers(this);
        Collections.addAll(entities, players);

        playerMenu.cleanup(this);

        ui = new PlayerHealth(this, JAVA2D);
        arena = new GameArena(this, JAVA2D);
        entityGraphics = new EntityGraphics(this, JAVA2D);
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

        entities.clear();

        ui = new TitleScreen(this, JAVA2D);
        state = State.MENU;
    }

    @SuppressWarnings("unused")
    public void rematch(Button button) {
        for (int i = 0; i < players.length; i++) {
            float horizontalFraction = (float) i / (players.length - 1);
            players[i].resetForRematch(horizontalFraction);
        }

        state = State.GAME;
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

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public Player[] getPlayers() {
        return players;
    }

    public void checkForWin() {
        int alivePlayers = 0;
        int winnerNumber = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].getHealth() <= 0)
                continue;

            alivePlayers++;
            winnerNumber = i + 1;
        }

        if (alivePlayers == 1)
            triggerMatchWin(winnerNumber);
    }

    private void triggerMatchWin(int winnerNumber) {
        state = State.MATCH_WIN;
        winScreen = new WinScreen(this, JAVA2D, winnerNumber);
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    private enum State {
        MENU,
        GAME,
        PAUSED,
        MATCH_WIN
    }

    public GameArena getArena() {
        return arena;
    }
}