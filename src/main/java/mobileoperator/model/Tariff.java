package mobileoperator.model;

public class Tariff {

    private final int id;
    private final String name;
    private final double monthlyFee;
    private final int minutes;
    private final int sms;
    private final int internetGb;

    public Tariff(
            int id,
            String name,
            double monthlyFee,
            int minutes,
            int sms,
            int internetGb
    ) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "ID тарифу має бути більшим за 0."
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Назва тарифу не може бути порожньою."
            );
        }

        if (monthlyFee < 0) {
            throw new IllegalArgumentException(
                    "Абонентська плата не може бути від'ємною."
            );
        }

        if (minutes < 0) {
            throw new IllegalArgumentException(
                    "Кількість хвилин не може бути від'ємною."
            );
        }

        if (sms < 0) {
            throw new IllegalArgumentException(
                    "Кількість SMS не може бути від'ємною."
            );
        }

        if (internetGb < 0) {
            throw new IllegalArgumentException(
                    "Кількість інтернету не може бути від'ємною."
            );
        }

        this.id = id;
        this.name = name;
        this.monthlyFee = monthlyFee;
        this.minutes = minutes;
        this.sms = sms;
        this.internetGb = internetGb;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSms() {
        return sms;
    }

    public int getInternetGb() {
        return internetGb;
    }

    @Override
    public String toString() {
        return name
                + " - "
                + monthlyFee
                + " грн/міс.";
    }
}