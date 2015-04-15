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
public class HtmlCommands implements CommandMarker {

	@Autowired
	private Context context;

	@CliCommand(value = "getPage", help = "executes a HTML Unit page request on the current path")
	public String get(
			@CliOption(key = { "uri" }, mandatory = false, help = "select a link by matching the uri") final String uri,
			@CliOption(key = { "title" }, mandatory = false, help = "select a link by matching the title") final String title,
			@CliOption(key = { "rel" }, mandatory = false, help = "select a link by matching the relation attribute") final String rel) {

	  //  final HtmlPage page = context.getWebClient().getPage("http://htmlunit.sourceforge.net");
	    
		StringBuilder sb = new StringBuilder();

//		handleOptions(uri, title, rel, sb);
//
//		printHeadline("GET", sb);
//
//		HttpResponse response = HttpUtils.get(context);
//
//		updateContext(response);
//
//		sb.append(OutputUtils.printRequestHeader(context));
//		sb.append(StringUtils.repeat("-", 30)).append("\n");
//		sb.append(OutputUtils.printStatus(context));
//		sb.append(OutputUtils.printResponseHeader(context));
//		sb.append(OutputUtils.printBody(context));
//
//        RestUtils.setLinks(context, response);

		return sb.toString();
	}


}
