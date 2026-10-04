package mobileoperator.service;

import mobileoperator.model.Tariff;

public record TariffCriteria(
        Double minMonthlyFee,
        Double maxMonthlyFee,
        Integer minMinutes,
        Integer maxMinutes,
        Integer minSms,
        Integer maxSms,
        Integer minInternetGb,
        Integer maxInternetGb
) {

    public TariffCriteria {
        validateRange(
                minMonthlyFee,
                maxMonthlyFee,
                "Абонентська плата"
        );

        validateRange(
                minMinutes,
                maxMinutes,
                "Хвилини"
        );

        validateRange(
                minSms,
                maxSms,
                "SMS"
        );

        validateRange(
                minInternetGb,
                maxInternetGb,
                "Інтернет"
        );
    }

    private static void validateRange(
            Number min,
            Number max,
            String parameter
    ) {
        if (min != null
                && max != null
                && min.doubleValue() > max.doubleValue()) {

            throw new IllegalArgumentException(
                    "Мінімальне значення параметра \""
                            + parameter
                            + "\" не може бути більшим за максимальне."
            );
        }

        if ((min != null && min.doubleValue() < 0)
                || (max != null && max.doubleValue() < 0)) {

            throw new IllegalArgumentException(
                    "Значення параметра \""
                            + parameter
                            + "\" не може бути від'ємним."
            );
        }
    }

    public boolean matches(Tariff tariff) {
        return inRange(
                tariff.getMonthlyFee(),
                minMonthlyFee,
                maxMonthlyFee
        )
                && inRange(
                tariff.getMinutes(),
                minMinutes,
                maxMinutes
        )
                && inRange(
                tariff.getSms(),
                minSms,
                maxSms
        )
                && inRange(
                tariff.getInternetGb(),
                minInternetGb,
                maxInternetGb
        );
    }

    private boolean inRange(
            Number value,
            Number min,
            Number max
    ) {
        double number = value.doubleValue();

        return (min == null || number >= min.doubleValue())
                && (max == null || number <= max.doubleValue());
    }
}