package mobileoperator.menu;

import java.io.IOException;
import java.util.List;

import mobileoperator.menu.items.InputMenuItem;
import mobileoperator.menu.items.SelectMenuItem;

import org.jline.terminal.Terminal;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.utils.InfoCmp.Capability;

public abstract class Menu {

    private final String title;
    private final List<MenuItem> items;

    protected Menu(String title, List<MenuItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public abstract MenuResult show(Terminal terminal);

    protected int selectItem(
            Terminal terminal,
            int selectedIndex
    ) throws IOException {

        BindingReader bindingReader =
                new BindingReader(terminal.reader());

        KeyMap<String> keyMap = new KeyMap<>();

        // Стрілка вгору
        keyMap.bind(
                "UP",
                KeyMap.key(
                        terminal,
                        Capability.key_up
                )
        );

        // Стрілка вниз
        keyMap.bind(
                "DOWN",
                KeyMap.key(
                        terminal,
                        Capability.key_down
                )
        );

        // Стрілка вліво
        keyMap.bind(
                "LEFT",
                KeyMap.key(
                        terminal,
                        Capability.key_left
                )
        );

        // Стрілка вправо
        keyMap.bind(
                "RIGHT",
                KeyMap.key(
                        terminal,
                        Capability.key_right
                )
        );

        // Enter
        keyMap.bind("ENTER", "\r");
        keyMap.bind("ENTER", "\n");
        keyMap.bind("ESC", "\033");

        // Backspace
        keyMap.bind("BACKSPACE", "\b");
        keyMap.bind("BACKSPACE", "\u007f");

        // Delete
        keyMap.bind(
                "DELETE",
                KeyMap.key(
                        terminal,
                        Capability.key_dc
                )
        );

        /*
         * Дозволяємо BindingReader повертати звичайні
         * символи, які не були окремо прив'язані.
         */
        keyMap.setUnicode("CHAR");
        keyMap.setNomatch("CHAR");

        while (true) {

            draw(terminal, selectedIndex);

            String key = bindingReader.readBinding(keyMap);

            MenuItem selectedItem = items.get(selectedIndex);

            /*
             * Звичайний символ.
             *
             * Якщо вибране поле введення,
             * передаємо символ у нього.
             */
            if ("CHAR".equals(key)) {

                String lastBinding = bindingReader.getLastBinding();

                if (selectedItem instanceof InputMenuItem input
                        && lastBinding != null
                        && lastBinding.length() == 1) {

                    input.insert(lastBinding.charAt(0));
                }

                continue;
            }

            if ("UP".equals(key)) {

                selectedIndex--;

                if (selectedIndex < 0) {
                    selectedIndex = items.size() - 1;
                }
            }

            else if ("DOWN".equals(key)) {

                selectedIndex++;

                if (selectedIndex >= items.size()) {
                    selectedIndex = 0;
                }
            }

            else if ("LEFT".equals(key)) {

                if (selectedItem instanceof InputMenuItem input) {
                    input.moveCursorLeft();
                }

                else if (selectedItem instanceof SelectMenuItem select) {
                    select.selectPrevious();
                }
            }

            else if ("RIGHT".equals(key)) {

                if (selectedItem instanceof InputMenuItem input) {
                    input.moveCursorRight();
                }

                else if (selectedItem instanceof SelectMenuItem select) {
                    select.selectNext();
                }
            }

            else if ("BACKSPACE".equals(key)) {

                if (selectedItem instanceof InputMenuItem input) {
                    input.backspace();
                }
            }

            else if ("DELETE".equals(key)) {

                if (selectedItem instanceof InputMenuItem input) {
                    input.delete();
                }
            }

            else if ("ESC".equals(key)) {
                return -1;
            }

            else if ("ENTER".equals(key)) {

                if (selectedItem instanceof InputMenuItem) {
                    continue;
                }

                return selectedIndex;
            }
        }
    }

    private void draw(
            Terminal terminal,
            int selectedIndex
    ) {

        terminal.puts(Capability.clear_screen);
        terminal.puts(Capability.cursor_home);
        terminal.flush();

        var writer = terminal.writer();

        writer.println();
        writer.println("================================");
        writer.println("       " + title);
        writer.println("================================");

        for (int i = 0; i < items.size(); i++) {

            MenuItem item = items.get(i);

            String displayText;

            if (item instanceof InputMenuItem input) {
                displayText = input.getDisplayText(i == selectedIndex);
            } else {
                displayText = item.getDisplayText();
            }

            if (i == selectedIndex) {
                writer.println("> " + displayText);
            } else {
                writer.println("  " + displayText);
            }
        }

        writer.println("================================");
        writer.println("↑ ↓ — вибір    Enter — підтвердити");

        writer.flush();
    }
}