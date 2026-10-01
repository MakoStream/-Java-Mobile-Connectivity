package mobileoperator.menu;

public abstract class MenuItem {

    private final String title;

    protected MenuItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract void execute();
}