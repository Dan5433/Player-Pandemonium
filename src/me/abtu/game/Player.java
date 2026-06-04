package me.abtu.game;

public class Player {
    protected final int left, right, jump, primary, secondary;
    protected float x, y;


    public Player(int[] keybinds) {
        this.left = keybinds[0];
        this.right = keybinds[1];
        this.jump = keybinds[2];
        this.primary = keybinds[3];
        this.secondary = keybinds[4];
    }
}
