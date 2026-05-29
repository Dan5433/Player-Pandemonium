import processing.core.PApplet;

public final class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    public void settings() {
        fullScreen(P2D);
    }

    public void setup() {
        rectMode(CORNERS);
    }

    public void draw() {
        background(255);
    }
}