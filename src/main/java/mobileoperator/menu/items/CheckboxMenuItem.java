package mobileoperator.menu.items;

import mobileoperator.menu.MenuItem;

public class CheckboxMenuItem extends MenuItem {

    private boolean checked;

    public CheckboxMenuItem(
            String title,
            boolean defaultChecked
    ) {
        super(title);
        this.checked = defaultChecked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void toggle() {
        checked = !checked;
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
        toggle();
    }
}