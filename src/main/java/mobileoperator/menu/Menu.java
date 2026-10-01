package mobileoperator.menu;

import java.io.IOException;
import java.util.List;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Terminal;
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

    public abstract void show();

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

        // Enter
        keyMap.bind("ENTER", "\r");
        keyMap.bind("ENTER", "\n");

        while (true) {

            draw(terminal, selectedIndex);

            String key = bindingReader.readBinding(keyMap);

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

            else if ("ENTER".equals(key)) {
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

            if (i == selectedIndex) {
                writer.println("> " + items.get(i).getTitle());
            } else {
                writer.println("  " + items.get(i).getTitle());
            }
        }

        writer.println("================================");
        writer.println("↑ ↓ — вибір    Enter — підтвердити");

        writer.flush();
    }
}