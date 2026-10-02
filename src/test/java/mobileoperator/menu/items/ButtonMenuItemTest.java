package mobileoperator.menu.items;

import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestLogger.class)
class ButtonMenuItemTest {

    @Test
    void shouldExecuteAction() {
        TestLogger.operation("Створення ButtonMenuItem");

        boolean[] executed = {false};

        TestLogger.state("executed до execute()", executed[0]);

        ButtonMenuItem item =
                new ButtonMenuItem(
                        "Тестова кнопка",
                        () -> executed[0] = true
                );

        TestLogger.operation("Виклик execute()");
        item.execute();

        TestLogger.state("executed після execute()", executed[0]);
        TestLogger.assertion("Дія кнопки виконана", true, executed[0]);

        assertTrue(executed[0]);
    }
}