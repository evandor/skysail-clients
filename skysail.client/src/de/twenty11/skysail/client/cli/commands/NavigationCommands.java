package de.twenty11.skysail.client.cli.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class NavigationCommands implements CommandMarker {

	@Autowired
	private Context context;

	@CliCommand(value = "setProtocol", help = "set protocol, i.e. 'http'")
	public String setProtocol(
			@CliOption(key = { "", "protocol" }, mandatory = true, help = "the protocol") final String protocol) {
		context.setProtocol(protocol);
		return "protocol set to " + protocol + ".";
	}

	@CliCommand(value = "setHost", help = "set host.")
	public String setHost(
			@CliOption(key = { "", "host" }, mandatory = true, help = "the host") final String host) {
		context.setHost(host);
		return "host set to " + host + ".";
	}

	@CliCommand(value = "setPort", help = "set port.")
	public String setPort(
			@CliOption(key = { "", "port" }, mandatory = true, help = "the port") final Integer port) {
		context.setPort(port);
		return "port set to " + port + ".";
	}

	@CliCommand(value = "pwd", help = "pwd")
	public String pwd() {
		return context.getServer() + context.getPath();
	}

	@CliCommand(value = "cd", help = "change directory")
	public String cd(
			@CliOption(key = { "", "dir" }, mandatory = false, help = "a absolute or relative directory") final String dir) {
		String currentPath = context.getPath();
		if (dir == null || dir.trim().equals("")) {
			context.setPath("");
		} else if (dir.startsWith("/")) {
			context.setPath(dir);
		} else if (dir.startsWith("..")) {
			String[] segments = currentPath.split("/");
			if (segments.length == 0) {
				context.setPath("");
			} else if (segments.length == 1) {
				context.setPath("");
			} else {
				context.setPath(Arrays.asList(segments)
						.subList(0, segments.length - 1).stream()
						.collect(Collectors.joining("/")));
			}
		} else {
			context.setPath(currentPath.endsWith("/") ? currentPath + dir
					: currentPath + "/" + dir);
		}
		return "path was set to '" + context.getPath() + "'";
	}

}
