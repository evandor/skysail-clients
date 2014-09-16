package de.twenty11.skysail.client.cli.commands;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.domain.JsonAssertion;
import de.twenty11.skysail.client.cli.domain.LinkAssertion;

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
			@CliOption(key = { "link" }, mandatory = false, optionContext = "foo,bar", help = "assert condition on the current links: --link uri|rel|title <uri>|<rel>|<title> exists|missing") final LinkAssertion linkAssertion) {

		StringBuilder sb = new StringBuilder();
		handleBody(bodyAssertion, sb);
		handleLinks(linkAssertion, sb);
		return sb.toString();
	}

	private void handleBody(final JsonAssertion bodyAssertion, StringBuilder sb) {
		if (bodyAssertion == null) {
			return;
		}
		Object match = JsonPath.read(context.getBody(),
				bodyAssertion.getJsonPath());
		if (match == null) {
			throw new IllegalStateException("assertion didn't find any match");
		}
		if (match instanceof List) {
			throw new IllegalStateException(
					"assertion found a list; try to match only one item");
		}
		if (!match.toString().equals(bodyAssertion.getExpectedValue())) {
			throw new IllegalStateException("expected match '"
					+ bodyAssertion.getExpectedValue() + "', but '"
					+ bodyAssertion.getJsonPath() + "' was '"
					+ match.toString() + "'");
		} else {
			sb.append("matched " + bodyAssertion);
		}
	}

	private void handleLinks(LinkAssertion linkAssertion, StringBuilder sb) {
		if (linkAssertion == null) {
			return;
		}
		Function<Linkheader, String> mapping = determineMapping(linkAssertion);
		boolean matchFound = foundMatch(linkAssertion, mapping);
		processResult(linkAssertion, sb, matchFound);
	}

	private Function<Linkheader, String> determineMapping(
			LinkAssertion linkAssertion) {
		Function<Linkheader, String> mapping = null;
		switch (linkAssertion.getIdentifier()) {
		case URI:
			mapping= lh -> lh.getUri();
			break;
		case REL:
			mapping= lh -> lh.getRel().toString();
			break;
		case TITLE:
			mapping= lh -> lh.getTitle();
			break;
		default:
			throw new IllegalStateException();
		}
		return mapping;
	}

	private void processResult(LinkAssertion linkAssertion, StringBuilder sb,
			boolean matchFound) {
		if (linkAssertion.getCondition().equals(Condition.EXISTS)) {
			if (matchFound) {
				sb.append("matched " + linkAssertion);
			} else {
				throw new IllegalStateException("expected match '"
					+ linkAssertion.getExpectedValue() + "', but was not found");
			}
		} else {
			if (!matchFound) {
				sb.append("ok: did not match " + linkAssertion);
			} else {
				throw new IllegalStateException("expected match '"
					+ linkAssertion.getExpectedValue() + "' to be missing, but was found");
			}
			
		}
	}

	private boolean foundMatch(LinkAssertion linkAssertion,
			Function<Linkheader, String> mapping) {
		return context.getLinks().stream().map(mapping)
				.filter(uri -> uri.equals(linkAssertion.getExpectedValue()))
				.findFirst().isPresent();
	}
}
