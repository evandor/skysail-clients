package de.twenty11.skysail.client.cli.commands.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;
import org.codehaus.jackson.map.ObjectMapper;

public class ConsoleUtils {

	public static void write(Context ctx, String msg) {
		IOConsole console = ctx.getIoConsole();
		console.writeOutput(msg + "\n");
	}

	public static void writeBody(Context ctx, HttpResponse returnResponse) {
		if (!CtxUtils.showBody(ctx)) {
			return;
		}
		try {
			IOConsole console = ctx.getIoConsole();
			String msg = EntityUtils.toString(returnResponse.getEntity());
			console.writeOutput(format(msg) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public static void writeHeader(Context ctx, HttpResponse returnResponse) {
		if (!CtxUtils.showHeader(ctx)) {
			return;
		}
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
		if (h.getName().equals("Link")) {
			return "<   " + h.getName() + ": " + h.getValue().replace(",",",\n          ");
		}
		return "<   " + h.getName() + ": " + h.getValue();
	}

}
