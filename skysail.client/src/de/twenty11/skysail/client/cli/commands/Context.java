package de.twenty11.skysail.client.cli.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.api.responses.Linkheader;

@Component
public class Context {

	private int port = 2016;
	private String host = "localhost";
	private String protocol = "http";
	private List<Linkheader> links = new ArrayList<>();
	private String path = "";
    private List<Header> requestHeaders = new ArrayList<>();
    private String body;
    private Header[] responseHeaders;
    private StatusLine status;
    private boolean showRequestHeaders = true;
    private boolean showResponseHeaders = true;
    private Boolean showBody = true;

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

    public void setRequestHeaders(List<Header> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public void setBody(String string) {
        this.body = string;
    }

    public void setResponseHeaders(Header[] allHeaders) {
        this.responseHeaders = allHeaders;
    }
    
    public List<Header> getRequestHeaders() {
        return requestHeaders;
    }
    
    public Header[] getResponseHeaders() {
        return responseHeaders;
    }
    
    public String getBody() {
        return body;
    }

    public void setStatus(StatusLine statusLine) {
        this.status = statusLine;
    }
    
    public StatusLine getStatus() {
        return status;
    }

    public void addOrSetHeader(Header header) {
//        Optional<Header> existingHeader = getRequestHeaders().stream().filter(h -> header.getName().equals(h.getName())).findFirst();
//        existingHeader.ifPresent(h -> getRequestHeaders().remove(h));
//        getRequestHeaders().add(header);
    }

    public void removeHeader(String header) {
//        Optional<Header> existingHeader = getRequestHeaders().stream().filter(h -> header.equals(h.getName())).findFirst();
//        existingHeader.ifPresent(h -> getRequestHeaders().remove(h));
    }

    public void showRequestHeaders(boolean showRequestHeaders) {
        this.showRequestHeaders = showRequestHeaders;
    }

    public boolean isShowRequestHeaders() {
        return this.showRequestHeaders;
    }

    public void showResponseHeaders(Boolean showResponseHeaders) {
        this.showResponseHeaders  = showResponseHeaders;
    }

    public boolean isShowResponseHeaders() {
        return this.showResponseHeaders;
    }

    public void showBody(Boolean showBody) {
        this.showBody = showBody;

    }
    public boolean isShowBody() {
        return this.showBody;
    }
}
