package de.twenty11.skysail.client.cli.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class AssertionCommands implements CommandMarker {

    @Autowired
    private Context context;

    @CliCommand(value = "assert", help = "assert conditions on the current headers or body: assert --body <assertion>")
    public String get(
            @CliOption(key = { "body" }, mandatory = true, help = "assert condition on the current body: --body username=admin") final String body) {

        String[] split = body.split("=");
        if (split.length != 2) {
            return "wrong invocation of assert!";
        }
        StringBuilder sb = new StringBuilder();
        Object match = JsonPath.read(context.getBody(), split[0]);
        if (match == null) {
            throw new IllegalStateException("assertion didn't find any match");
        }
        if (match instanceof List) {
            throw new IllegalStateException("assertion found a list; try to match only one item");
        }
        if (!match.toString().equals(split[1])) {
            throw new IllegalStateException("expected match '" + split[1] + "', but '" + split[0] + "' was '"
                    + match.toString() + "'");
        }
        return sb.toString();
    }

}
