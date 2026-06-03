package me.abtu.util;


import com.jogamp.newt.event.KeyEvent;

public final class NewtKeyEvent {
    public static String getKeyCodeText(int keyCode) {
        //match unset keybind
        if (keyCode == KeyEvent.VK_UNDEFINED)
            return "Unset";

        //most matching use ASCII

        //match special characters before alphabet
        if (keyCode >= KeyEvent.VK_EXCLAMATION_MARK && keyCode <= KeyEvent.VK_AT)
            return String.valueOf((char) keyCode);

        //match alphabet
        if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z)
            return String.valueOf((char) keyCode);

        //match special characters after alphabet
        if (keyCode >= KeyEvent.VK_OPEN_BRACKET && keyCode <= KeyEvent.VK_BACK_QUOTE)
            return String.valueOf((char) keyCode);

        //match functions keys as they are mapped to lowercase alphabet
        if (keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F24)
            return "F" + (keyCode - KeyEvent.VK_F1 + 1);

        //match special characters after ASCII lowercase alphabet
        if (keyCode >= KeyEvent.VK_LEFT_BRACE && keyCode <= KeyEvent.VK_TILDE)
            return String.valueOf((char) keyCode);

        //match numpad digits
        if (keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9)
            return "Numpad " + (char) (keyCode - 0x50);


        //manually match modifier and action keys
        return switch (keyCode) {
            case KeyEvent.VK_HOME -> "Home";
            case KeyEvent.VK_END, KeyEvent.VK_FINAL -> "End";
            case KeyEvent.VK_PRINTSCREEN -> "Print Screen";

            case KeyEvent.VK_BACK_SPACE -> "Backspace";
            case KeyEvent.VK_TAB -> "Tab";
            case KeyEvent.VK_PAGE_DOWN -> "Page Down";
            case KeyEvent.VK_CLEAR -> "Clear";
            case KeyEvent.VK_ENTER -> "Enter";
            case KeyEvent.VK_SHIFT -> "Shift";
            case KeyEvent.VK_PAGE_UP -> "Page Up";
            case KeyEvent.VK_CONTROL -> "Control";
            case KeyEvent.VK_ALT -> "Alt";
            case KeyEvent.VK_ALT_GRAPH -> "Right Alt";
            case KeyEvent.VK_CAPS_LOCK -> "Caps Lock";
            case KeyEvent.VK_PAUSE -> "Pause";
            case KeyEvent.VK_SCROLL_LOCK -> "Scroll Lock";
            case KeyEvent.VK_CANCEL -> "Cancel";
            case KeyEvent.VK_INSERT -> "Insert";

            case KeyEvent.VK_SEPARATOR, KeyEvent.VK_DECIMAL -> "Numpad .";
            case KeyEvent.VK_ADD -> "Numpad +";
            case KeyEvent.VK_SUBTRACT -> "Numpad -";
            case KeyEvent.VK_MULTIPLY -> "Numpad *";
            case KeyEvent.VK_DIVIDE -> "Numpad /";
            case KeyEvent.VK_DELETE -> "Delete";
            case KeyEvent.VK_NUM_LOCK -> "Num Lock";

            case KeyEvent.VK_LEFT -> "Left";
            case KeyEvent.VK_UP -> "Up";
            case KeyEvent.VK_RIGHT -> "Right";
            case KeyEvent.VK_DOWN -> "Down";

            case KeyEvent.VK_WINDOWS, KeyEvent.VK_META -> "Meta";

            case KeyEvent.VK_HELP -> "Help";
            case KeyEvent.VK_COMPOSE -> "Compose";
            case KeyEvent.VK_BEGIN -> "Begin";
            case KeyEvent.VK_STOP -> "Stop";

            default -> "Unmapped Key " + keyCode;
        };
    }
}
