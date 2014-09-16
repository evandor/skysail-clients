package de.twenty11.skysail.client.cli.utils;

import java.util.Arrays;
import java.util.function.Predicate;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.Context;

public class RestUtils {

    public static void matchParams(Context context, String uri, final String title, final String rel, StringBuilder sb) {
        matchGet(context, uri, sb, l -> containsIgnoreCase(l.getUri(), uri));
        matchGet(context, title, sb, l -> containsIgnoreCase(l.getTitle(), title));
        matchGet(context, rel, sb, l -> containsIgnoreCase(l.getRel().toString(), rel.toString()));
    }
    
    private static void matchGet(Context context, final String title, StringBuilder sb,
            Predicate<? super Linkheader> matcher) {
        if (title != null && title.trim().length() > 0) {
            context.getLinks().stream()
                    .filter(lh -> lh.getVerbs().contains(org.restlet.data.Method.GET))
                    .filter(matcher).findFirst().ifPresent(l -> {
                        context.setPath(l.getUri());
                        // sb.append("found link and changed path to '").append(l.getUri()).append("'.\n");
                        });
        }
    }
    
    private static boolean containsIgnoreCase(String string, String sub) {
        return string.toLowerCase().contains(sub.toLowerCase());
    }

    public static void setLinks(Context context, HttpResponse response) {
        context.getLinks().clear();
        Header[] linkHeaders = response.getHeaders("Link");
        for (Header linkheader : linkHeaders) {
            if (linkheader.getValue().trim().length() == 0) {
                continue;
            }
            Arrays.stream(linkheader.getValue().split(",")).forEach(
                    l -> context.getLinks().add(Linkheader.valueOf(l)));
        }
    }

}
