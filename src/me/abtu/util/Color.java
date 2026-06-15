package me.abtu.util;

public enum Color {
    WHITE(0xff_ff_ff_ff),
    RED(0xff_f0_00_00),
    GREEN(0xff_00_f0_00),
    CYAN(0xff_00_f0_f0),
    GOLD(0xff_ff_ba_00);

    private final int hex;

    Color(int hex) {
        this.hex = hex;
    }

    public int hex() {
        return hex;
    }
}
