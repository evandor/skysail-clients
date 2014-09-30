package de.twenty11.skysail.client.cli.commands;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;
import de.twenty11.skysail.client.cli.utils.OutputUtils;
import de.twenty11.skysail.client.cli.utils.RestUtils;

@Component
public class RestCommands implements CommandMarker {

	@Autowired
	private Context context;

	@CliCommand(value = "head", help = "executes a HEAD request on the current path")
    public String head(
            @CliOption(key = { "uri" }, mandatory = false, help = "select a link by matching the uri") final String uri,
            @CliOption(key = { "title" }, mandatory = false, help = "select a link by matching the title") final String title,
            @CliOption(key = { "rel" }, mandatory = false, help = "select a link by matching the relation attribute") final String rel
            ) {

        StringBuilder sb = new StringBuilder();

        RestUtils.matchParams(context, uri, title, rel, sb);

        String url = context.getCurrentUrl();// + "?media=json";
        String headline = "\n> GET '" + url + "'\n";
        sb.append(StringUtils.repeat("=", headline.length()));
        sb.append(headline);
        sb.append(StringUtils.repeat("=", headline.length())).append("\n\n");

        HttpResponse response = HttpUtils.head(context);

        context.setResponseHeaders(response.getAllHeaders());
        context.setBody("");
        context.setStatus(response.getStatusLine());

        sb.append(OutputUtils.printRequestHeader(context));
        sb.append(StringUtils.repeat("-", 30)).append("\n");
        sb.append(OutputUtils.printStatus(context));
        sb.append(OutputUtils.printResponseHeader(context));

        RestUtils.setLinks(context, response);

        return sb.toString();
    }

	@CliCommand(value = "options", help = "executes a OPTIONS request on the current path")
	public String options() {

		StringBuilder sb = new StringBuilder();

		String url = context.getCurrentUrl();

		printHeadline("OPTIONS", sb);

		HttpResponse response = HttpUtils.options(url, context.getRequestHeaders());

		updateContext(response);


		sb.append(OutputUtils.printResponseHeader(context));
		sb.append(OutputUtils.printBody(context));

		return sb.toString();
	}
	
	@CliCommand(value = "get", help = "executes a GET request on the current path")
	public String get(
			@CliOption(key = { "uri" }, mandatory = false, help = "select a link by matching the uri") final String uri,
			@CliOption(key = { "title" }, mandatory = false, help = "select a link by matching the title") final String title,
			@CliOption(key = { "rel" }, mandatory = false, help = "select a link by matching the relation attribute") final String rel) {

		StringBuilder sb = new StringBuilder();

		handleOptions(uri, title, rel, sb);

		printHeadline("GET", sb);

		HttpResponse response = HttpUtils.get(context);

		updateContext(response);

		sb.append(OutputUtils.printRequestHeader(context));
		sb.append(StringUtils.repeat("-", 30)).append("\n");
		sb.append(OutputUtils.printStatus(context));
		sb.append(OutputUtils.printResponseHeader(context));
		sb.append(OutputUtils.printBody(context));

        RestUtils.setLinks(context, response);

		return sb.toString();
	}

	@CliCommand(value = "post", help = "executes a POST request on the current path")
	public String post(
			@CliOption(key = { "uri" }, mandatory = false, help = "select a link by matching the uri") final String uri,
			@CliOption(key = { "title" }, mandatory = false, help = "select a link by matching the title") final String title,
			@CliOption(key = { "rel" }, mandatory = false, help = "select a link by matching the relation attribute") final String rel,
			@CliOption(key = { "data" }, mandatory = true, help = "the POST entity") final String data) {

		StringBuilder sb = new StringBuilder();

		handleOptions(uri, title, rel, sb);

		printHeadline("POST", sb);

		HttpResponse response = HttpUtils.post(context, data);

		updateContext(response);

		sb.append(OutputUtils.printRequestHeader(context));
		sb.append(StringUtils.repeat("-", 30)).append("\n");
		sb.append(OutputUtils.printStatus(context));
		sb.append(OutputUtils.printResponseHeader(context));
		sb.append(OutputUtils.printBody(context));

        RestUtils.setLinks(context, response);

		return sb.toString();
	}

	private void updateContext(HttpResponse response) {
		context.setResponseHeaders(response.getAllHeaders());
		try {
			context.setBody(EntityUtils.toString(response.getEntity()));
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.setStatus(response.getStatusLine());
	}

	private void printHeadline(String method, StringBuilder sb) {
		String headline = "\n> "+method+" '" + context.getCurrentUrl() + "'\n";
		sb.append(StringUtils.repeat("=", headline.length()));
		sb.append(headline);
		sb.append(StringUtils.repeat("=", headline.length())).append("\n\n");
	}

	private void handleOptions(final String uri, final String title,
			final String rel, StringBuilder sb) {
		RestUtils.matchParams(context, uri, title, rel, sb);
	}
	

}
