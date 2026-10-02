package mobileoperator.menu.items;

import mobileoperator.logging.OperationLogger;
import mobileoperator.menu.MenuItem;

public class CheckboxMenuItem extends MenuItem {

    private boolean checked;

    public CheckboxMenuItem(
            String title,
            boolean defaultChecked
    ) {
        super(title);

        OperationLogger.operation(
                "CheckboxMenuItem",
                "constructor",
                "title='" + title + "', checked=" + defaultChecked
        );

        this.checked = defaultChecked;

        OperationLogger.result(
                "CheckboxMenuItem",
                "constructor",
                "checked=" + checked
        );
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        OperationLogger.operation(
                "CheckboxMenuItem",
                "setChecked",
                "before=" + this.checked + ", after=" + checked
        );

        this.checked = checked;

        OperationLogger.result(
                "CheckboxMenuItem",
                "setChecked",
                "checked=" + this.checked
        );
    }

    public void toggle() {
        OperationLogger.operation(
                "CheckboxMenuItem",
                "toggle",
                "before=" + checked
        );

        checked = !checked;

        OperationLogger.result(
                "CheckboxMenuItem",
                "toggle",
                "after=" + checked
        );
    }

    @Override
    public String getDisplayText() {
        return "["
                + (checked ? "x" : " ")
                + "] "
                + getTitle();
    }

    @Override
    public void execute() {
        OperationLogger.operation(
                "CheckboxMenuItem",
                "execute",
                "toggle"
        );

        toggle();
    }
}