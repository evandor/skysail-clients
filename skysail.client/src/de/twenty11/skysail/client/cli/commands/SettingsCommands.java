package de.twenty11.skysail.client.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class SettingsCommands implements CommandMarker {

    @Autowired
    private Context context;

    @CliCommand(value = "env", help = "set context variables for fine-tuning the programs behavior")
    public void env(
            @CliOption(key = { "showBody" }, mandatory = false, help = "if true: show body", specifiedDefaultValue = "true") final Boolean showBody,
            @CliOption(key = { "showRequestHeaders" }, mandatory = false, help = "if true: show request headers", specifiedDefaultValue = "true") final Boolean showRequestHeaders,
            @CliOption(key = { "showResponseHeaders" }, mandatory = false, help = "if true: show response headers", specifiedDefaultValue = "true") final Boolean showResponseHeaders) {

        if (showBody != null) {
            context.showBody(showBody);
        }
        if (showRequestHeaders != null) {
            context.showRequestHeaders(showRequestHeaders);
        }
        if (showResponseHeaders != null) {
            context.showResponseHeaders(showResponseHeaders);
        }
    }

    private void set(final Boolean value) {
        if (value != null) {
            context.showRequestHeaders(value);
        }
    }
}
