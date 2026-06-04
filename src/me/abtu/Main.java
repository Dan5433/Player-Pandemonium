package me.abtu;

import me.abtu.game.Player;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import me.abtu.graphics.game.GameArena;
import me.abtu.graphics.ui.PlayerMenu;
import me.abtu.graphics.ui.TitleScreen;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.event.KeyEvent;

import java.util.ArrayList;
import java.util.function.Consumer;

public final class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    public void settings() {
        fullScreen(P2D);
//        size(GraphicsBuffer.REFERENCE_WIDTH, GraphicsBuffer.REFERENCE_HEIGHT);
        noSmooth();
    }

    // fonts
    private PFont pixelbit, jersey;

    //graphics
    private GraphicsBuffer ui, moving;
    private PImage arena;

    //events
    private final ArrayList<Consumer<KeyEvent>> keyPressEventListeners = new ArrayList<>();

    //gameplay
    private Player[] players;


    public void setup() {
        loadFonts();

        initializeGraphics();
    }

    private void initializeGraphics() {
        ui = new TitleScreen(this, NEAREST_NEIGHBOR);
    }

    public void draw() {
        background(255);

        //draw graphics
        if (arena != null)
            image(arena, 0, 0);

        if (moving != null)
            image(moving.getGraphicsImage(this), 0, 0);

        if (ui != null)
            image(ui.getGraphicsImage(this), 0, 0);
    }

    public void keyPressed(KeyEvent event) {
        //prevent quitting via escape key
        if (event.getKeyCode() == ESC)
            key = 0;


        for (Consumer<KeyEvent> listener : keyPressEventListeners)
            listener.accept(event);
    }

    @SuppressWarnings("unused")
    public void openPlayerMenu(Button button) {
        ui = new PlayerMenu(this, NEAREST_NEIGHBOR);
    }

    @SuppressWarnings("unused")
    public void startGame(Button button) {
        PlayerMenu playerMenu = (PlayerMenu) ui;
        players = playerMenu.getPlayers();

        ui = null;
        arena = new GameArena(this, NEAREST_NEIGHBOR).getGraphicsImage(this);
    }

    public void addKeyPressEventListener(Consumer<KeyEvent> listener) {
        keyPressEventListeners.add(listener);
    }

    public void removeKeyPressEventListener(Consumer<KeyEvent> listener) {
        keyPressEventListeners.remove(listener);
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
}