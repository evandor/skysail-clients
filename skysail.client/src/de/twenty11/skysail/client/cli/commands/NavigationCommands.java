package de.twenty11.skysail.client.cli.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;

@Component
public class SetHostCommand implements CommandMarker {

	private int port = 2016;
	private String host = "localhost";
	private String protocol = "http";
	private List<Linkheader> links = new ArrayList<>();

	@CliCommand(value = "setHost", help = "set host.")
	public String setHost(
			@CliOption(key = { "", "host" }, mandatory = true, help = "the host") final String host) {
		this.host = host;
		return "host set to " + host + ".";
	}
	
	@CliCommand(value = "setPort", help = "set port.")
	public String setPort(
			@CliOption(key = { "", "port" }, mandatory = true, help = "the port") final Integer port) {
		this.port = port;
		return "port set to " + port + ".";
	}
	
	@CliCommand(value = "setProtocol", help = "set protocol, i.e. 'http'")
	public String setProtocol(
			@CliOption(key = { "", "protocol" }, mandatory = true, help = "the protocol") final String protocol) {
		this.protocol = protocol;
		return "protocol set to " + protocol + ".";
	}
	
	@CliCommand(value = "pwd", help = "pwd")
	public String pwd() {
		return getServer();
	}
	
	@CliCommand(value = "get", help = "executes a GET request on the current path")
	public String get() {
		StringBuilder sb = new StringBuilder();
		
		String url = getProtocol() + "://" + getServer() + getPath() + "?media=json";
		sb.append("> GET '" + url +"'\n");
		
		HttpResponse response = HttpUtils.get(url);
		StatusLine statusLine = response.getStatusLine();
		sb.append(statusLine.getStatusCode() + ": " + statusLine.getReasonPhrase() + "\n");		
		
		String msg = Arrays.stream(response.getAllHeaders())
				.map(h -> getSingleHeaderRepresentation(h))
				.collect(Collectors.joining("\n"));
		sb.append(msg + "\n\n");
		
		try {
			sb.append(format(EntityUtils.toString(response.getEntity())));
		} catch (ParseException | IOException e) {
			sb.append(e.getMessage());
			e.printStackTrace();
		}
		
		links .clear();
		Header[] linkHeaders = response.getHeaders("Link");
		for (Header linkheader : linkHeaders) {
			if (linkheader.getValue().trim().length() == 0) {
				continue;
			}
			Arrays.stream(linkheader.getValue().split(",")).forEach(l -> links.add(Linkheader.valueOf(l)));
		}
		
		return sb.toString();
	}

	private String getServer() {
		return host + ":" + port;
	}

	private String getPath() {
		return "";
	}

	private String getProtocol() {
		return protocol;
	}

	private static String getSingleHeaderRepresentation(Header h) {
		if (h.getName().equals("Link")) {
			return "<   " + h.getName() + ": " + h.getValue().replace(",",",\n          ");
		}
		return "<   " + h.getName() + ": " + h.getValue();
	}
	
	private static String format(String msg) {
		ObjectMapper mapper = new ObjectMapper();
		Object json;
		try {
			json = mapper.readValue(msg, Object.class);
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}	
	}
	
	
}
