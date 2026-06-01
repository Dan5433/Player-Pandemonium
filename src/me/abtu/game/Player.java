package me.abtu.game;


public class Player {
    //controls
    private final int left;
    private final int right;
    private final int up;
    private final int down;
    private final int primary;
    private final int secondary;

    public Player(int up, int down, int left, int right, int primary, int secondary) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.primary = primary;
        this.secondary = secondary;
    }
}
