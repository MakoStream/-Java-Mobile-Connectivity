package mobileoperator.menu;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class MenuController {

    private final Deque<Menu> menuStack = new ArrayDeque<>();

    public void start(Menu firstMenu) {
        menuStack.push(firstMenu);

        try (Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build()) {

            terminal.enterRawMode();
            terminal.puts(org.jline.utils.InfoCmp.Capability.keypad_xmit);
            terminal.flush();

            boolean running = true;

            while (running && !menuStack.isEmpty()) {

                Menu currentMenu = menuStack.peek();

                MenuResult result = currentMenu.show(terminal);

                switch (result.getType()) {

                    case STAY:
                        break;

                    case NEXT:
                        menuStack.push(result.getNextMenu());
                        break;

                    case BACK:
                        menuStack.pop();
                        break;

                    case EXIT:
                        running = false;
                        break;
                }
            }

            terminal.puts(
                    org.jline.utils.InfoCmp.Capability.keypad_local
            );
            terminal.flush();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Не вдалося запустити термінал.",
                    e
            );
        }
    }
}