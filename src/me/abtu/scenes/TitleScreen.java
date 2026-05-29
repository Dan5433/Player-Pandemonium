package me.abtu.scenes;

import me.abtu.Main;
import processing.core.PConstants;

public class TitleScreen extends Scene {
    @Override
    public void draw(Main main) {
        main.background(255);
        main.fill(0);


        main.textFont(main.jersey);
        main.textAlign(PConstants.CENTER, PConstants.CENTER);

        main.textSize(120);
        main.text("Player Pandemonium", main.width / 2f, main.height / 4f);

        main.textSize(32);
        main.text("Press Enter to Start", main.width / 2f, main.height / 2f);
    }
}
