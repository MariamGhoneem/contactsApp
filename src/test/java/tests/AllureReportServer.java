package tests;

import org.testng.annotations.AfterSuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;

public class AllureReportServer {

    @AfterSuite
    public void serveAllureReport() {
        try {
            File projectDir = new File(System.getProperty("user.dir"));
            File logFile = File.createTempFile("allure-serve", ".log");

            new ProcessBuilder("mvn", "-q", "allure:serve")
                    .directory(projectDir)
                    .redirectErrorStream(true)
                    .redirectOutput(logFile)
                    .start();

            Instant deadline = Instant.now().plus(Duration.ofSeconds(30));
            while (Instant.now().isBefore(deadline)) {
                for (String line : Files.readAllLines(logFile.toPath())) {
                    if (line.contains("Server started at")) {
                        System.out.println(line);
                        return;
                    }
                }
                Thread.sleep(500);
            }
            System.err.println("Allure report server did not start within 30s; check " + logFile);
        } catch (IOException | InterruptedException e) {
            System.err.println("Could not start Allure report server: " + e.getMessage());
        }
    }
}
