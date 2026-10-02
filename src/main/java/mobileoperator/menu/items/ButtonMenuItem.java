package mobileoperator.menu.items;

import mobileoperator.logging.OperationLogger;
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

        OperationLogger.operation(
                "ButtonMenuItem",
                "constructor",
                "title='" + title + "', hasNextMenu=" + (nextMenu != null)
        );

        if (action == null) {
            OperationLogger.error(
                    "ButtonMenuItem",
                    "constructor",
                    "action is null"
            );

            throw new IllegalArgumentException(
                    "Дія кнопки не може бути null."
            );
        }

        this.action = action;
        this.nextMenu = nextMenu;

        OperationLogger.result(
                "ButtonMenuItem",
                "constructor",
                "created successfully"
        );
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    @Override
    public void execute() {
        OperationLogger.operation(
                "ButtonMenuItem",
                "execute",
                "title='" + getTitle() + "'"
        );

        action.run();

        OperationLogger.result(
                "ButtonMenuItem",
                "execute",
                "action completed"
        );
    }
}