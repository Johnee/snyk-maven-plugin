package io.snyk.snyk_maven_plugin.command;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandLineTest {

    @Test
    public void shouldIncludePathAndCommandName() {
        ProcessBuilder pb = CommandLine.asProcessBuilder(
            "/path/to/cli",
            Command.TEST,
            Optional.empty(),
            emptyList(),
            false
        );

        assertEquals(
            asList(
                "/path/to/cli",
                "test"
            ),
            pb.command()
        );
    }

    @Test
    public void shouldIncludeArguments() {
        ProcessBuilder pb = CommandLine.asProcessBuilder(
            "/path/to/cli",
            Command.TEST,
            Optional.empty(),
            asList(
                "--print-deps",
                "--all-projects",
                "--json-file-output=out.json"
            ),
            false
        );

        assertEquals(
            asList(
                "/path/to/cli",
                "test",
                "--print-deps",
                "--all-projects",
                "--json-file-output=out.json"
            ),
            pb.command()
        );
    }

    @Test
    public void shouldNotModifyEnvironmentByDefault() {
        ProcessBuilder pb = CommandLine.asProcessBuilder(
            "/path/to/cli",
            Command.TEST,
            Optional.empty(),
            emptyList(),
            false
        );
        assertEquals(
            System.getenv(),
            pb.environment()
        );
    }

    @Test
    public void shouldIncludeAPIToken() {
        ProcessBuilder pb = CommandLine.asProcessBuilder(
            "/path/to/cli",
            Command.TEST,
            Optional.of("fake-token"),
            emptyList(),
            false
        );

        assertEquals(
            "fake-token",
            pb.environment().get("SNYK_TOKEN")
        );
    }

    @Test
    public void shouldForceColorWhenEnabled() {
        ProcessBuilder pb = CommandLine.asProcessBuilder(
            "/path/to/cli",
            Command.TEST,
            Optional.empty(),
            emptyList(),
            true
        );
        assertEquals(
            "true",
            pb.environment().get("FORCE_COLOR")
        );
    }

}
