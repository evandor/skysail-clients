package de.twenty11.skysail.client.cli.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class OutputUtils {

	public static String printRequestHeader(List<Header> requestHeaders) {
		StringBuilder sb = new StringBuilder();
		
		String msg =requestHeaders.stream()
				.map(h -> getSingleHeaderRepresentation(h))
				.collect(Collectors.joining("\n"));
		sb.append(msg + "\n\n");
		return sb.toString();
	}
	
	public static String printResponseHeader(HttpResponse response) {
		StringBuilder sb = new StringBuilder();
		StatusLine statusLine = response.getStatusLine();
		sb.append(statusLine.getStatusCode() + ": " + statusLine.getReasonPhrase() + "\n");		
		
		String msg = Arrays.stream(response.getAllHeaders())
				.map(h -> getSingleHeaderRepresentation(h))
				.collect(Collectors.joining("\n"));
		sb.append(msg + "\n\n");
		return sb.toString();
	}

	public static String printBody(HttpResponse response) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(format(EntityUtils.toString(response.getEntity())));
		} catch (ParseException | IOException e) {
			sb.append(e.getMessage());
			e.printStackTrace();
		}
		return sb.toString();
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
			return "Original Message:\n" + msg;
		}	
	}

}
