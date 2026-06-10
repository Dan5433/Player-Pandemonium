package me.abtu.util;

public enum Color {
    RED(0xff_f0_00_00),
    GREEN(0xff_00_f0_00),
    CYAN(0xff_00_f0_f0);

    private final int hex;

    Color(int hex) {
        this.hex = hex;
    }

    public int hex() {
        return hex;
    }
}
