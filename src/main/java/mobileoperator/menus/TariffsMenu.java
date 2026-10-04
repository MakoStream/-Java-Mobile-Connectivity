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
import mobileoperator.model.MobileOperator;

public class TariffsMenu extends Menu {

    public TariffsMenu(ApplicationData applicationData) {
        super(
                "Тарифи",
                createItems(applicationData)
        );
    }

    private static List<MenuItem> createItems(
            ApplicationData applicationData
    ) {
        List<MenuItem> items = new ArrayList<>();

        for (MobileOperator operator :
                applicationData.getOperators()) {

            items.add(
                    new ButtonMenuItem(
                            operator.getName(),
                            () -> showOperatorTariffs(operator)
                    )
            );
        }

        items.add(
                new ButtonMenuItem(
                        "Повернутися назад",
                        () -> {
                        }
                )
        );

        return items;
    }

    private static void showOperatorTariffs(
            MobileOperator operator
    ) {
        System.out.println();
        System.out.println(
                "Оператор: " + operator.getName()
        );
        System.out.println();

        if (operator.getTariffs().isEmpty()) {
            System.out.println("Тарифів немає.");
        } else {
            operator.getTariffs().forEach(
                    tariff -> System.out.println(
                            tariff.getName()
                                    + " - "
                                    + tariff.getMonthlyFee()
                                    + " грн/міс."
                    )
            );
        }

        System.out.println();
        System.out.println(
                "Натисніть будь-яку клавішу для повернення..."
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

                if (selectedIndex == getItems().size() - 1) {
                    return MenuResult.back();
                }

                selectedItem.execute();

                terminal.reader().read();
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Не вдалося працювати з терміналом.",
                    e
            );
        }
    }
}