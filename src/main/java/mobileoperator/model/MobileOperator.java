package mobileoperator.model;

import java.util.ArrayList;
import java.util.List;

public class MobileOperator {

    private final int id;
    private final String name;
    private final List<Tariff> tariffs;
    private final List<Client> clients;

    public MobileOperator(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "ID компанії має бути більшим за 0."
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Назва компанії не може бути порожньою."
            );
        }

        this.id = id;
        this.name = name;
        this.tariffs = new ArrayList<>();
        this.clients = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Tariff> getTariffs() {
        return List.copyOf(tariffs);
    }

    public List<Client> getClients() {
        return List.copyOf(clients);
    }

    public void addTariff(Tariff tariff) {
        if (tariff == null) {
            throw new IllegalArgumentException(
                    "Тариф не може бути null."
            );
        }

        if (tariffs.stream().anyMatch(
                existingTariff -> existingTariff.getId() == tariff.getId()
        )) {
            throw new IllegalArgumentException(
                    "Тариф з таким ID вже існує."
            );
        }

        tariffs.add(tariff);
    }

    public void addClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException(
                    "Клієнт не може бути null."
            );
        }

        if (clients.stream().anyMatch(
                existingClient -> existingClient.getId() == client.getId()
        )) {
            throw new IllegalArgumentException(
                    "Клієнт з таким ID вже існує."
            );
        }

        clients.add(client);
    }

    public int getClientCount() {
        return clients.size();
    }

    public Tariff findTariffById(int tariffId) {
        return tariffs.stream()
                .filter(tariff -> tariff.getId() == tariffId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return name;
    }
}