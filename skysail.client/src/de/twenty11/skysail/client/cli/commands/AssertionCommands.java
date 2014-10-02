package de.twenty11.skysail.client.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.client.cli.domain.KeyValueAssertion;
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

	@CliCommand(value = "assertTrue", help = "assert conditions on the current headers, body, status or body")
	public String assertTrue(
			@CliOption(key = { "body" }, mandatory = false, help = "assert condition on the current body: --body username=admin") final KeyValueAssertion bodyAssertion,
            @CliOption(key = { "header" }, mandatory = false, help = "assert condition on the current header: --header Content-Type=appliation/json") final KeyValueAssertion headerAssertion,
            @CliOption(key = { "status" }, mandatory = false, help = "assert condition on the current status: --status 200") final Integer statusCode,
			@CliOption(key = { "link" }, mandatory = false, optionContext = "foo,bar", help = "assert condition on the current links: --link uri|rel|title <uri>|<rel>|<title> exists|missing") final LinkAssertion linkAssertion) {

		StringBuilder sb = new StringBuilder();
		AssertionUtils.handleBody(context, bodyAssertion, sb);
        AssertionUtils.handleHeader(context, headerAssertion, sb);
        AssertionUtils.handleStatus(context, statusCode, sb);
		AssertionUtils.handleLinks(context, linkAssertion, sb);
		return sb.toString();
	}
	
	@CliCommand(value = "assertNotEmpty", help = "assert that certain element exists in headers (todo:, body or links) and is not empty: assertNotEmpty --header Set-Cookie")
    public String assertNotEmpty(
            @CliOption(key = { "body" }, mandatory = false, help = "assert condition on the current body: --body username=admin") final String bodyKey,
            @CliOption(key = { "header" }, mandatory = false, help = "assert condition on the current header: --header Content-Type=appliation/json") final String headerKey,
            @CliOption(key = { "link" }, mandatory = false, optionContext = "foo,bar", help = "assert condition on the current links: --link uri|rel|title <uri>|<rel>|<title> exists|missing") final String linkKey) {

        StringBuilder sb = new StringBuilder();
        //AssertionUtils.handleBody(context, bodyAssertion, sb);
        AssertionUtils.handleHeaderNotEmpty(context, headerKey, sb);
        //AssertionUtils.handleLinks(context, linkAssertion, sb);
        return sb.toString();
    }

    @CliCommand(value = "assertEmpty", help = "assert that certain element do not exist in headers (todo:, body or links) and is not empty: assertEmpty --header Set-Cookie")
    public String assertEmpty(
            @CliOption(key = { "body" }, mandatory = false, help = "assert condition on the current body: --body username=admin") final String bodyKey,
            @CliOption(key = { "header" }, mandatory = false, help = "assert condition on the current header: --header Content-Type=appliation/json") final String headerKey,
            @CliOption(key = { "link" }, mandatory = false, optionContext = "foo,bar", help = "assert condition on the current links: --link uri|rel|title <uri>|<rel>|<title> exists|missing") final String linkKey) {

        StringBuilder sb = new StringBuilder();
        //AssertionUtils.handleBody(context, bodyAssertion, sb);
        AssertionUtils.handleHeaderEmpty(context, headerKey, sb);
        //AssertionUtils.handleLinks(context, linkAssertion, sb);
        return sb.toString();
    }

}
