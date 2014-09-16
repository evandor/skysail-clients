package de.twenty11.skysail.client.cli.commands;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

//@Component
public class LinksCommands implements CommandMarker {

	@Autowired
	private Context context;
	
	@CliCommand(value = "links", help = "shows the available links to navigate to")
	public String links() {
		return "";//context.getLinks().stream().map(link -> link.toString()).collect(Collectors.joining("\n"));
	}

}
