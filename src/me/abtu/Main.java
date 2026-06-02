package me.abtu;

import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import me.abtu.graphics.ui.PlayerMenu;
import me.abtu.graphics.ui.TitleScreen;
import processing.core.PApplet;
import processing.core.PFont;
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
    private GraphicsBuffer ui;

    //events
    private final ArrayList<Consumer<KeyEvent>> keyPressEventListeners = new ArrayList<>();


    public void setup() {
        loadFonts();

        initializeGraphics();
    }

    private void initializeGraphics() {
        ui = new TitleScreen(this, NEAREST_NEIGHBOR);
    }

    public void draw() {
        background(255);

        image(ui.getGraphicsImage(this), 0, 0);
    }

    public void keyPressed(KeyEvent event) {
        for (Consumer<KeyEvent> listener : keyPressEventListeners)
            listener.accept(event);
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

    public void pressPlayButton(Button button) {
        ui = new PlayerMenu(this, NEAREST_NEIGHBOR);
    }

    public void addKeyPressEventListener(Consumer<KeyEvent> listener) {
        keyPressEventListeners.add(listener);
    }

    public void removeKeyPressEventListener(Consumer<KeyEvent> listener) {
        keyPressEventListeners.remove(listener);
    }
}