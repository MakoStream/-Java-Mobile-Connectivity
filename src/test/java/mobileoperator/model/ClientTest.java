package mobileoperator.model;

import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(TestLogger.class)
class ClientTest {

    @Test
    void shouldCreateClient() {
        TestLogger.operation("Створення клієнта");

        Client client =
                new Client(
                        1,
                        "Олександр",
                        "Петренко",
                        "+380671234567",
                        1
                );

        TestLogger.state("client.id", client.getId());
        TestLogger.state("client.firstName", client.getFirstName());
        TestLogger.state("client.lastName", client.getLastName());
        TestLogger.state("client.phoneNumber", client.getPhoneNumber());
        TestLogger.state("client.tariffId", client.getTariffId());

        assertEquals(1, client.getId());
        assertEquals("Олександр", client.getFirstName());
        assertEquals("Петренко", client.getLastName());
        assertEquals("+380671234567", client.getPhoneNumber());
        assertEquals(1, client.getTariffId());

        TestLogger.assertion("ID клієнта", 1, client.getId());
        TestLogger.assertion("Ім'я клієнта", "Олександр", client.getFirstName());
        TestLogger.assertion("Прізвище клієнта", "Петренко", client.getLastName());
        TestLogger.assertion("Номер телефону", "+380671234567", client.getPhoneNumber());
        TestLogger.assertion("ID тарифу", 1, client.getTariffId());
    }

    @Test
    void shouldReturnFullName() {
        TestLogger.operation("Створення клієнта для перевірки повного імені");

        Client client =
                new Client(
                        1,
                        "Олександр",
                        "Петренко",
                        "+380671234567",
                        1
                );

        TestLogger.state("fullName", client.getFullName());
        TestLogger.assertion(
                "Повне ім'я клієнта",
                "Олександр Петренко",
                client.getFullName()
        );

        assertEquals(
                "Олександр Петренко",
                client.getFullName()
        );
    }

    @Test
    void shouldRejectInvalidId() {
        TestLogger.operation("Створення клієнта з ID=0");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Client(
                        0,
                        "Олександр",
                        "Петренко",
                        "+380671234567",
                        1
                )
        );

        TestLogger.assertion(
                "ID=0 має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectEmptyFirstName() {
        TestLogger.operation("Створення клієнта з порожнім ім'ям");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Client(
                        1,
                        "",
                        "Петренко",
                        "+380671234567",
                        1
                )
        );

        TestLogger.assertion(
                "Порожнє ім'я має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectEmptyLastName() {
        TestLogger.operation("Створення клієнта з порожнім прізвищем");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Client(
                        1,
                        "Олександр",
                        "",
                        "+380671234567",
                        1
                )
        );

        TestLogger.assertion(
                "Порожнє прізвище має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectEmptyPhoneNumber() {
        TestLogger.operation("Створення клієнта з порожнім номером телефону");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Client(
                        1,
                        "Олександр",
                        "Петренко",
                        "",
                        1
                )
        );

        TestLogger.assertion(
                "Порожній номер телефону має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectInvalidTariffId() {
        TestLogger.operation("Створення клієнта з tariffId=0");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Client(
                        1,
                        "Олександр",
                        "Петренко",
                        "+380671234567",
                        0
                )
        );

        TestLogger.assertion(
                "tariffId=0 має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }
}