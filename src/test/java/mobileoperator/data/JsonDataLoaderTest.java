package mobileoperator.data;

import mobileoperator.model.MobileOperator;
import mobileoperator.testing.TestLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(TestLogger.class)
class JsonDataLoaderTest {

    @TempDir
    Path tempDirectory;

    private final JsonDataLoader loader = new JsonDataLoader();

    private void writeJsonFiles(
            String tariffsJson,
            String clientsJson
    ) throws IOException {
        Files.writeString(
                tempDirectory.resolve("tariffs.json"),
                tariffsJson
        );

        Files.writeString(
                tempDirectory.resolve("clients.json"),
                clientsJson
        );
    }

    @Test
    void shouldLoadOperatorFromJson() throws IOException {
        TestLogger.operation("Завантаження оператора з JSON");

        writeJsonFiles(
                """
                {
                  "tariffs": [
                    {
                      "id": 1,
                      "name": "LOVE UA",
                      "monthlyFee": 350,
                      "minutes": 1200,
                      "sms": 100,
                      "internetGb": 25
                    }
                  ]
                }
                """,
                """
                {
                  "clients": [
                    {
                      "id": 1,
                      "firstName": "Олександр",
                      "lastName": "Петренко",
                      "phoneNumber": "+380671234567",
                      "tariffId": 1
                    }
                  ]
                }
                """
        );

        MobileOperator operator = loader.loadOperator(
                1,
                "Kyivstar",
                tempDirectory
        );

        TestLogger.state("operator.name", operator.getName());
        TestLogger.state("tariffCount", operator.getTariffs().size());
        TestLogger.state("clientCount", operator.getClientCount());

        TestLogger.assertion("Назва оператора", "Kyivstar", operator.getName());
        TestLogger.assertion("Кількість тарифів", 1, operator.getTariffs().size());
        TestLogger.assertion("Кількість клієнтів", 1, operator.getClientCount());

        assertEquals("Kyivstar", operator.getName());
        assertEquals(1, operator.getTariffs().size());
        assertEquals(1, operator.getClientCount());
        assertEquals("LOVE UA", operator.getTariffs().get(0).getName());
        assertEquals(1, operator.getClients().get(0).getTariffId());
    }

    @Test
    void shouldRejectClientWithUnknownTariff() throws IOException {
        TestLogger.operation("Перевірка посилання на неіснуючий тариф");

        writeJsonFiles(
                """
                {
                  "tariffs": [
                    {
                      "id": 1,
                      "name": "LOVE UA",
                      "monthlyFee": 350,
                      "minutes": 1200,
                      "sms": 100,
                      "internetGb": 25
                    }
                  ]
                }
                """,
                """
                {
                  "clients": [
                    {
                      "id": 1,
                      "firstName": "Олександр",
                      "lastName": "Петренко",
                      "phoneNumber": "+380671234567",
                      "tariffId": 999
                    }
                  ]
                }
                """
        );

        IOException exception = assertThrows(
                IOException.class,
                () -> loader.loadOperator(1, "Kyivstar", tempDirectory)
        );

        TestLogger.state("Помилка", exception.getMessage());
        TestLogger.assertion(
                "Повідомлення про неіснуючий тариф",
                true,
                exception.getMessage().contains("неіснуючий тариф")
        );
    }

    @Test
    void shouldRejectInvalidTariffData() throws IOException {
        TestLogger.operation("Перевірка від'ємної абонентської плати");

        writeJsonFiles(
                """
                {
                  "tariffs": [
                    {
                      "id": 1,
                      "name": "LOVE UA",
                      "monthlyFee": -350,
                      "minutes": 1200,
                      "sms": 100,
                      "internetGb": 25
                    }
                  ]
                }
                """,
                """
                {
                  "clients": []
                }
                """
        );

        TestLogger.operation("Очікується IllegalArgumentException");

        assertThrows(
                IllegalArgumentException.class,
                () -> loader.loadOperator(1, "Kyivstar", tempDirectory)
        );
    }

    @Test
    void shouldRejectInvalidJsonStructure() throws IOException {
        TestLogger.operation("Перевірка відсутнього масиву tariffs");

        writeJsonFiles(
                """
                {
                  "plans": []
                }
                """,
                """
                {
                  "clients": []
                }
                """
        );

        IOException exception = assertThrows(
                IOException.class,
                () -> loader.loadOperator(1, "Kyivstar", tempDirectory)
        );

        TestLogger.state("Помилка", exception.getMessage());
        TestLogger.assertion(
                "Повідомлення про неправильну структуру",
                true,
                exception.getMessage().contains("масив tariffs")
        );
    }

    @Test
    void shouldRejectMissingJsonFile() {
        TestLogger.operation("Перевірка відсутніх JSON-файлів");

        assertThrows(
                IOException.class,
                () -> loader.loadOperator(1, "Kyivstar", tempDirectory)
        );
    }

    @Test
    void shouldRejectNullDirectory() {
        TestLogger.operation("Перевірка null замість шляху до папки");

        assertThrows(
                IllegalArgumentException.class,
                () -> loader.loadOperator(1, "Kyivstar", null)
        );
    }
}