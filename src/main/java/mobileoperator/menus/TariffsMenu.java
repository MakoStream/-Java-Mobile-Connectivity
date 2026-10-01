package mobileoperator.menus;

import java.io.IOException;
import java.util.List;

import org.jline.terminal.Terminal;

import mobileoperator.menu.Menu;
import mobileoperator.menu.MenuItem;
import mobileoperator.menu.MenuResult;

import mobileoperator.menu.items.ButtonMenuItem;

public class TariffsMenu extends Menu {

    public TariffsMenu() {
        super(
                "Тарифи",
                List.of(
                        new ButtonMenuItem(
                                "Переглянути тарифи",
                                () -> {
                                    System.out.println("Перегляд тарифів");
                                }
                        ),

                        new ButtonMenuItem(
                                "Додати тариф",
                                () -> {
                                    System.out.println("Додавання тарифу");
                                }
                        ),

                        new ButtonMenuItem(
                                "Видалити тариф",
                                () -> {
                                    System.out.println("Видалення тарифу");
                                }
                        ),

                        new ButtonMenuItem(
                                "Повернутися назад",
                                () -> {
                                }
                        )
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

                // Esc
                if (selectedIndex == -1) {
                    return MenuResult.back();
                }

                MenuItem selectedItem =
                        getItems().get(selectedIndex);

                // Останній пункт — "Повернутися назад"
                if (selectedIndex == getItems().size() - 1) {
                    return MenuResult.back();
                }

                selectedItem.execute();
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Не вдалося працювати з терміналом.",
                    e
            );
        }
    }
}