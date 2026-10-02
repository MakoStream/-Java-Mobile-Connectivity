package mobileoperator.menu.items;

import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputMenuItemTest {

    private static final String TEST_CLASS =
            "InputMenuItemTest";

    @BeforeEach
    void beforeEach() {
        TestLogger.operation(
                TEST_CLASS,
                "beforeEach",
                "Підготовка до тесту"
        );
    }

    @Test
    void shouldStoreInitialValue() {

        String testMethod =
                "shouldStoreInitialValue";

        TestLogger.startTest(
                TEST_CLASS,
                testMethod
        );

        String title = "Мінімальна ціна";
        String initialValue = "350";
        boolean numericOnly = true;
        int maxLength = 10;

        TestLogger.state(
                TEST_CLASS,
                "title",
                title
        );

        TestLogger.state(
                TEST_CLASS,
                "initialValue",
                initialValue
        );

        TestLogger.state(
                TEST_CLASS,
                "numericOnly",
                numericOnly
        );

        TestLogger.state(
                TEST_CLASS,
                "maxLength",
                maxLength
        );

        InputMenuItem item =
                new InputMenuItem(
                        title,
                        initialValue,
                        numericOnly,
                        maxLength
                );

        TestLogger.operation(
                TEST_CLASS,
                testMethod,
                "Створено InputMenuItem"
        );

        TestLogger.state(
                TEST_CLASS,
                "item.value",
                item.getValue()
        );

        TestLogger.state(
                TEST_CLASS,
                "item.cursorPosition",
                item.getCursorPosition()
        );

        String actualValue = item.getValue();

        TestLogger.assertion(
                "Перевірка початкового значення",
                "350",
                actualValue
        );

        assertEquals(
                "350",
                actualValue
        );

        int actualCursorPosition =
                item.getCursorPosition();

        TestLogger.assertion(
                "Перевірка позиції курсора",
                3,
                actualCursorPosition
        );

        assertEquals(
                3,
                actualCursorPosition
        );

        TestLogger.endTest(
                TEST_CLASS,
                testMethod
        );
    }

    @Test
    void shouldInsertCharacter() {

        String testMethod =
                "shouldInsertCharacter";

        TestLogger.startTest(
                TEST_CLASS,
                testMethod
        );

        InputMenuItem item =
                new InputMenuItem(
                        "Ціна",
                        "",
                        true,
                        10
                );

        TestLogger.state(
                TEST_CLASS,
                "initialValue",
                item.getValue()
        );

        TestLogger.state(
                TEST_CLASS,
                "initialCursorPosition",
                item.getCursorPosition()
        );

        boolean result;

        result = item.insert('3');

        TestLogger.operation(
                TEST_CLASS,
                testMethod,
                "Вставка символу '3'"
        );

        TestLogger.state(
                TEST_CLASS,
                "insertResult",
                result
        );

        TestLogger.state(
                TEST_CLASS,
                "valueAfter3",
                item.getValue()
        );

        TestLogger.state(
                TEST_CLASS,
                "cursorAfter3",
                item.getCursorPosition()
        );

        assertTrue(result);

        result = item.insert('5');

        TestLogger.operation(
                TEST_CLASS,
                testMethod,
                "Вставка символу '5'"
        );

        TestLogger.state(
                TEST_CLASS,
                "valueAfter5",
                item.getValue()
        );

        TestLogger.state(
                TEST_CLASS,
                "cursorAfter5",
                item.getCursorPosition()
        );

        assertTrue(result);

        result = item.insert('0');

        TestLogger.operation(
                TEST_CLASS,
                testMethod,
                "Вставка символу '0'"
        );

        TestLogger.state(
                TEST_CLASS,
                "valueAfter0",
                item.getValue()
        );

        TestLogger.state(
                TEST_CLASS,
                "cursorAfter0",
                item.getCursorPosition()
        );

        assertTrue(result);

        TestLogger.assertion(
                "Перевірка кінцевого значення",
                "350",
                item.getValue()
        );

        assertEquals(
                "350",
                item.getValue()
        );

        TestLogger.endTest(
                TEST_CLASS,
                testMethod
        );
    }
}