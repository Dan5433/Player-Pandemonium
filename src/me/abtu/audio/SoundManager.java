package me.abtu.audio;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class SoundManager {
    public final SoundFile throwing;
    public final SoundFile fireball;

    public SoundManager(PApplet app) {
        throwing = new SoundFile(app, "throw.wav");
        fireball = new SoundFile(app, "fireball.wav");
    }
}
