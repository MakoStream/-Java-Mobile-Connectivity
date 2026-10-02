package mobileoperator.menu.items;

import mobileoperator.logging.OperationLogger;
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

        OperationLogger.operation(
                "InputMenuItem",
                "constructor",
                "title='" + title + "', initialValue='" + initialValue
                        + "', numericOnly=" + numericOnly
                        + ", maxLength=" + maxLength
        );

        if (maxLength < 0) {
            OperationLogger.error(
                    "InputMenuItem",
                    "constructor",
                    "maxLength < 0"
            );

            throw new IllegalArgumentException(
                    "Максимальна довжина не може бути від'ємною."
            );
        }

        if (initialValue == null) {
            initialValue = "";
        }

        if (initialValue.length() > maxLength
                || (numericOnly && !initialValue.matches("\\d*"))) {

            OperationLogger.error(
                    "InputMenuItem",
                    "constructor",
                    "initialValue не відповідає обмеженням"
            );

            throw new IllegalArgumentException(
                    "Початкове значення не відповідає обмеженням."
            );
        }

        this.value = new StringBuilder(initialValue);
        this.numericOnly = numericOnly;
        this.maxLength = maxLength;
        this.cursorPosition = value.length();

        OperationLogger.result(
                "InputMenuItem",
                "constructor",
                "value='" + value + "', cursor=" + cursorPosition
        );
    }

    public String getValue() {
        return value.toString();
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    public boolean insert(char character) {
        OperationLogger.operation(
                "InputMenuItem",
                "insert",
                "char='" + character
                        + "', value='" + value
                        + "', cursor=" + cursorPosition
        );

        if (value.length() >= maxLength) {
            OperationLogger.result(
                    "InputMenuItem",
                    "insert",
                    "rejected: maximum length reached"
            );

            return false;
        }

        if (numericOnly && !Character.isDigit(character)) {
            OperationLogger.result(
                    "InputMenuItem",
                    "insert",
                    "rejected: character is not numeric"
            );

            return false;
        }

        value.insert(cursorPosition, character);
        cursorPosition++;

        OperationLogger.result(
                "InputMenuItem",
                "insert",
                "value='" + value + "', cursor=" + cursorPosition
        );

        return true;
    }

    public void moveCursorLeft() {
        OperationLogger.operation(
                "InputMenuItem",
                "moveCursorLeft",
                "cursor=" + cursorPosition
        );

        if (cursorPosition > 0) {
            cursorPosition--;
        }

        OperationLogger.result(
                "InputMenuItem",
                "moveCursorLeft",
                "cursor=" + cursorPosition
        );
    }

    public void moveCursorRight() {
        OperationLogger.operation(
                "InputMenuItem",
                "moveCursorRight",
                "cursor=" + cursorPosition
                        + ", length=" + value.length()
        );

        if (cursorPosition < value.length()) {
            cursorPosition++;
        }

        OperationLogger.result(
                "InputMenuItem",
                "moveCursorRight",
                "cursor=" + cursorPosition
        );
    }

    public void backspace() {
        OperationLogger.operation(
                "InputMenuItem",
                "backspace",
                "value='" + value + "', cursor=" + cursorPosition
        );

        if (cursorPosition > 0) {
            char deletedCharacter = value.charAt(cursorPosition - 1);

            value.deleteCharAt(cursorPosition - 1);
            cursorPosition--;

            OperationLogger.result(
                    "InputMenuItem",
                    "backspace",
                    "deleted='" + deletedCharacter
                            + "', value='" + value
                            + "', cursor=" + cursorPosition
            );
        } else {
            OperationLogger.result(
                    "InputMenuItem",
                    "backspace",
                    "nothing deleted: cursor at beginning"
            );
        }
    }

    public void delete() {
        OperationLogger.operation(
                "InputMenuItem",
                "delete",
                "value='" + value + "', cursor=" + cursorPosition
        );

        if (cursorPosition < value.length()) {
            char deletedCharacter = value.charAt(cursorPosition);

            value.deleteCharAt(cursorPosition);

            OperationLogger.result(
                    "InputMenuItem",
                    "delete",
                    "deleted='" + deletedCharacter
                            + "', value='" + value
                            + "', cursor=" + cursorPosition
            );
        } else {
            OperationLogger.result(
                    "InputMenuItem",
                    "delete",
                    "nothing deleted: cursor at end"
            );
        }
    }

    public String getDisplayText(boolean showCursor) {
        if (!showCursor) {
            return getTitle() + ": " + value;
        }

        String text = value.toString();
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
        OperationLogger.operation(
                "InputMenuItem",
                "execute",
                "InputMenuItem does not execute an action"
        );
    }
}