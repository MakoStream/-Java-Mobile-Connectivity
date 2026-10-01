package mobileoperator.menu.items;

import mobileoperator.menu.MenuItem;

public class InputMenuItem extends MenuItem {

    private final StringBuilder value;
    private final boolean numericOnly;
    private final int maxLength;
    private int cursorPosition;

    public InputMenuItem(
            String title,
            String initialValue,
            boolean numericOnly,
            int maxLength
    ) {
        super(title);

        if (maxLength < 0) {
            throw new IllegalArgumentException(
                    "Максимальна довжина не може бути від'ємною."
            );
        }

        if (initialValue == null) {
            initialValue = "";
        }

        if (initialValue.length() > maxLength
                || (numericOnly && !initialValue.matches("\\d*"))) {
            throw new IllegalArgumentException(
                    "Початкове значення не відповідає обмеженням."
            );
        }

        this.value = new StringBuilder(initialValue);
        this.numericOnly = numericOnly;
        this.maxLength = maxLength;
        this.cursorPosition = value.length();
    }

    public String getValue() {
        return value.toString();
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    public boolean insert(char character) {
        if (value.length() >= maxLength) {
            return false;
        }

        if (numericOnly && !Character.isDigit(character)) {
            return false;
        }

        value.insert(cursorPosition, character);
        cursorPosition++;

        return true;
    }

    public void moveCursorLeft() {
        if (cursorPosition > 0) {
            cursorPosition--;
        }
    }

    public void moveCursorRight() {
        if (cursorPosition < value.length()) {
            cursorPosition++;
        }
    }

    public void backspace() {
        if (cursorPosition > 0) {
            value.deleteCharAt(cursorPosition - 1);
            cursorPosition--;
        }
    }

    public void delete() {
        if (cursorPosition < value.length()) {
            value.deleteCharAt(cursorPosition);
        }
    }

    public String getDisplayText(boolean showCursor) {

        if (!showCursor) {
            return getTitle() + ": " + getValue();
        }

        String text = getValue();

        String beforeCursor = text.substring(0, cursorPosition);
        String afterCursor = text.substring(cursorPosition);

        return getTitle()
                + ": "
                + beforeCursor
                + "|"
                + afterCursor;
    }

    @Override
    public String getDisplayText() {
        return getDisplayText(false);
    }

    @Override
    public void execute() {
        // Поле введення саме по собі не виконує дію.
    }
}