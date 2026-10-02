package mobileoperator.menu.items;

import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestLogger.class)
class CheckboxMenuItemTest {

    @Test
    void shouldHaveInitialState() {
        TestLogger.operation("Створення CheckboxMenuItem з початковим станом checked=true");

        CheckboxMenuItem item =
                new CheckboxMenuItem(
                        "Лише доступні",
                        true
                );

        TestLogger.state("item.isChecked()", item.isChecked());
        TestLogger.assertion("Початковий стан checkbox", true, item.isChecked());

        assertTrue(item.isChecked());
    }

    @Test
    void shouldToggleState() {
        TestLogger.operation("Створення CheckboxMenuItem з початковим станом checked=false");

        CheckboxMenuItem item =
                new CheckboxMenuItem(
                        "Лише доступні",
                        false
                );

        TestLogger.state("Початковий стан", item.isChecked());
        TestLogger.assertion("Початковий стан checkbox", false, item.isChecked());

        assertFalse(item.isChecked());

        TestLogger.operation("Виклик toggle()");
        item.toggle();

        TestLogger.state("Стан після першого toggle()", item.isChecked());
        TestLogger.assertion("Стан після першого toggle()", true, item.isChecked());

        assertTrue(item.isChecked());

        TestLogger.operation("Виклик toggle() повторно");
        item.toggle();

        TestLogger.state("Стан після другого toggle()", item.isChecked());
        TestLogger.assertion("Стан після другого toggle()", false, item.isChecked());

        assertFalse(item.isChecked());
    }

    @Test
    void executeShouldToggleCheckbox() {
        TestLogger.operation("Створення CheckboxMenuItem з початковим станом checked=false");

        CheckboxMenuItem item =
                new CheckboxMenuItem(
                        "Лише доступні",
                        false
                );

        TestLogger.state("Стан до execute()", item.isChecked());
        TestLogger.assertion("Стан до execute()", false, item.isChecked());

        assertFalse(item.isChecked());

        TestLogger.operation("Виклик execute()");
        item.execute();

        TestLogger.state("Стан після execute()", item.isChecked());
        TestLogger.assertion("Стан після execute()", true, item.isChecked());

        assertTrue(item.isChecked());
    }
}