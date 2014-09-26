package de.twenty11.skysail.client.cli.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RestCommands2Test extends TestBase {

    private static final String TEST_FILES_DIR = "skysail.client.test/resources/largetests";
    private static final String TEST_FILE_SUFFIX = ".test";

    private String filename;

    public RestCommands2Test(final String filenname) {
        this.filename = filenname;
    }

    @Before
    public void setUp() {
        exec("setHost localhost");
        exec("setPort 2016");

        exec("env --showRequestHeaders false");
        exec("env --showResponseHeaders false");
        exec("env --showBody false");

        exec("cd");
    }

    @Parameters(name = "{index}: {0}")
    public static Collection<String[]> files() {
        ArrayList<String[]> result = new ArrayList<>();
        getTestFiles(Paths.get(TEST_FILES_DIR), result);
        return result;
    }

    private static void getTestFiles(Path path, ArrayList<String[]> result) {
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(path);
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    getTestFiles(entry, result);
                }
                if (entry.toString().endsWith(TEST_FILE_SUFFIX)) {
                    result.add(new String[] { entry.toString() });
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void testName3() throws Exception {
        File file = new File(filename);
        System.out.println("");
        System.out.println("***** " + file.getAbsolutePath());
        System.out.println("");
        Stream<String> lines = Files.lines(Paths.get(filename));
        lines.forEach(line -> runTestLine(line));
        lines.close();
    }

    private void runTestLine(String line) {
        System.out.println("***** executing: " + line);
        exec(line);
    }

}
