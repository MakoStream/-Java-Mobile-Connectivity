package mobileoperator.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class OperationLogger {

    private static final Path LOG_DIRECTORY = Paths.get("target", "test-log");
    private static final Path LOG_FILE = LOG_DIRECTORY.resolve("test.log");

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private OperationLogger() {
    }

    public static void operation(String className, String methodName, String message) {
        write("OPERATION", className + "." + methodName + " | " + message);
    }

    public static void result(String className, String methodName, String message) {
        write("RESULT", className + "." + methodName + " | " + message);
    }

    public static void error(String className, String methodName, String message) {
        write("ERROR", className + "." + methodName + " | " + message);
    }

    public static void exception(String className, String methodName, Throwable exception) {
        write(
                "ERROR",
                className + "." + methodName
                        + " | "
                        + exception.getClass().getSimpleName()
                        + ": "
                        + exception.getMessage()
        );
    }

    private static synchronized void write(String type, String message) {
        try {
            Files.createDirectories(LOG_DIRECTORY);

            String line =
                    "["
                            + LocalDateTime.now().format(TIME_FORMAT)
                            + "] ["
                            + type
                            + "] "
                            + message
                            + System.lineSeparator();

            Files.writeString(
                    LOG_FILE,
                    line,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException exception) {
            throw new RuntimeException(
                    "Не вдалося записати операцію в лог.",
                    exception
            );
        }
    }
}