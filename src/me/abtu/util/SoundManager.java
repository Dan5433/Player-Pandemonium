package me.abtu.util;

import processing.core.PApplet;
import processing.sound.SoundFile;

public final class SoundManager {
    private static SoundFile throwing, fireball, hit;

    public static void initialize(PApplet app) {
        throwing = new SoundFile(app, "sfx/abilities/throw.wav");
        fireball = new SoundFile(app, "sfx/abilities/fireball.wav");
        hit = new SoundFile(app, "sfx/hit.wav");
    }

    public static SoundFile getThrowing() {
        return throwing;
    }

    public static SoundFile getFireball() {
        return fireball;
    }

    public static SoundFile getHit() {
        return hit;
    }
}
