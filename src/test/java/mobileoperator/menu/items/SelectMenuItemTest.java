package mobileoperator.menu.items;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectMenuItemTest {

    @Test
    void shouldSelectInitialValue() {
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

        item.selectNext();

        assertEquals(
                "Vodafone",
                item.getSelectedValue()
        );
    }

    @Test
    void shouldWrapAroundWhenSelectingNext() {
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

        item.selectNext();

        assertEquals(
                "Kyivstar",
                item.getSelectedValue()
        );
    }

    @Test
    void shouldSelectPreviousValue() {
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

        item.selectPrevious();

        assertEquals(
                "Kyivstar",
                item.getSelectedValue()
        );
    }

    @Test
    void shouldWrapAroundWhenSelectingPrevious() {
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

        item.selectPrevious();

        assertEquals(
                "lifecell",
                item.getSelectedValue()
        );
    }
}