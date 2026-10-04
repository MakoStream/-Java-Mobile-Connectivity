package mobileoperator.service;

public enum TariffSortParameter {

    MONTHLY_FEE("Абонентська плата"),
    MINUTES("Хвилини"),
    SMS("SMS"),
    INTERNET_GB("Інтернет");

    private final String displayName;

    TariffSortParameter(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}