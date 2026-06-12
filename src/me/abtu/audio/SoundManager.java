package me.abtu.audio;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class SoundManager {
    public final SoundFile throwing;
    public final SoundFile fireball;

    public SoundManager(PApplet app) {
        throwing = new SoundFile(app, "sfx/abilities/throw.wav");
        fireball = new SoundFile(app, "sfx/abilities/fireball.wav");
    }
}
