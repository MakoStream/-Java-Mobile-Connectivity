package mobileoperator.menu.items;

import mobileoperator.menu.Menu;
import mobileoperator.menu.MenuItem;

public class ButtonMenuItem extends MenuItem {

    private final Runnable action;
    private final Menu nextMenu;

    public ButtonMenuItem(
            String title,
            Runnable action
    ) {
        this(title, action, null);
    }

    public ButtonMenuItem(
            String title,
            Runnable action,
            Menu nextMenu
    ) {
        super(title);

        if (action == null) {
            throw new IllegalArgumentException(
                    "Дія кнопки не може бути null."
            );
        }

        this.action = action;
        this.nextMenu = nextMenu;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    @Override
    public void execute() {
        action.run();
    }
}