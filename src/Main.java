import processing.core.PApplet;
import processing.core.PFont;

public final class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    public void settings() {
        fullScreen(P2D);
    }

    //fonts
    PFont pixelbit, jersey;

    public void setup() {
        rectMode(CORNERS);
        loadFonts();
    }

    public void draw() {
        background(255);

        textFont(jersey);
        textSize(32);
        fill(0);
        text("testing", mouseX, mouseY);
    }

    private void loadFonts() {
        pixelbit = createFont("fonts/Pixelbit.ttf", 32);
        jersey = createFont("fonts/jersey10/Jersey10-Regular.ttf", 32);
    }
}