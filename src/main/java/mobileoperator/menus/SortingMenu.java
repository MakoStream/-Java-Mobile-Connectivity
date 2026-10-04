package mobileoperator.menus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jline.terminal.Terminal;

import mobileoperator.ApplicationData;
import mobileoperator.menu.Menu;
import mobileoperator.menu.MenuItem;
import mobileoperator.menu.MenuResult;
import mobileoperator.menu.items.ButtonMenuItem;
import mobileoperator.menu.items.InputMenuItem;
import mobileoperator.menu.items.SelectMenuItem;
import mobileoperator.model.MobileOperator;
import mobileoperator.model.Tariff;
import mobileoperator.service.TariffSortParameter;

public class SortingMenu extends Menu {

    private final ApplicationData applicationData;
    private final SelectMenuItem operatorSelect;
    private final SelectMenuItem parameterSelect;
    private final SelectMenuItem directionSelect;
    private final InputMenuItem minValueInput;
    private final InputMenuItem maxValueInput;

    public SortingMenu(ApplicationData applicationData) {
        super(
                "Сортування тарифів",
                new ArrayList<>()
        );

        this.applicationData = applicationData;

        List<String> operatorOptions =
                new ArrayList<>();

        operatorOptions.add("Усі оператори");

        for (MobileOperator operator :
                applicationData.getOperators()) {

            operatorOptions.add(
                    operator.getName()
            );
        }

        this.operatorSelect = new SelectMenuItem(
                "Оператор",
                operatorOptions,
                0
        );

        this.parameterSelect = new SelectMenuItem(
                "Параметр",
                List.of(
                        "Абонентська плата",
                        "Хвилини",
                        "SMS",
                        "Інтернет"
                ),
                0
        );

        this.directionSelect = new SelectMenuItem(
                "Напрямок",
                List.of(
                        "За зростанням",
                        "За спаданням"
                ),
                0
        );

        this.minValueInput = new InputMenuItem(
                "Мінімальне значення",
                "0",
                true,
                6
        );

        this.maxValueInput = new InputMenuItem(
                "Максимальне значення",
                "999999",
                true,
                6
        );

        getItems().add(operatorSelect);
        getItems().add(parameterSelect);
        getItems().add(directionSelect);
        getItems().add(minValueInput);
        getItems().add(maxValueInput);

        getItems().add(
                new ButtonMenuItem(
                        "Сортувати",
                        () -> {
                        }
                )
        );

        getItems().add(
                new ButtonMenuItem(
                        "Повернутися назад",
                        () -> {
                        }
                )
        );
    }

    @Override
    public MenuResult show(Terminal terminal) {

        try {
            int selectedIndex = 0;

            while (true) {

                selectedIndex = selectItem(
                        terminal,
                        selectedIndex
                );

                if (selectedIndex == -1) {
                    return MenuResult.back();
                }

                MenuItem selectedItem =
                        getItems().get(selectedIndex);

                if (selectedItem == operatorSelect) {
                    changeSelectValue(
                            terminal,
                            operatorSelect
                    );
                    clearTerminal(terminal);
                    continue;
                }

                if (selectedItem == parameterSelect) {
                    changeSelectValue(
                            terminal,
                            parameterSelect
                    );
                    clearTerminal(terminal);
                    continue;
                }

                if (selectedItem == directionSelect) {
                    changeSelectValue(
                            terminal,
                            directionSelect
                    );
                    clearTerminal(terminal);
                    continue;
                }

                if (selectedItem == minValueInput) {
                    editInput(
                            terminal,
                            minValueInput
                    );
                    clearTerminal(terminal);
                    continue;
                }

                if (selectedItem == maxValueInput) {
                    editInput(
                            terminal,
                            maxValueInput
                    );
                    continue;
                }

                if (selectedIndex == getItems().size() - 1) {
                    clearTerminal(terminal);
                    return MenuResult.back();
                }

                selectedItem.execute();

                clearTerminal(terminal);
                showSortedTariffs(terminal);
                clearTerminal(terminal);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Не вдалося працювати з терміналом.",
                    e
            );
        }
    }

    private void changeSelectValue(
            Terminal terminal,
            SelectMenuItem select
    ) throws IOException {

        while (true) {

            terminal.writer().println();
            terminal.writer().println(
                    select.getDisplayText()
            );
            terminal.writer().println(
                    "← → - змінити. Enter - підтвердити."
            );
            terminal.writer().flush();

            int key = terminal.reader().read();

            if (key == 13 || key == 10) {
                break;
            }

            if (key == 27) {
                break;
            }

            if (key == 68) {
                select.selectPrevious();
            }

            if (key == 67) {
                select.selectNext();
            }
        }
    }

