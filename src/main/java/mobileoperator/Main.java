package mobileoperator;

import java.util.List;

import mobileoperator.menu.MainMenu;
import mobileoperator.menu.MenuItem;

public class Main {

    public static void main(String[] args) {

        List<MenuItem> menuItems = List.of(

                new MenuItem("Список тарифів") {
                    @Override
                    public void execute() {
                        System.out.println();
                        System.out.println("Ви обрали: Список тарифів");
                    }
                },

                new MenuItem("Загальна кількість клієнтів") {
                    @Override
                    public void execute() {
                        System.out.println();
                        System.out.println("Ви обрали: Загальна кількість клієнтів");
                    }
                },

                new MenuItem("Сортування тарифів") {
                    @Override
                    public void execute() {
                        System.out.println();
                        System.out.println("Ви обрали: Сортування тарифів");
                    }
                },

                new MenuItem("Пошук тарифу") {
                    @Override
                    public void execute() {
                        System.out.println();
                        System.out.println("Ви обрали: Пошук тарифу");
                    }
                },

                new MenuItem("Вихід") {
                    @Override
                    public void execute() {
                        System.out.println();
                        System.out.println("Вихід з програми.");
                    }
                }
        );

        MainMenu mainMenu = new MainMenu(menuItems);

        mainMenu.show();
    }
}