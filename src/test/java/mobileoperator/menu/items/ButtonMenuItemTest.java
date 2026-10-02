package mobileoperator.menu.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ButtonMenuItemTest {

    @Test
    void shouldExecuteAction() {

        boolean[] executed = {false};

        ButtonMenuItem item =
                new ButtonMenuItem(
                        "Тестова кнопка",
                        () -> executed[0] = true
                );

        item.execute();

        assertTrue(executed[0]);
    }
}