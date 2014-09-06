package de.twenty11.skysail.client.cli.commands;

import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class HeaderCommands implements CommandMarker {

    @Autowired
    private Context context;

    @CliCommand(value = "setHeader", help = "sets existing or adds new header to be used in subsequent requests")
    public String get(
            @CliOption(key = { "", "header" }, mandatory = false, help = "e.g. setHeader Accept=text/html") final Header header) {
        context.addOrSetHeader(header);
        return "Request headers set to: " + context.getRequestHeaders().toString();
    }

    @CliCommand(value = "removeHeader", help = "removes header from subsequent requests")
    public String remove(
            @CliOption(key = { "", "header" }, mandatory = false, help = "e.g. removeHeader Accept") final String header) {
        context.removeHeader(header);
        return "Request headers set to: " + context.getRequestHeaders().toString();
    }

    @CliCommand(value = "showHeader", help = "shows request headers for subsequent requests")
    public String show() {
        return "Request headers set to: " + context.getRequestHeaders().toString();
    }

}
