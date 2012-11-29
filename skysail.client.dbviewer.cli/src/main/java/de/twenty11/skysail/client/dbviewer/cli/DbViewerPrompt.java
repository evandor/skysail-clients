package de.twenty11.skysail.client.dbviewer.cli;

import org.clamshellcli.api.Context;
import org.clamshellcli.api.Prompt;

public class DbViewerPrompt implements Prompt {

	private static final String PROMPT = "jmx-cli > ";

	public String getValue(Context ctx) {
		return PROMPT;
	}

	public void plug(Context plug) {
		// nothing to do
	}

}
