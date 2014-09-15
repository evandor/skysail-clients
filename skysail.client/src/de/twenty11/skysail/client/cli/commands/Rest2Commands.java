//package de.twenty11.skysail.client.cli.commands;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.function.Predicate;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.Header;
//import org.apache.http.HttpResponse;
//import org.apache.http.ParseException;
//import org.apache.http.util.EntityUtils;
//import org.restlet.data.Method;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.shell.core.CommandMarker;
//import org.springframework.shell.core.annotation.CliCommand;
//import org.springframework.shell.core.annotation.CliOption;
//import org.springframework.stereotype.Component;
//
//import de.twenty11.skysail.api.responses.Linkheader;
//import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;
//import de.twenty11.skysail.client.cli.utils.OutputUtils;
//
//@Component
//public class Rest2Commands implements CommandMarker {
//
//    @Autowired
//    private Context context;
//
//    
//    private void matchGet(final String title, StringBuilder sb, Predicate<? super Linkheader> matcher) {
//        if (title != null && title.trim().length() > 0) {
//            context.getLinks().stream().filter(lh -> lh.getVerbs().contains(Method.GET)).filter(matcher).findFirst().ifPresent(l -> {
//                context.setPath(l.getUri());
//                //sb.append("found link and changed path to '").append(l.getUri()).append("'.\n");
//            });
//        }
//    }
//
//    private void setLinks(HttpResponse response) {
//        context.getLinks().clear();
//        Header[] linkHeaders = response.getHeaders("Link");
//        for (Header linkheader : linkHeaders) {
//            if (linkheader.getValue().trim().length() == 0) {
//                continue;
//            }
//            Arrays.stream(linkheader.getValue().split(",")).forEach(l -> context.getLinks().add(Linkheader.valueOf(l)));
//        }
//    }
//
//    private boolean containsIgnoreCase(String string, String sub) {
//        return string.toLowerCase().contains(sub.toLowerCase());
//    }
//
//}
