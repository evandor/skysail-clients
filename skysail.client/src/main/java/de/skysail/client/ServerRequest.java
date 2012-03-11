package de.skysail.client;

import org.restlet.data.Cookie;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

public class ServerRequest {

    private String urlPart;
    
    private String query;
    
    private String fragment;
    
    private Series<Cookie> cookies;
    
    public ServerRequest(String url) {
        this(url, null);
    }
    
    public ServerRequest(String url, String query) {
        this.urlPart = url;
        this.query = query;
    }

    public Representation call(MediaType mediatype) {
        String url = SkysailClientApplication.getRestletUrl(urlPart, query, fragment);
        ClientResource cr = new ClientResource(url);
        cr.setCookies(cookies);
        return cr.get(mediatype);
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    public void setFragments(String fragment) {
        this.fragment = fragment;
    }
    
    public void setCookies(Series<Cookie> cookies) {
        this.cookies = cookies;
    }
    
    
}
