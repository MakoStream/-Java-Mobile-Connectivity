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
        return getAllTariffs(operators).stream()
                .sorted(
                        Comparator.comparingDouble(
                                Tariff::getMonthlyFee
                        )
                )
                .toList();
    }

    public List<Tariff> sortOperatorTariffsByMonthlyFee(
            MobileOperator operator
    ) {
        if (operator == null) {
            throw new IllegalArgumentException(
                    "Оператор не може бути null."
            );
        }

        return operator.getTariffs().stream()
                .sorted(
                        Comparator.comparingDouble(
                                Tariff::getMonthlyFee
                        )
                )
                .toList();
    }

    public List<Tariff> search(
            List<MobileOperator> operators,
            TariffCriteria criteria
    ) {
        validateOperators(operators);

        if (criteria == null) {
            throw new IllegalArgumentException(
                    "Критерії пошуку не можуть бути null."
            );
        }

        return getAllTariffs(operators).stream()
                .filter(criteria::matches)
                .toList();
    }

    private void validateOperators(List<MobileOperator> operators) {
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