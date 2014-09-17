package de.twenty11.skysail.client.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.client.cli.domain.JsonAssertion;
import de.twenty11.skysail.client.cli.domain.LinkAssertion;
import de.twenty11.skysail.client.cli.utils.AssertionUtils;

@Component
public class AssertionCommands implements CommandMarker {

	@Autowired
	private Context context;

	public enum Condition {
		EXISTS, MISSING
	}

	public enum Identifier {
		URI, REL, TITLE
	}

	@CliCommand(value = "assert", help = "assert conditions on the current headers or body: assert --body <assertion>")
	public String asserting(
			@CliOption(key = { "body" }, mandatory = false, help = "assert condition on the current body: --body username=admin") final JsonAssertion bodyAssertion,
            @CliOption(key = { "status" }, mandatory = false, help = "assert condition on the current status: --status 200") final String statusCode,
			@CliOption(key = { "link" }, mandatory = false, optionContext = "foo,bar", help = "assert condition on the current links: --link uri|rel|title <uri>|<rel>|<title> exists|missing") final LinkAssertion linkAssertion) {

		StringBuilder sb = new StringBuilder();
		AssertionUtils.handleBody(context, bodyAssertion, sb);
        AssertionUtils.handleStatus(context, Integer.valueOf(statusCode), sb);
		AssertionUtils.handleLinks(context, linkAssertion, sb);
		return sb.toString();
	}


}
