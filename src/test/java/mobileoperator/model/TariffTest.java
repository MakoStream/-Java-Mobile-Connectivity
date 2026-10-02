package mobileoperator.model;

import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(TestLogger.class)
class TariffTest {

    @Test
    void shouldCreateTariff() {
        TestLogger.operation("Створення тарифу LOVE UA");

        Tariff tariff =
                new Tariff(
                        1,
                        "LOVE UA",
                        350,
                        1200,
                        100,
                        25
                );

        TestLogger.state("tariff.id", tariff.getId());
        TestLogger.state("tariff.name", tariff.getName());
        TestLogger.state("tariff.monthlyFee", tariff.getMonthlyFee());
        TestLogger.state("tariff.minutes", tariff.getMinutes());
        TestLogger.state("tariff.sms", tariff.getSms());
        TestLogger.state("tariff.internetGb", tariff.getInternetGb());

        assertEquals(1, tariff.getId());
        assertEquals("LOVE UA", tariff.getName());
        assertEquals(350, tariff.getMonthlyFee());
        assertEquals(1200, tariff.getMinutes());
        assertEquals(100, tariff.getSms());
        assertEquals(25, tariff.getInternetGb());

        TestLogger.assertion("ID тарифу", 1, tariff.getId());
        TestLogger.assertion("Назва тарифу", "LOVE UA", tariff.getName());
        TestLogger.assertion("Абонентська плата", 350, tariff.getMonthlyFee());
        TestLogger.assertion("Кількість хвилин", 1200, tariff.getMinutes());
        TestLogger.assertion("Кількість SMS", 100, tariff.getSms());
        TestLogger.assertion("Інтернет", 25, tariff.getInternetGb());
    }

    @Test
    void shouldRejectInvalidId() {
        TestLogger.operation("Створення тарифу з ID=0");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tariff(
                        0,
                        "LOVE UA",
                        350,
                        1200,
                        100,
                        25
                )
        );

        TestLogger.assertion(
                "ID=0 має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectEmptyName() {
        TestLogger.operation("Створення тарифу з порожньою назвою");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tariff(
                        1,
                        "",
                        350,
                        1200,
                        100,
                        25
                )
        );

        TestLogger.assertion(
                "Порожня назва має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectNegativeMonthlyFee() {
        TestLogger.operation("Створення тарифу з від'ємною абонентською платою");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tariff(
                        1,
                        "LOVE UA",
                        -350,
                        1200,
                        100,
                        25
                )
        );

        TestLogger.assertion(
                "Від'ємна абонентська плата має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectNegativeMinutes() {
        TestLogger.operation("Створення тарифу з від'ємною кількістю хвилин");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tariff(
                        1,
                        "LOVE UA",
                        350,
                        -1200,
                        100,
                        25
                )
        );

        TestLogger.assertion(
                "Від'ємна кількість хвилин має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectNegativeSms() {
        TestLogger.operation("Створення тарифу з від'ємною кількістю SMS");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tariff(
                        1,
                        "LOVE UA",
                        350,
                        1200,
                        -100,
                        25
                )
        );

        TestLogger.assertion(
                "Від'ємна кількість SMS має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectNegativeInternet() {
        TestLogger.operation("Створення тарифу з від'ємним обсягом інтернету");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tariff(
                        1,
                        "LOVE UA",
                        350,
                        1200,
                        100,
                        -25
                )
        );

        TestLogger.assertion(
                "Від'ємний обсяг інтернету має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }
}