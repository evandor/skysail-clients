package de.skysail.client;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class ClientUtils {

	/**
	 * create a restlet request from a url path, a url query and a url fragment
	 * and send it to the skysail restlet server. 
	 * 
	 * @param urlPart
	 * @param query
	 * @param fragment
	 * @return
	 * @throws IOException
	 */
	public static Representation restletCall(MediaType mediatype, String urlPart, String query, String fragment)
			throws IOException {
		
		String url = SkysailClientApplication.getRestletUrl(urlPart, query, fragment);
		ClientResource cr = new ClientResource(url);
		return cr.get(mediatype);
	}
	
	public static Representation restletCall(String urlPart, String query, String fragment)
			throws IOException {
		return restletCall(MediaType.APPLICATION_JSON, urlPart, query, fragment);
	}

	public static Representation restletCall(String urlPart, String query)
			throws IOException {
		return restletCall(MediaType.APPLICATION_JSON, urlPart, query, null);
	}

	public static Representation restletCall(String urlPart)
			throws IOException {
		return restletCall(MediaType.APPLICATION_JSON, urlPart, null, null);
	}

	public static Representation restletCall(MediaType mediatype, String urlPart, String query)
			throws IOException {
		return restletCall(mediatype, urlPart, query, null);
	}

	public static Representation restletCall(MediaType mediatype, String urlPart)
			throws IOException {
		return restletCall(mediatype, urlPart, null, null);
	}

}
