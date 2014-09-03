package de.twenty11.skysail.client.cli.commands.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.clamshellcli.api.Context;

import de.twenty11.skysail.api.responses.Linkheader;

public class LinkUtils {

	private static List<Linkheader> links = new ArrayList<>();

	public static void setCurrentLinks(Context ctx, HttpResponse response) {
		links.clear();
		Header[] linkHeaders = response.getHeaders("Link");
		for (Header linkheader : linkHeaders) {
			if (linkheader.getValue().trim().length() == 0) {
				continue;
			}
			Arrays.stream(linkheader.getValue().split(",")).forEach(l -> links.add(Linkheader.valueOf(l)));
		}
		ctx.putValue("links", links);
	}

	private static String getSingleHeaderRepresentation(Header h) {
		if (h.getName().equals("Link")) {
			return "<   " + h.getName() + ": "
					+ h.getValue().replace(",", ",\n          ");
		}
		return "<   " + h.getName() + ": " + h.getValue();
	}

	public static String getLinkUri(Context ctx, int linkNumber) {
		List<Linkheader> links = (List<Linkheader>) ctx.getValue("links");
		Linkheader linkheader = links.get(linkNumber-1);
		return linkheader.getUri();

	}

}
