package de.twenty11.skysail.client.cli.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

import de.twenty11.skysail.client.cli.domain.JsonAssertion;

@Component
public class AssertionCommands implements CommandMarker {

    @Autowired
    private Context context;

    @CliCommand(value = "assert", help = "assert conditions on the current headers or body: assert --body <assertion>")
    public String get(
            @CliOption(key = { "body" }, mandatory = true, help = "assert condition on the current body: --body username=admin") final JsonAssertion assertion) {

        StringBuilder sb = new StringBuilder();
        Object match = JsonPath.read(context.getBody(), assertion.getJsonPath());
        if (match == null) {
            throw new IllegalStateException("assertion didn't find any match");
        }
        if (match instanceof List) {
            throw new IllegalStateException("assertion found a list; try to match only one item");
        }
        if (!match.toString().equals(assertion.getExpectedValue())) {
            throw new IllegalStateException("expected match '" + assertion.getExpectedValue() + "', but '"
                    + assertion.getJsonPath() + "' was '" + match.toString() + "'");
        } else {
            sb.append("matched " + assertion);
        }
        return sb.toString();
    }

}
