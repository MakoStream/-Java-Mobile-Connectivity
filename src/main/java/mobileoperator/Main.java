package mobileoperator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import mobileoperator.menus.MainMenu;
import mobileoperator.menus.SortingMenu;
import mobileoperator.menus.StatisticsMenu;
import mobileoperator.menus.TariffsMenu;
import mobileoperator.menu.MenuController;
import mobileoperator.menu.MenuItem;
import mobileoperator.menu.items.ButtonMenuItem;

public class Main {

    public static void main(String[] args) {

        try {
            ApplicationData applicationData =
                    new ApplicationData(
                            Path.of("data")
                    );

            List<MenuItem> menuItems = List.of(

                    new ButtonMenuItem(
                            "Тарифи",
                            () -> {
                            },
                            new TariffsMenu(applicationData)
                    ),

                    new ButtonMenuItem(
                            "Статистика",
                            () -> {
                            },
                            new StatisticsMenu(applicationData)
                    ),

                    new ButtonMenuItem(
                            "Сортування тарифів",
                            () -> {
                            },
                            new SortingMenu(applicationData)
                    )
            );

            MainMenu mainMenu =
                    new MainMenu(menuItems);

            MenuController menuController =
                    new MenuController();

            menuController.start(mainMenu);

        } catch (IOException e) {
            System.err.println(
                    "Не вдалося завантажити дані: "
                            + e.getMessage()
            );
        }
    }
}