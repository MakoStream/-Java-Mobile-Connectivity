package mobileoperator.menu;

import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(TestLogger.class)
class MenuResultTest {

    @Test
    void stayShouldCreateStayResult() {
        TestLogger.operation("Створення MenuResult.stay()");

        MenuResult result = MenuResult.stay();

        TestLogger.state("result.type", result.getType());
        TestLogger.state("result.nextMenu", result.getNextMenu());
        TestLogger.assertion("Тип результату", MenuResult.Type.STAY, result.getType());
        TestLogger.assertion("Наступне меню", null, result.getNextMenu());

        assertEquals(
                MenuResult.Type.STAY,
                result.getType()
        );

        assertNull(result.getNextMenu());
    }

    @Test
    void backShouldCreateBackResult() {
        TestLogger.operation("Створення MenuResult.back()");

        MenuResult result = MenuResult.back();

        TestLogger.state("result.type", result.getType());
        TestLogger.state("result.nextMenu", result.getNextMenu());
        TestLogger.assertion("Тип результату", MenuResult.Type.BACK, result.getType());
        TestLogger.assertion("Наступне меню", null, result.getNextMenu());

        assertEquals(
                MenuResult.Type.BACK,
                result.getType()
        );

        assertNull(result.getNextMenu());
    }

    @Test
    void exitShouldCreateExitResult() {
        TestLogger.operation("Створення MenuResult.exit()");

        MenuResult result = MenuResult.exit();

        TestLogger.state("result.type", result.getType());
        TestLogger.state("result.nextMenu", result.getNextMenu());
        TestLogger.assertion("Тип результату", MenuResult.Type.EXIT, result.getType());
        TestLogger.assertion("Наступне меню", null, result.getNextMenu());

        assertEquals(
                MenuResult.Type.EXIT,
                result.getType()
        );

        assertNull(result.getNextMenu());
    }

    @Test
    void nextShouldCreateNextResult() {
        TestLogger.operation("Створення тестового Menu для MenuResult.next()");

        Menu menu = new Menu(
                "Тестове меню",
                java.util.List.of()
        ) {
            @Override
            public MenuResult show(org.jline.terminal.Terminal terminal) {
                return MenuResult.stay();
            }
        };

        TestLogger.operation("Створення MenuResult.next(menu)");

        MenuResult result = MenuResult.next(menu);

        TestLogger.state("result.type", result.getType());
        TestLogger.state("result.nextMenu", result.getNextMenu().getTitle());
        TestLogger.assertion("Тип результату", MenuResult.Type.NEXT, result.getType());
        TestLogger.assertion("Наступне меню", "Тестове меню", result.getNextMenu().getTitle());

        assertEquals(
                MenuResult.Type.NEXT,
                result.getType()
        );

        assertEquals(
                menu,
                result.getNextMenu()
        );
    }

    @Test
    void nextShouldRejectNullMenu() {
        TestLogger.operation("Перевірка MenuResult.next(null)");

        TestLogger.assertion(
                "MenuResult.next(null) має викинути IllegalArgumentException",
                IllegalArgumentException.class,
                assertThrows(
                        IllegalArgumentException.class,
                        () -> MenuResult.next(null)
                ).getClass()
        );
    }
}