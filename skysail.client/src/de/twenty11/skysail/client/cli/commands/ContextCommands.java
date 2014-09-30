package de.twenty11.skysail.client.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.client.cli.utils.LinksUtils;

@Component
public class ContextCommands implements CommandMarker {

    @Autowired
    private Context context;

    @CliCommand(value = "links", help = "shows the available links to navigate to")
    public String links() {
        return LinksUtils.links(context);
    }

    @CliCommand(value = "body", help = "shows the current body")
    public String body() {
        return context.getBody();
    }

}
