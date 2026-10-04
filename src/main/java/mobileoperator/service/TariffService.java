package mobileoperator.service;

import mobileoperator.model.MobileOperator;
import mobileoperator.model.Tariff;

import java.util.Comparator;
import java.util.List;

public class TariffService {

    public int countAllClients(List<MobileOperator> operators) {
        validateOperators(operators);

        return operators.stream()
                .mapToInt(MobileOperator::getClientCount)
                .sum();
    }

    public List<Tariff> getAllTariffs(List<MobileOperator> operators) {
        validateOperators(operators);

        return operators.stream()
                .flatMap(operator -> operator.getTariffs().stream())
                .toList();
    }

    public List<Tariff> sortByMonthlyFee(
            List<MobileOperator> operators
    ) {
        return sortByParameter(
                getAllTariffs(operators),
                TariffSortParameter.MONTHLY_FEE,
                true
        );
    }

    public List<Tariff> sortOperatorTariffsByMonthlyFee(
            MobileOperator operator
    ) {
        if (operator == null) {
            throw new IllegalArgumentException(
                    "Оператор не може бути null."
            );
        }

        return sortByParameter(
                operator.getTariffs(),
                TariffSortParameter.MONTHLY_FEE,
                true
        );
    }

    public List<Tariff> sortByParameter(
            List<Tariff> tariffs,
            TariffSortParameter parameter,
            boolean ascending
    ) {
        if (tariffs == null) {
            throw new IllegalArgumentException(
                    "Список тарифів не може бути null."
            );
        }

        if (tariffs.stream().anyMatch(tariff -> tariff == null)) {
            throw new IllegalArgumentException(
                    "Список тарифів не може містити null."
            );
        }

        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Параметр сортування не може бути null."
            );
        }

        Comparator<Tariff> comparator =
                Comparator.comparingDouble(
                        tariff -> getParameterValue(
                                tariff,
                                parameter
                        )
                );

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return tariffs.stream()
                .sorted(comparator)
                .toList();
    }

    public List<Tariff> filterByRange(
            List<Tariff> tariffs,
            TariffSortParameter parameter,
            double min,
            double max
    ) {
        if (tariffs == null) {
            throw new IllegalArgumentException(
                    "Список тарифів не може бути null."
            );
        }

        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Параметр не може бути null."
            );
        }

        if (min < 0 || max < 0) {
            throw new IllegalArgumentException(
                    "Значення діапазону не можуть бути від'ємними."
            );
        }

        if (min > max) {
            throw new IllegalArgumentException(
                    "Мінімальне значення не може бути більшим за максимальне."
            );
        }

        return tariffs.stream()
                .filter(
                        tariff -> {
                            double value =
                                    getParameterValue(
                                            tariff,
                                            parameter
                                    );

                            return value >= min
                                    && value <= max;
                        }
                )
                .toList();
    }

    public List<Tariff> sortAndFilter(
            List<Tariff> tariffs,
            TariffSortParameter parameter,
            double min,
            double max,
            boolean ascending
    ) {
        List<Tariff> filtered =
                filterByRange(
                        tariffs,
                        parameter,
                        min,
                        max
                );

        return sortByParameter(
                filtered,
                parameter,
                ascending
        );
    }

    private double getParameterValue(
            Tariff tariff,
            TariffSortParameter parameter
    ) {
        return switch (parameter) {
            case MONTHLY_FEE -> tariff.getMonthlyFee();
            case MINUTES -> tariff.getMinutes();
            case SMS -> tariff.getSms();
            case INTERNET_GB -> tariff.getInternetGb();
        };
    }

    private void validateOperators(
            List<MobileOperator> operators
    ) {
        if (operators == null) {
            throw new IllegalArgumentException(
                    "Список операторів не може бути null."
            );
        }

        if (operators.stream().anyMatch(operator -> operator == null)) {
            throw new IllegalArgumentException(
                    "Список не може містити null."
            );
        }
    }
}