package de.twenty11.skysail.client.cli.commands;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;

public abstract class AbstractCommand implements Command {

	public abstract Object execute(Context arg0);

	@Override
	public void plug(Context arg0) {
	}


	@Override
	public Descriptor getDescriptor() {
		return null;
	}

}
