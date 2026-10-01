package mobileoperator.menu.items;

import java.util.List;

import mobileoperator.menu.MenuItem;

public class SelectMenuItem extends MenuItem {

    private final List<String> options;
    private int selectedIndex;

    public SelectMenuItem(
            String title,
            List<String> options,
            int defaultIndex
    ) {
        super(title);

        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException(
                    "Список варіантів не може бути порожнім."
            );
        }

        if (defaultIndex < 0 || defaultIndex >= options.size()) {
            throw new IllegalArgumentException(
                    "Неправильний індекс початкового значення."
            );
        }

        this.options = List.copyOf(options);
        this.selectedIndex = defaultIndex;
    }

    public String getSelectedValue() {
        return options.get(selectedIndex);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public List<String> getOptions() {
        return options;
    }

    public void selectPrevious() {

        selectedIndex--;

        if (selectedIndex < 0) {
            selectedIndex = options.size() - 1;
        }
    }

    public void selectNext() {

        selectedIndex++;

        if (selectedIndex >= options.size()) {
            selectedIndex = 0;
        }
    }

    @Override
    public String getDisplayText() {
        return getTitle()
                + ": < "
                + getSelectedValue()
                + " >";
    }

    @Override
    public void execute() {
        // Enter нічого не робить.
    }
}