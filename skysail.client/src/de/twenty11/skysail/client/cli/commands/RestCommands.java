package de.twenty11.skysail.client.cli.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;
import de.twenty11.skysail.client.cli.utils.OutputUtils;

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

        matchGet(uri, sb, l -> containsIgnoreCase(l.getUri(),uri));
        matchGet(title, sb, l -> containsIgnoreCase(l.getTitle(),title));
        matchGet(rel, sb, l -> containsIgnoreCase(l.getRel().toString(),rel.toString()));

        String url = context.getCurrentUrl();// + "?media=json";
        String headline = "\n> GET '" + url + "'\n";
        sb.append(StringUtils.repeat("=", headline.length()));
        sb.append(headline);
        sb.append(StringUtils.repeat("=", headline.length())).append("\n\n");

        HttpResponse response = HttpUtils.head(url, context.getRequestHeaders());

        context.setResponseHeaders(response.getAllHeaders());
        context.setBody("");
        context.setStatus(response.getStatusLine());

        sb.append(OutputUtils.printRequestHeader(context));
        sb.append(StringUtils.repeat("-", 30)).append("\n");
        sb.append(OutputUtils.printStatus(context));
        sb.append(OutputUtils.printResponseHeader(context));
        //sb.append(OutputUtils.printBody(context));

        setLinks(response);

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

		setLinks(response);

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

		setLinks(response);

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
		matchGet(uri, sb, l -> containsIgnoreCase(l.getUri(), uri));
		matchGet(title, sb, l -> containsIgnoreCase(l.getTitle(), title));
		matchGet(rel, sb,
				l -> containsIgnoreCase(l.getRel().toString(), rel.toString()));
	}

	private void matchGet(final String title, StringBuilder sb,
			Predicate<? super Linkheader> matcher) {
		if (title != null && title.trim().length() > 0) {
			context.getLinks().stream()
					.filter(lh -> lh.getVerbs().contains(org.restlet.data.Method.GET))
					.filter(matcher).findFirst().ifPresent(l -> {
						context.setPath(l.getUri());
						// sb.append("found link and changed path to '").append(l.getUri()).append("'.\n");
						});
		}
	}

	private void setLinks(HttpResponse response) {
		context.getLinks().clear();
		Header[] linkHeaders = response.getHeaders("Link");
		for (Header linkheader : linkHeaders) {
			if (linkheader.getValue().trim().length() == 0) {
				continue;
			}
			Arrays.stream(linkheader.getValue().split(",")).forEach(
					l -> context.getLinks().add(Linkheader.valueOf(l)));
		}
	}

	private boolean containsIgnoreCase(String string, String sub) {
		return string.toLowerCase().contains(sub.toLowerCase());
	}

}
