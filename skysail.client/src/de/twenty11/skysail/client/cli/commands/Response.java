package de.twenty11.skysail.client.cli.commands;

import java.util.List;
import java.util.Map;

public class Response {

	private Map<String, List<String>> headers;
	private String body;

	public Response(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public Response withBody(String body) {
		this.body = body;
		return this;
	}
	
	public String getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return new StringBuffer("Headers:\n").append(headers.toString()).append("\nBody:\n").append(body).append("\n").toString();
	}
}
