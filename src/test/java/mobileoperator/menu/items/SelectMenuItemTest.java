package mobileoperator.menu.items;

import java.util.List;

import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestLogger.class)
class SelectMenuItemTest {

    @Test
    void shouldSelectInitialValue() {
        TestLogger.operation("Створення SelectMenuItem з defaultIndex=0");

        SelectMenuItem item =
                new SelectMenuItem(
                        "Компанія",
                        List.of(
                                "Kyivstar",
                                "Vodafone",
                                "lifecell"
                        ),
                        0
                );

        TestLogger.state("selectedIndex", item.getSelectedIndex());
        TestLogger.state("selectedValue", item.getSelectedValue());
        TestLogger.assertion("Початкове значення", "Kyivstar", item.getSelectedValue());
        TestLogger.assertion("Початковий індекс", 0, item.getSelectedIndex());

        assertEquals(
                "Kyivstar",
                item.getSelectedValue()
        );

        assertEquals(
                0,
                item.getSelectedIndex()
        );
    }

    @Test
    void shouldSelectNextValue() {
        TestLogger.operation("Створення SelectMenuItem з defaultIndex=0");

        SelectMenuItem item =
                new SelectMenuItem(
                        "Компанія",
                        List.of(
                                "Kyivstar",
                                "Vodafone",
                                "lifecell"
                        ),
                        0
                );

        TestLogger.operation("Виклик selectNext()");
        item.selectNext();

        TestLogger.state("selectedIndex", item.getSelectedIndex());
        TestLogger.state("selectedValue", item.getSelectedValue());
        TestLogger.assertion("Значення після selectNext()", "Vodafone", item.getSelectedValue());

        assertEquals(
                "Vodafone",
                item.getSelectedValue()
        );
    }

    @Test
    void shouldWrapAroundWhenSelectingNext() {
        TestLogger.operation("Створення SelectMenuItem з defaultIndex=2");

        SelectMenuItem item =
                new SelectMenuItem(
                        "Компанія",
                        List.of(
                                "Kyivstar",
                                "Vodafone",
                                "lifecell"
                        ),
                        2
                );

        TestLogger.state("Початковий selectedIndex", item.getSelectedIndex());
        TestLogger.state("Початковий selectedValue", item.getSelectedValue());

        TestLogger.operation("Виклик selectNext() з останнього елемента");
        item.selectNext();

        TestLogger.state("selectedIndex після selectNext()", item.getSelectedIndex());
        TestLogger.state("selectedValue після selectNext()", item.getSelectedValue());
        TestLogger.assertion("Перехід з останнього на перший", "Kyivstar", item.getSelectedValue());

        assertEquals(
                "Kyivstar",
                item.getSelectedValue()
        );
    }

    @Test
    void shouldSelectPreviousValue() {
        TestLogger.operation("Створення SelectMenuItem з defaultIndex=1");

        SelectMenuItem item =
                new SelectMenuItem(
                        "Компанія",
                        List.of(
                                "Kyivstar",
                                "Vodafone",
                                "lifecell"
                        ),
                        1
                );

        TestLogger.state("Початковий selectedIndex", item.getSelectedIndex());
        TestLogger.state("Початковий selectedValue", item.getSelectedValue());

        TestLogger.operation("Виклик selectPrevious()");
        item.selectPrevious();

        TestLogger.state("selectedIndex", item.getSelectedIndex());
        TestLogger.state("selectedValue", item.getSelectedValue());
        TestLogger.assertion("Значення після selectPrevious()", "Kyivstar", item.getSelectedValue());

        assertEquals(
                "Kyivstar",
                item.getSelectedValue()
        );
    }

    @Test
    void shouldWrapAroundWhenSelectingPrevious() {
        TestLogger.operation("Створення SelectMenuItem з defaultIndex=0");

        SelectMenuItem item =
                new SelectMenuItem(
                        "Компанія",
                        List.of(
                                "Kyivstar",
                                "Vodafone",
                                "lifecell"
                        ),
                        0
                );

        TestLogger.state("Початковий selectedIndex", item.getSelectedIndex());
        TestLogger.state("Початковий selectedValue", item.getSelectedValue());

        TestLogger.operation("Виклик selectPrevious() з першого елемента");
        item.selectPrevious();

        TestLogger.state("selectedIndex після selectPrevious()", item.getSelectedIndex());
        TestLogger.state("selectedValue після selectPrevious()", item.getSelectedValue());
        TestLogger.assertion("Перехід з першого на останній", "lifecell", item.getSelectedValue());

        assertEquals(
                "lifecell",
                item.getSelectedValue()
        );
    }
}