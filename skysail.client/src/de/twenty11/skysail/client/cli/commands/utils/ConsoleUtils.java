package de.twenty11.skysail.client.cli.commands.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

public class ConsoleUtils {

	public static void write(Context ctx, String msg) {
		IOConsole console = ctx.getIoConsole();
		console.writeOutput(msg + "\n");
	}

	public static void writeBody(Context ctx, HttpResponse returnResponse) {
		try {
			IOConsole console = ctx.getIoConsole();
			String msg = EntityUtils.toString(returnResponse.getEntity());
			console.writeOutput(msg + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeHeader(Context ctx, HttpResponse returnResponse) {
		IOConsole console = ctx.getIoConsole();
		String msg = Arrays.stream(returnResponse.getAllHeaders())
				.map(h -> getSingleHeaderRepresentation(h))
				.collect(Collectors.joining("\n"));
		console.writeOutput(msg + "\n\n");
	}

	public static void writeStatus(Context ctx, HttpResponse response) {
		IOConsole console = ctx.getIoConsole();
		StatusLine statusLine = response.getStatusLine();
		console.writeOutput(statusLine.getStatusCode() + ": " + statusLine.getReasonPhrase() + "\n");		
	}


	private static String getSingleHeaderRepresentation(Header h) {
		return "<   " + h.getName() + ": " + h.getValue();
	}

}
