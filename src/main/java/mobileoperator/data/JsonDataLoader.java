package mobileoperator.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mobileoperator.model.Client;
import mobileoperator.model.MobileOperator;
import mobileoperator.model.Tariff;

import java.io.IOException;
import java.nio.file.Path;

public class JsonDataLoader {

    private final ObjectMapper objectMapper;

    public JsonDataLoader() {
        this.objectMapper = new ObjectMapper();
    }

    public MobileOperator loadOperator(
            int id,
            String name,
            Path directory
    ) throws IOException {
        if (directory == null) {
            throw new IllegalArgumentException(
                    "Шлях до папки оператора не може бути null."
            );
        }

        Path tariffsPath = directory.resolve("tariffs.json");
        Path clientsPath = directory.resolve("clients.json");

        MobileOperator operator = new MobileOperator(id, name);

        JsonNode tariffsRoot = objectMapper.readTree(tariffsPath.toFile());
        JsonNode clientsRoot = objectMapper.readTree(clientsPath.toFile());

        JsonNode tariffs = tariffsRoot.get("tariffs");
        JsonNode clients = clientsRoot.get("clients");

        if (tariffs == null || !tariffs.isArray()) {
            throw new IOException(
                    "Файл tariffs.json повинен містити масив tariffs."
            );
        }

        if (clients == null || !clients.isArray()) {
            throw new IOException(
                    "Файл clients.json повинен містити масив clients."
            );
        }

        for (JsonNode node : tariffs) {
            Tariff tariff = new Tariff(
                    requiredInt(node, "id"),
                    requiredText(node, "name"),
                    requiredDouble(node, "monthlyFee"),
                    requiredInt(node, "minutes"),
                    requiredInt(node, "sms"),
                    requiredInt(node, "internetGb")
            );

            operator.addTariff(tariff);
        }

        for (JsonNode node : clients) {
            Client client = new Client(
                    requiredInt(node, "id"),
                    requiredText(node, "firstName"),
                    requiredText(node, "lastName"),
                    requiredText(node, "phoneNumber"),
                    requiredInt(node, "tariffId")
            );

            if (operator.findTariffById(client.getTariffId()) == null) {
                throw new IOException(
                        "Клієнт з ID "
                                + client.getId()
                                + " посилається на неіснуючий тариф "
                                + client.getTariffId()
                                + "."
                );
            }

            operator.addClient(client);
        }

        return operator;
    }

    private int requiredInt(JsonNode node, String field) throws IOException {
        JsonNode value = requiredField(node, field);

        if (!value.isIntegralNumber() || !value.canConvertToInt()) {
            throw new IOException(
                    "Поле " + field + " повинно бути цілим числом."
            );
        }

        return value.intValue();
    }

    private double requiredDouble(JsonNode node, String field) throws IOException {
        JsonNode value = requiredField(node, field);

        if (!value.isNumber()) {
            throw new IOException(
                    "Поле " + field + " повинно бути числом."
            );
        }

        double result = value.doubleValue();

        if (!Double.isFinite(result)) {
            throw new IOException(
                    "Поле " + field + " повинно містити скінченне число."
            );
        }

        return result;
    }

    private String requiredText(JsonNode node, String field) throws IOException {
        JsonNode value = requiredField(node, field);

        if (!value.isTextual() || value.asText().isBlank()) {
            throw new IOException(
                    "Поле " + field + " повинно містити непорожній текст."
            );
        }

        return value.asText();
    }

    private JsonNode requiredField(JsonNode node, String field) throws IOException {
        if (node == null || !node.isObject()) {
            throw new IOException(
                    "Елемент JSON повинен бути об'єктом."
            );
        }

        JsonNode value = node.get(field);

        if (value == null || value.isNull()) {
            throw new IOException(
                    "Відсутнє обов'язкове поле: " + field
            );
        }

        return value;
    }
}