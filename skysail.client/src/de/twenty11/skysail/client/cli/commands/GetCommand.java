package de.twenty11.skysail.client.cli.commands;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;

public class GetCommand implements Command {

	public GetCommand() {
		System.out.println("new getCOmmmand");
	}
	
	protected static final String ACTION_NAME = "get";
	private Descriptor descriptor = new HttpCommandDescriptor(ACTION_NAME,
			"get",
			"executes http GET request on current path (see command 'pwd')");

	@Override
	public Response execute(Context ctx) {
		IOConsole console = ctx.getIoConsole();
		String url = Utils.getUrl(ctx) + "?media=json";
		Response response = HttpUtils.get(url);

		

		StringBuilder result = new StringBuilder();
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url)
					.openConnection();
			// con.setRequestMethod("POST");
			// con.getOutputStream().write("LOGIN".getBytes("UTF-8"));

			String userpass = "admin:skysail";
			String basicAuth = "Basic "
					+ javax.xml.bind.DatatypeConverter
							.printBase64Binary(userpass.getBytes());
			con.setRequestProperty("Authorization", basicAuth);

			Map<String, List<String>> headerFields = con.getHeaderFields();
			response = new Response(null);

			InputStream inputStream = con.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				// console.writeOutput(inputLine);
				result.append(inputLine);
			}
			in.close();
			response.withBody(result.toString());
			console.writeOutput(response.toString());
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Descriptor getDescriptor() {
		return this.descriptor;
	}

	@Override
	public void plug(Context arg0) {
	}

}
