package mobileoperator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import mobileoperator.menus.MainMenu;
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

                    new MenuItem(
                            "Загальна кількість клієнтів"
                    ) {
                        @Override
                        public void execute() {
                            System.out.println();
                            System.out.println(
                                    "Загальна кількість клієнтів: "
                                            + applicationData
                                            .getTotalClientCount()
                            );
                        }
                    },

                    new MenuItem(
                            "Сортування тарифів"
                    ) {
                        @Override
                        public void execute() {
                            System.out.println();
                            System.out.println(
                                    "Ви обрали: Сортування тарифів"
                            );
                        }
                    },

                    new MenuItem(
                            "Пошук тарифу"
                    ) {
                        @Override
                        public void execute() {
                            System.out.println();
                            System.out.println(
                                    "Ви обрали: Пошук тарифу"
                            );
                        }
                    },

                    new MenuItem("Вихід") {
                        @Override
                        public void execute() {
                            System.out.println();
                            System.out.println(
                                    "Вихід з програми."
                            );
                        }
                    }
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