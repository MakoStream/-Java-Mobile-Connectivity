package mobileoperator.menu.items;

import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestLogger.class)
class InputMenuItemTest {

    @Test
    void shouldStoreInitialValue() {
        TestLogger.operation("Створення InputMenuItem");

        InputMenuItem item =
                new InputMenuItem(
                        "Мінімальна ціна",
                        "350",
                        true,
                        10
                );

        TestLogger.state("item.value", item.getValue());
        TestLogger.state("item.cursorPosition", item.getCursorPosition());

        TestLogger.assertion(
                "Перевірка початкового значення",
                "350",
                item.getValue()
        );

        assertEquals(
                "350",
                item.getValue()
        );

        TestLogger.assertion(
                "Перевірка початкової позиції курсора",
                3,
                item.getCursorPosition()
        );

        assertEquals(
                3,
                item.getCursorPosition()
        );
    }

    @Test
    void shouldInsertCharacter() {
        TestLogger.operation("Створення InputMenuItem");

        InputMenuItem item =
                new InputMenuItem(
                        "Ціна",
                        "",
                        true,
                        10
                );

        TestLogger.operation("Вставка символу '3'");
        boolean result1 = item.insert('3');
        TestLogger.state("insert('3') result", result1);
        TestLogger.state("value", item.getValue());

        TestLogger.operation("Вставка символу '5'");
        boolean result2 = item.insert('5');
        TestLogger.state("insert('5') result", result2);
        TestLogger.state("value", item.getValue());

        TestLogger.operation("Вставка символу '0'");
        boolean result3 = item.insert('0');
        TestLogger.state("insert('0') result", result3);
        TestLogger.state("value", item.getValue());

        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);

        TestLogger.assertion(
                "Перевірка результату вставки",
                "350",
                item.getValue()
        );

        assertEquals(
                "350",
                item.getValue()
        );
    }

    @Test
    void shouldRejectNonNumericCharacter() {
        TestLogger.operation("Створення InputMenuItem");

        InputMenuItem item =
                new InputMenuItem(
                        "Ціна",
                        "",
                        true,
                        10
                );

        TestLogger.operation("Спроба вставити нечисловий символ 'a'");

        boolean result = item.insert('a');

        TestLogger.state("insert('a') result", result);
        TestLogger.state("value", item.getValue());

        assertFalse(result);

        TestLogger.assertion(
                "Значення не повинно змінитися",
                "",
                item.getValue()
        );

        assertEquals(
                "",
                item.getValue()
        );
    }

    @Test
    void shouldRespectMaximumLength() {
        TestLogger.operation("Створення InputMenuItem з maxLength=3");

        InputMenuItem item =
                new InputMenuItem(
                        "Ціна",
                        "",
                        true,
                        3
                );

        TestLogger.operation("Вставка '1'");
        assertTrue(item.insert('1'));

        TestLogger.operation("Вставка '2'");
        assertTrue(item.insert('2'));

        TestLogger.operation("Вставка '3'");
        assertTrue(item.insert('3'));

        TestLogger.operation("Спроба вставити четвертий символ '4'");

        boolean result = item.insert('4');

        TestLogger.state("insert('4') result", result);
        TestLogger.state("value", item.getValue());

        assertFalse(result);

        TestLogger.assertion(
                "Перевірка максимальної довжини",
                "123",
                item.getValue()
        );

        assertEquals(
                "123",
                item.getValue()
        );
    }

    @Test
    void shouldMoveCursorAndDeleteCharacter() {
        TestLogger.operation("Створення InputMenuItem зі значенням 123");

        InputMenuItem item =
                new InputMenuItem(
                        "Ціна",
                        "123",
                        true,
                        10
                );

        TestLogger.state(
                "initial value",
                item.getValue()
        );

        TestLogger.state(
                "initial cursorPosition",
                item.getCursorPosition()
        );

        TestLogger.operation("Переміщення курсора вліво");
        item.moveCursorLeft();

        TestLogger.state(
                "cursorPosition after moveLeft",
                item.getCursorPosition()
        );

        TestLogger.operation("Видалення символу через backspace");
        item.backspace();

        TestLogger.state(
                "value after backspace",
                item.getValue()
        );

        TestLogger.state(
                "cursorPosition after backspace",
                item.getCursorPosition()
        );

        TestLogger.assertion(
                "Перевірка значення після видалення",
                "13",
                item.getValue()
        );

        assertEquals(
                "13",
                item.getValue()
        );
    }
}