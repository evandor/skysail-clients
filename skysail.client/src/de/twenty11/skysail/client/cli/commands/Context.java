package de.twenty11.skysail.client.cli.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.twenty11.skysail.api.responses.Linkheader;

@Component
public class Context {

	private int port = 2016;
	private String host = "localhost";
	private String protocol = "http";
	private List<Linkheader> links = new ArrayList<>();
	private String path = "";

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public List<Linkheader> getLinks() {
		return links;
	}

	public void setLinks(List<Linkheader> links) {
		this.links = links;
	}

	public String getCurrentUrl() {
		return getProtocol() + "://" + getServer() + getPath();	
	}
	
	public String getServer() {
		return getHost() + ":" + getPort();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
