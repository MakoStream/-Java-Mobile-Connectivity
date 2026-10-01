package mobileoperator.menu;

import java.io.IOException;
import java.util.List;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

public class MainMenu extends Menu {

    public MainMenu(List<MenuItem> items) {
        super("Мобільний оператор", items);
    }

    @Override
    public void show() {

        try (Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build()) {

            terminal.enterRawMode();

            /*
             * Вмикаємо application keypad mode.
             * Саме це потрібно JLine для коректної
             * роботи стрілок у custom input loop.
             */
            terminal.puts(Capability.keypad_xmit);
            terminal.flush();

            int selectedIndex = 0;
            boolean running = true;

            while (running) {

                selectedIndex = selectItem(
                        terminal,
                        selectedIndex
                );

                MenuItem selectedItem =
                        getItems().get(selectedIndex);

                selectedItem.execute();

                // Останній пункт — "Вихід"
                if (selectedIndex == getItems().size() - 1) {

                    running = false;

                } else {

                    terminal.writer().println();
                    terminal.writer().println(
                            "Натисніть Enter, щоб повернутися до меню..."
                    );
                    terminal.writer().flush();

                    waitForEnter(terminal);
                }
            }

            /*
             * Повертаємо звичайний режим клавіатури.
             */
            terminal.puts(Capability.keypad_local);
            terminal.flush();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Не вдалося отримати доступ до клавіатури.",
                    e
            );
        }
    }

    private void waitForEnter(
            Terminal terminal
    ) throws IOException {

        while (true) {

            int key = terminal.reader().read();

            if (key == '\r' || key == '\n') {
                return;
            }
        }
    }
}