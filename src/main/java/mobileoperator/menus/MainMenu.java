package mobileoperator.menus;

import java.io.IOException;
import java.util.List;

import org.jline.terminal.Terminal;

import mobileoperator.menu.Menu;
import mobileoperator.menu.MenuItem;
import mobileoperator.menu.MenuResult;
import mobileoperator.menu.items.ButtonMenuItem;

public class MainMenu extends Menu {

    public MainMenu(List<MenuItem> items) {
        super(
                "Мобільний оператор",
                items
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
                    return MenuResult.exit();
                }

                MenuItem selectedItem =
                        getItems().get(selectedIndex);

                selectedItem.execute();

                if (selectedItem instanceof ButtonMenuItem button
                        && button.getNextMenu() != null) {

                    return MenuResult.next(
                            button.getNextMenu()
                    );
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Не вдалося працювати з терміналом.",
                    e
            );
        }
    }
}