package de.twenty11.skysail.client.dbviewer.cli;

import org.clamshellcli.api.Context;
import org.clamshellcli.api.Prompt;

public class DbViewerPrompt implements Prompt {

	public String getValue(Context ctx) {
		return "skysail DbViewer> ";
	}

	public void plug(Context plug) {
		// nothing to do
	}

}
