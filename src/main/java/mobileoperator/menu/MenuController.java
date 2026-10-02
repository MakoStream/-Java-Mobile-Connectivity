package mobileoperator.menu;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import mobileoperator.logging.OperationLogger;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class MenuController {

    private final Deque<Menu> menuStack = new ArrayDeque<>();

    public void start(Menu firstMenu) {
        OperationLogger.operation(
                "MenuController",
                "start",
                "firstMenu='" + firstMenu.getTitle() + "'"
        );

        menuStack.push(firstMenu);

        try (Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build()) {

            terminal.enterRawMode();

            terminal.puts(
                    org.jline.utils.InfoCmp.Capability.keypad_xmit
            );
            terminal.flush();

            boolean running = true;

            while (running && !menuStack.isEmpty()) {

                Menu currentMenu = menuStack.peek();

                OperationLogger.operation(
                        "MenuController",
                        "start",
                        "currentMenu='" + currentMenu.getTitle()
                                + "', stackSize=" + menuStack.size()
                );

                MenuResult result = currentMenu.show(terminal);

                OperationLogger.operation(
                        "MenuController",
                        "start",
                        "result=" + result.getType()
                );

                switch (result.getType()) {

                    case STAY:
                        break;

                    case NEXT:
                        menuStack.push(result.getNextMenu());

                        OperationLogger.result(
                                "MenuController",
                                "start",
                                "NEXT -> '" + result.getNextMenu().getTitle()
                                        + "', stackSize=" + menuStack.size()
                        );
                        break;

                    case BACK:
                        menuStack.pop();

                        OperationLogger.result(
                                "MenuController",
                                "start",
                                "BACK, stackSize=" + menuStack.size()
                        );
                        break;

                    case EXIT:
                        running = false;

                        OperationLogger.result(
                                "MenuController",
                                "start",
                                "EXIT"
                        );
                        break;
                }
            }

            terminal.puts(
                    org.jline.utils.InfoCmp.Capability.keypad_local
            );
            terminal.flush();

            OperationLogger.result(
                    "MenuController",
                    "start",
                    "controller stopped"
            );

        } catch (IOException e) {
            OperationLogger.exception(
                    "MenuController",
                    "start",
                    e
            );

            throw new RuntimeException(
                    "Не вдалося запустити термінал.",
                    e
            );
        }
    }
}