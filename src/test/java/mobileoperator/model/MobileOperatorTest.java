package mobileoperator.model;

import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(TestLogger.class)
class MobileOperatorTest {

    @Test
    void shouldCreateOperator() {
        TestLogger.operation("Створення MobileOperator");

        MobileOperator operator =
                new MobileOperator(
                        1,
                        "Kyivstar"
                );

        TestLogger.state("operator.id", operator.getId());
        TestLogger.state("operator.name", operator.getName());
        TestLogger.assertion("ID компанії", 1, operator.getId());
        TestLogger.assertion("Назва компанії", "Kyivstar", operator.getName());

        assertEquals(1, operator.getId());
        assertEquals("Kyivstar", operator.getName());
    }

    @Test
    void shouldAddTariff() {
        TestLogger.operation("Створення MobileOperator");

        MobileOperator operator =
                new MobileOperator(
                        1,
                        "Kyivstar"
                );

        Tariff tariff =
                new Tariff(
                        1,
                        "LOVE UA",
                        350,
                        1200,
                        100,
                        25
                );

        TestLogger.operation("Додавання тарифу до компанії");
        operator.addTariff(tariff);

        TestLogger.state("Кількість тарифів", operator.getTariffs().size());
        TestLogger.assertion("Кількість тарифів після додавання", 1, operator.getTariffs().size());

        assertEquals(1, operator.getTariffs().size());
        assertEquals(tariff, operator.getTariffs().get(0));
    }

    @Test
    void shouldRejectDuplicateTariffId() {
        TestLogger.operation("Створення MobileOperator");

        MobileOperator operator =
                new MobileOperator(
                        1,
                        "Kyivstar"
                );

        Tariff firstTariff =
                new Tariff(
                        1,
                        "LOVE UA",
                        350,
                        1200,
                        100,
                        25
                );

        Tariff secondTariff =
                new Tariff(
                        1,
                        "LOVE UA PLUS",
                        500,
                        2000,
                        200,
                        50
                );

        operator.addTariff(firstTariff);

        TestLogger.operation("Спроба додати тариф з дубльованим ID");

        assertThrows(
                IllegalArgumentException.class,
                () -> operator.addTariff(secondTariff)
        );

        TestLogger.assertion(
                "Дубльований ID тарифу має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldAddClient() {
        TestLogger.operation("Створення MobileOperator");

        MobileOperator operator =
                new MobileOperator(
                        1,
                        "Kyivstar"
                );

        Client client =
                new Client(
                        1,
                        "Олександр",
                        "Петренко",
                        "+380671234567",
                        1
                );

        TestLogger.operation("Додавання клієнта до компанії");
        operator.addClient(client);

        TestLogger.state("Кількість клієнтів", operator.getClients().size());
        TestLogger.assertion("Кількість клієнтів після додавання", 1, operator.getClients().size());

        assertEquals(1, operator.getClients().size());
        assertEquals(client, operator.getClients().get(0));
    }

    @Test
    void shouldRejectDuplicateClientId() {
        TestLogger.operation("Створення MobileOperator");

        MobileOperator operator =
                new MobileOperator(
                        1,
                        "Kyivstar"
                );

        Client firstClient =
                new Client(
                        1,
                        "Олександр",
                        "Петренко",
                        "+380671234567",
                        1
                );

        Client secondClient =
                new Client(
                        1,
                        "Іван",
                        "Іваненко",
                        "+380671234568",
                        1
                );

        operator.addClient(firstClient);

        TestLogger.operation("Спроба додати клієнта з дубльованим ID");

        assertThrows(
                IllegalArgumentException.class,
                () -> operator.addClient(secondClient)
        );

        TestLogger.assertion(
                "Дубльований ID клієнта має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldReturnCorrectClientCount() {
        TestLogger.operation("Створення MobileOperator");

        MobileOperator operator =
                new MobileOperator(
                        1,
                        "Kyivstar"
                );

        operator.addClient(
                new Client(
                        1,
                        "Олександр",
                        "Петренко",
                        "+380671234567",
                        1
                )
        );

        operator.addClient(
                new Client(
                        2,
                        "Іван",
                        "Іваненко",
                        "+380671234568",
                        1
                )
        );

        operator.addClient(
                new Client(
                        3,
                        "Марія",
                        "Коваленко",
                        "+380671234569",
                        1
                )
        );

        TestLogger.state("Кількість клієнтів", operator.getClientCount());
        TestLogger.assertion("Загальна кількість клієнтів", 3, operator.getClientCount());

        assertEquals(3, operator.getClientCount());
    }

    @Test
    void shouldFindTariffById() {
        TestLogger.operation("Створення MobileOperator");

        MobileOperator operator =
                new MobileOperator(
                        1,
                        "Kyivstar"
                );

        Tariff tariff =
                new Tariff(
                        1,
                        "LOVE UA",
                        350,
                        1200,
                        100,
                        25
                );

        operator.addTariff(tariff);

        TestLogger.operation("Пошук тарифу за ID=1");

        Tariff result = operator.findTariffById(1);

        TestLogger.state("Знайдений тариф", result);
        TestLogger.assertion("Знайдений тариф", tariff, result);

        assertEquals(tariff, result);
    }

    @Test
    void shouldReturnNullWhenTariffNotFound() {
        TestLogger.operation("Створення MobileOperator");

        MobileOperator operator =
                new MobileOperator(
                        1,
                        "Kyivstar"
                );

        TestLogger.operation("Пошук неіснуючого тарифу за ID=999");

        Tariff result = operator.findTariffById(999);

        TestLogger.state("Результат пошуку", result);
        TestLogger.assertion("Результат пошуку неіснуючого тарифу", null, result);

        assertNull(result);
    }

    @Test
    void shouldRejectInvalidOperatorId() {
        TestLogger.operation("Створення MobileOperator з ID=0");

        assertThrows(
                IllegalArgumentException.class,
                () -> new MobileOperator(0, "Kyivstar")
        );

        TestLogger.assertion(
                "ID=0 має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }

    @Test
    void shouldRejectEmptyOperatorName() {
        TestLogger.operation("Створення MobileOperator з порожньою назвою");

        assertThrows(
                IllegalArgumentException.class,
                () -> new MobileOperator(1, "")
        );

        TestLogger.assertion(
                "Порожня назва має викликати IllegalArgumentException",
                IllegalArgumentException.class,
                IllegalArgumentException.class
        );
    }
}