    private void editInput(
            Terminal terminal,
            InputMenuItem input
    ) throws IOException {

        while (true) {

            terminal.writer().println();
            terminal.writer().println(
                    input.getDisplayText(true)
            );
            terminal.writer().println(
                    "Введіть значення. Enter - підтвердити."
            );
            terminal.writer().flush();

            int key = terminal.reader().read();

            if (key == 13 || key == 10) {
                break;
            }

            if (key == 8 || key == 127) {
                input.backspace();
                continue;
            }

            if (key == 27) {
                break;
            }

            if (Character.isDigit((char) key)) {
                input.insert((char) key);
            }
        }
    }

    private void showSortedTariffs(
            Terminal terminal
    ) throws IOException {

        double minValue;
        double maxValue;

        try {
            minValue = Double.parseDouble(
                    minValueInput.getValue()
            );

            maxValue = Double.parseDouble(
                    maxValueInput.getValue()
            );
        } catch (NumberFormatException e) {
            showMessage(
                    terminal,
                    "Значення повинні бути числами."
            );
            return;
        }

        if (minValue > maxValue) {
            showMessage(
                    terminal,
                    "Мінімальне значення не може бути більшим за максимальне."
            );
            return;
        }

        List<Tariff> tariffs =
                getSelectedTariffs();

        TariffSortParameter parameter =
                getSelectedParameter();

        boolean ascending =
                directionSelect.getSelectedIndex() == 0;

        List<Tariff> result =
                applicationData
                        .getTariffService()
                        .sortAndFilter(
                                tariffs,
                                parameter,
                                minValue,
                                maxValue,
                                ascending
                        );

        terminal.writer().println();
        terminal.writer().println(
                "Результат сортування"
        );
        terminal.writer().println();

        terminal.writer().println(
                "Оператор: "
                        + operatorSelect.getSelectedValue()
        );

        terminal.writer().println(
                "Параметр: "
                        + parameter.getDisplayName()
        );

        terminal.writer().println(
                "Діапазон: "
                        + minValue
                        + " - "
                        + maxValue
        );

        terminal.writer().println(
                "Напрямок: "
                        + directionSelect.getSelectedValue()
        );

        terminal.writer().println();

        if (result.isEmpty()) {
            terminal.writer().println(
                    "Тарифів за заданими параметрами не знайдено."
            );
        } else {

            for (Tariff tariff : result) {

                String operatorName =
                        findOperatorName(tariff);

                terminal.writer().println(
                        operatorName
                                + " | "
                                + tariff.getName()
                );

                terminal.writer().println(
                        "Абонентська плата: "
                                + tariff.getMonthlyFee()
                );

                terminal.writer().println(
                        "Хвилини: "
                                + tariff.getMinutes()
                );

                terminal.writer().println(
                        "SMS: "
                                + tariff.getSms()
                );

                terminal.writer().println(
                        "Інтернет: "
                                + tariff.getInternetGb()
                                + " GB"
                );

                terminal.writer().println();
            }
        }

        terminal.writer().println(
                "Натисніть будь-яку клавішу для повернення..."
        );
        terminal.writer().flush();

        terminal.reader().read();
    }

    private List<Tariff> getSelectedTariffs() {

        int selectedIndex =
                operatorSelect.getSelectedIndex();

        if (selectedIndex == 0) {
            return applicationData
                    .getTariffService()
                    .getAllTariffs(
                            applicationData.getOperators()
                    );
        }

        MobileOperator operator =
                applicationData
                        .getOperators()
                        .get(selectedIndex - 1);

        return operator.getTariffs();
    }

    private String findOperatorName(
            Tariff tariff
    ) {

        for (MobileOperator operator :
                applicationData.getOperators()) {

            if (operator.getTariffs().contains(tariff)) {
                return operator.getName();
            }
        }

        return "Невідомий оператор";
    }

    private TariffSortParameter getSelectedParameter() {

        return switch (
                parameterSelect.getSelectedIndex()
        ) {
            case 0 -> TariffSortParameter.MONTHLY_FEE;
            case 1 -> TariffSortParameter.MINUTES;
            case 2 -> TariffSortParameter.SMS;
            case 3 -> TariffSortParameter.INTERNET_GB;
            default -> throw new IllegalStateException(
                    "Невідомий параметр сортування."
            );
        };
    }

    private void showMessage(
            Terminal terminal,
            String message
    ) throws IOException {

        terminal.writer().println();
        terminal.writer().println(message);
        terminal.writer().println();
        terminal.writer().println(
                "Натисніть будь-яку клавішу для повернення..."
        );
        terminal.writer().flush();

        terminal.reader().read();
    }

    private void clearTerminal(
            Terminal terminal
    ) {

        terminal.writer().print(
                "\033[H\033[2J"
        );
        terminal.writer().flush();
    }
}