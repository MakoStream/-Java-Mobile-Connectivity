package mobileoperator.model;

public class Client {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final int tariffId;

    public Client(
            int id,
            String firstName,
            String lastName,
            String phoneNumber,
            int tariffId
    ) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "ID клієнта має бути більшим за 0."
            );
        }

        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException(
                    "Ім'я клієнта не може бути порожнім."
            );
        }

        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException(
                    "Прізвище клієнта не може бути порожнім."
            );
        }

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Номер телефону не може бути порожнім."
            );
        }

        if (tariffId <= 0) {
            throw new IllegalArgumentException(
                    "ID тарифу має бути більшим за 0."
            );
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.tariffId = tariffId;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getTariffId() {
        return tariffId;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName()
                + " — "
                + phoneNumber;
    }
}