package mobileoperator;

import java.util.List;

import mobileoperator.menus.MainMenu;
import mobileoperator.menus.TariffsMenu;
import mobileoperator.menu.MenuController;
import mobileoperator.menu.MenuItem;
import mobileoperator.menu.items.ButtonMenuItem;
import mobileoperator.menu.items.CheckboxMenuItem;
import mobileoperator.menu.items.InputMenuItem;
import mobileoperator.menu.items.SelectMenuItem;

// import org.jline.terminal.Terminal;

// import java.io.IOException;
// import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<MenuItem> menuItems = List.of(

                new ButtonMenuItem(
                        "Тарифи",
                        () -> {
                        },
                        new TariffsMenu()
                ),

                new MenuItem("Загальна кількість клієнтів") {
                    @Override
                    public void execute() {
                        System.out.println();
                        System.out.println(
                                "Ви обрали: Загальна кількість клієнтів"
                        );
                    }
                },

                new MenuItem("Сортування тарифів") {
                    @Override
                    public void execute() {
                        System.out.println();
                        System.out.println(
                                "Ви обрали: Сортування тарифів"
                        );
                    }
                },

                new MenuItem("Пошук тарифу") {
                    @Override
                    public void execute() {
                        System.out.println();
                        System.out.println(
                                "Ви обрали: Пошук тарифу"
                        );
                    }
                },

                new InputMenuItem(
                        "Мінімальна ціна",
                        "35345",
                        true,
                        10
                ),

                new SelectMenuItem(
                        "Компанія",
                        List.of(
                                "Kyivstar",
                                "Vodafone",
                                "lifecell"
                        ),
                        0
                ),
                new CheckboxMenuItem(
                        "Лише доступні",
                        true
                ),

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

        MainMenu mainMenu = new MainMenu(menuItems);

        MenuController menuController = new MenuController();

        menuController.start(mainMenu);
    }
}