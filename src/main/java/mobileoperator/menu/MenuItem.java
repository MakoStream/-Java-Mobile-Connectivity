package mobileoperator.menu;

public abstract class MenuItem {

    private final String title;

    protected MenuItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Текст, який відображається в меню.
     * Інші типи елементів зможуть перевизначати цей метод.
     */
    public String getDisplayText() {
        return title;
    }

    /**
     * Дія, що виконується після натискання Enter.
     */
    public abstract void execute();
}