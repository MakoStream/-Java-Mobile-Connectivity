package mobileoperator.menu.items;

import java.util.List;

import mobileoperator.logging.OperationLogger;
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

        OperationLogger.operation(
                "SelectMenuItem",
                "constructor",
                "title='" + title + "', options=" + options
                        + ", defaultIndex=" + defaultIndex
        );

        if (options == null || options.isEmpty()) {
            OperationLogger.error(
                    "SelectMenuItem",
                    "constructor",
                    "options is null or empty"
            );

            throw new IllegalArgumentException(
                    "Список варіантів не може бути порожнім."
            );
        }

        if (defaultIndex < 0 || defaultIndex >= options.size()) {
            OperationLogger.error(
                    "SelectMenuItem",
                    "constructor",
                    "defaultIndex=" + defaultIndex
                            + ", options.size=" + options.size()
            );

            throw new IllegalArgumentException(
                    "Неправильний індекс початкового значення."
            );
        }

        this.options = List.copyOf(options);
        this.selectedIndex = defaultIndex;

        OperationLogger.result(
                "SelectMenuItem",
                "constructor",
                "index=" + selectedIndex
                        + ", value='" + this.options.get(selectedIndex) + "'"
        );
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
        OperationLogger.operation(
                "SelectMenuItem",
                "selectPrevious",
                "index=" + selectedIndex
                        + ", value='" + options.get(selectedIndex) + "'"
        );

        selectedIndex--;

        if (selectedIndex < 0) {
            selectedIndex = options.size() - 1;
        }

        OperationLogger.result(
                "SelectMenuItem",
                "selectPrevious",
                "index=" + selectedIndex
                        + ", value='" + options.get(selectedIndex) + "'"
        );
    }

    public void selectNext() {
        OperationLogger.operation(
                "SelectMenuItem",
                "selectNext",
                "index=" + selectedIndex
                        + ", value='" + options.get(selectedIndex) + "'"
        );

        selectedIndex++;

        if (selectedIndex >= options.size()) {
            selectedIndex = 0;
        }

        OperationLogger.result(
                "SelectMenuItem",
                "selectNext",
                "index=" + selectedIndex
                        + ", value='" + options.get(selectedIndex) + "'"
        );
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
        OperationLogger.operation(
                "SelectMenuItem",
                "execute",
                "no action"
        );
    }
}