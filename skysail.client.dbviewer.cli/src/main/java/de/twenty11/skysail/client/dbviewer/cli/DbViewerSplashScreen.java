package de.twenty11.skysail.client.dbviewer.cli;

import java.io.OutputStream;
import java.io.PrintStream;

import org.clamshellcli.api.Context;
import org.clamshellcli.api.SplashScreen;

public class DbViewerSplashScreen implements SplashScreen{
    private static StringBuilder screen;
    static{
        screen = new StringBuilder();
        screen
            .append(String.format("%n%n"))
            .append("A command-line tool for Skysail").append(String.format("%n"))
            .append("Powered by Clamshell-Cli framework ").append(String.format("%n"))
            .append("http://code.google.com/p/clamshell-cli/").append(String.format("%n%n"))
    
            .append("Java version: ").append(System.getProperty("java.version")).append(String.format("%n"))
            .append("OS: ").append(System.getProperty("os.name")).append(", Version: ").append(System.getProperty("os.version"))

            ;
    }
    
    public void render(Context ctx) {
        PrintStream out = new PrintStream ((OutputStream)ctx.getValue(Context.KEY_OUTPUT_STREAM));
        out.println(screen);
    }

    public void plug(Context plug) {
    }
    
}