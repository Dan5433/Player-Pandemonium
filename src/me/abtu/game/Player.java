package me.abtu.game;


import java.awt.event.KeyEvent;

public class Player {
    public static final Player PLAYER_ONE = new Player(KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D,
            KeyEvent.VK_Q, KeyEvent.VK_E);
    public static final Player PLAYER_TWO = new Player(KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
            KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL);

    private final int left, right, jump, primary, secondary;

    public Player(int jump, int left, int right, int primary, int secondary) {
        this.left = left;
        this.right = right;
        this.jump = jump;
        this.primary = primary;
        this.secondary = secondary;
    }

    public Player() {
        this.left = -1;
        this.right = -1;
        this.jump = -1;
        this.primary = -1;
        this.secondary = -1;
    }


    public String getLeftKeyText() {
        return left != -1 ? KeyEvent.getKeyText(left) : "Unset";
    }

    public String getRightKeyText() {
        return right != -1 ? KeyEvent.getKeyText(right) : "Unset";
    }

    public String getJumpKeyText() {
        return jump != -1 ? KeyEvent.getKeyText(jump) : "Unset";
    }

    public String getPrimaryKeyText() {
        return primary != -1 ? KeyEvent.getKeyText(primary) : "Unset";
    }

    public String getSecondaryKeyText() {
        return secondary != -1 ? KeyEvent.getKeyText(secondary) : "Unset";
    }
}
