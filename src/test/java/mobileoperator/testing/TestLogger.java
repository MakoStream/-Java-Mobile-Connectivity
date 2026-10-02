package mobileoperator.testing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestLogger implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Path LOG_DIRECTORY = Paths.get("target", "test-log");
    private static final Path LOG_FILE = LOG_DIRECTORY.resolve("test.log");

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        write(
                "TEST START",
                context.getRequiredTestClass().getSimpleName()
                        + "."
                        + context.getRequiredTestMethod().getName()
        );
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        String testName =
                context.getRequiredTestClass().getSimpleName()
                        + "."
                        + context.getRequiredTestMethod().getName();

        if (context.getExecutionException().isPresent()) {
            Throwable exception = context.getExecutionException().get();

            write(
                    "TEST ERROR",
                    testName
                            + " | "
                            + exception.getClass().getSimpleName()
                            + ": "
                            + exception.getMessage()
            );
        } else {
            write(
                    "TEST END",
                    testName + " | PASSED"
            );
        }
    }

    public static void operation(String message) {
        write("TEST OPERATION", message);
    }

    public static void state(String variableName, Object value) {
        write(
                "TEST STATE",
                variableName + " = " + String.valueOf(value)
        );
    }

    public static void assertion(
            String description,
            Object expected,
            Object actual
    ) {
        write(
                "TEST ASSERT",
                description
                        + " | expected="
                        + String.valueOf(expected)
                        + " | actual="
                        + String.valueOf(actual)
        );
    }

    public static void separator() {
        write(
                "SEPARATOR",
                "----------------------------------------"
        );
    }

    private static synchronized void write(
            String type,
            String message
    ) {
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
                    "Не вдалося записати тестовий лог.",
                    exception
            );
        }
    }
}