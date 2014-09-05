package de.twenty11.skysail.client.cli.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;
import de.twenty11.skysail.client.cli.utils.OutputUtils;

@Component
public class RestCommands implements CommandMarker {

	@Autowired
	private Context context;
	
	@CliCommand(value = "get", help = "executes a GET request on the current path")
	public String get() {
		StringBuilder sb = new StringBuilder();
		
		String url = context.getCurrentUrl() + "?media=json";
		String headline = "\n> GET '" + url +"'\n";
		sb.append(StringUtils.repeat("=", headline.length()));
		sb.append(headline);
		sb.append(StringUtils.repeat("=", headline.length())).append("\n\n");
		
		
		List<Header> requestHeaders = new ArrayList<>();
		HttpResponse response = HttpUtils.get(url, requestHeaders);

		sb.append(OutputUtils.printRequestHeader(requestHeaders));
		sb.append(StringUtils.repeat("-", 30)).append("\n");
		sb.append(OutputUtils.printResponseHeader(response));
		sb.append(OutputUtils.printBody(response));
		
		setLinks(response);
		
		return sb.toString();
	}

	private void setLinks(HttpResponse response) {
		context.getLinks().clear();
		Header[] linkHeaders = response.getHeaders("Link");
		for (Header linkheader : linkHeaders) {
			if (linkheader.getValue().trim().length() == 0) {
				continue;
			}
			Arrays.stream(linkheader.getValue().split(",")).forEach(l -> context.getLinks().add(Linkheader.valueOf(l)));
		}
	}

}
