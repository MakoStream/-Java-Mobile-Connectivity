package mobileoperator.menu.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckboxMenuItemTest {

    @Test
    void shouldHaveInitialState() {
        CheckboxMenuItem item =
                new CheckboxMenuItem(
                        "Лише доступні",
                        true
                );

        assertTrue(item.isChecked());
    }

    @Test
    void shouldToggleState() {
        CheckboxMenuItem item =
                new CheckboxMenuItem(
                        "Лише доступні",
                        false
                );

        assertFalse(item.isChecked());

        item.toggle();

        assertTrue(item.isChecked());

        item.toggle();

        assertFalse(item.isChecked());
    }

    @Test
    void executeShouldToggleCheckbox() {
        CheckboxMenuItem item =
                new CheckboxMenuItem(
                        "Лише доступні",
                        false
                );

        item.execute();

        assertTrue(item.isChecked());
    }
}