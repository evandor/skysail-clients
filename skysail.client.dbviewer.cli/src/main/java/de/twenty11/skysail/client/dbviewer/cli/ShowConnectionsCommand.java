package de.twenty11.skysail.client.dbviewer.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;

public class ShowConnectionsCommand implements Command {

	private static final String NAMESPACE = "syscmd";
	private static final String ACTION_NAME = "connections";

	@Override
	public void plug(Context arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(Context ctx) {
//		Map<String,Object> argsMap = (Map<String,Object>) ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
//		try {
//			HttpURLConnection con = (HttpURLConnection) new URL("http://www.heise.de").openConnection();
//			//con.setRequestMethod("POST");
//			//con.getOutputStream().write("LOGIN".getBytes("UTF-8"));
//			InputStream inputStream = con.getInputStream();
//			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//			String inputLine;
//			while ((inputLine = in.readLine()) != null) {
//	            System.out.println(inputLine);
//	        }
//	        in.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}

	@Override
	public Descriptor getDescriptor() {
		return new Command.Descriptor() {
			@Override
			public String getNamespace() {
				return NAMESPACE;
			}

			@Override
			public String getName() {
				return ACTION_NAME;
			}

			@Override
			public String getDescription() {
				return "Prints current date/time";
			}

			@Override
			public String getUsage() {
				return "Type 'time'";
			}

			@Override
			public Map<String, String> getArguments() {
				return Collections.emptyMap();
			}
		};

	}

}
