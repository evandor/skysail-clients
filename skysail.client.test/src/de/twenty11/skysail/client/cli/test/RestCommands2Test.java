package de.twenty11.skysail.client.cli.test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RestCommands2Test extends TestBase {

    private String filename;

    public RestCommands2Test(final String filenname) {
        this.filename = filenname;
    }

    @Parameters(name = "{index}: {0}")
    public static Collection<String[]> files() {
        return Arrays.asList(new String[][] { { "skysail.client.test/resources/some.test" } });
    }

   // @Test
    public void testName2() throws Exception {
        exec("get", "assert --status 200");
    }
    
    @Test
    public void testName3() throws Exception {
        File file = new File(filename);
        System.out.println(file.getAbsolutePath());
        if (file.exists() && !file.isDirectory()) {
            Stream<String> lines = Files.lines(Paths.get(filename));
            lines.forEach(line -> exec(line));
        }
    }

}
