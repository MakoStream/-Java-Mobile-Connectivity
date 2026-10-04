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

public class StatisticsMenu extends Menu {

    public StatisticsMenu(ApplicationData applicationData) {
        super(
                "Статистика",
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
                            "Кількість клієнтів "
                                    + operator.getName()
                                    + ": "
                                    + operator.getClientCount(),
                            () -> {
                            }
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

                if (selectedIndex == getItems().size() - 1) {
                    return MenuResult.back();
                }

                getItems()
                        .get(selectedIndex)
                        .execute();
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Не вдалося працювати з терміналом.",
                    e
            );
        }
    }
}