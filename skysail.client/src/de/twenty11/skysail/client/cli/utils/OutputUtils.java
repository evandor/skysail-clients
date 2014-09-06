package de.twenty11.skysail.client.cli.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.codehaus.jackson.map.ObjectMapper;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.Context;

public class OutputUtils {

	public static String printRequestHeader(Context context) {
		StringBuilder sb = new StringBuilder();
		
		String msg =context.getRequestHeaders().stream()
				.map(h -> getSingleHeaderRepresentation(">", h))
				.filter(line -> (line != null))
				.collect(Collectors.joining("\n"));
		sb.append(msg + "\n\n");
		return sb.toString();
	}

    public static Object printStatus(Context context) {
        StringBuilder sb = new StringBuilder();
        StatusLine statusLine = context.getStatus();
        sb.append(statusLine.getStatusCode() + ": " + statusLine.getReasonPhrase() + "\n");     
        return sb.toString();
    }

	public static String printResponseHeader(Context context) {
		StringBuilder sb = new StringBuilder();
		
		String msg = Arrays.stream(context.getResponseHeaders())
				.map(h -> getSingleHeaderRepresentation("<", h))
				.filter(line -> (line != null))
				.collect(Collectors.joining("\n"));
		sb.append(msg + "\n\n");
		return sb.toString();
	}

	public static String printBody(Context context) {
//        HttpEntity entity = context.getBody();
		StringBuilder sb = new StringBuilder();
		try {
	        String responseString = context.getBody();
	        if (responseString.trim().length() == 0) {
	            return "";
	        }
            sb.append(format(responseString));
		} catch (ParseException e) {
			sb.append(e.getMessage());
			e.printStackTrace();
		}
		return sb.toString();
	}

	private static String getSingleHeaderRepresentation(String prompt, Header h) {
		if (h.getName().equals("Link")) {
			return formatLinks(h);
		}
		return prompt + "   " + h.getName() + ": " + h.getValue();
	}

    private static String formatLinks(Header h) {
        StringBuilder sb = new StringBuilder("<   " + h.getName() + ": ");
        if (h.getValue().trim().equals("")) {
            return null;
        }
        
        int maxUriLength = getLinkheaderStream(h).map(lh -> lh.getUri().length()).mapToInt(i -> i).max().orElse(0);
        int maxRelationLength = getLinkheaderStream(h).map(lh -> lh.getRel().toString().length()).mapToInt(i -> i).max().orElse(0);
        int maxTitleLength = getLinkheaderStream(h).map(lh -> lh.getTitle().length()).mapToInt(i -> i).max().orElse(0);
        getLinkheaderStream(h).forEach(lh -> {
            sb.append("\n          <").append(lh.getUri()).append(">;").append(StringUtils.repeat(" ", 1 + maxUriLength - lh.getUri().length()));
            sb.append("rel=\"").append(lh.getRel()).append("\";").append(StringUtils.repeat(" ", 1 + maxRelationLength - lh.getRel().toString().length()));
            sb.append("title=\"").append(lh.getTitle()).append("\";").append(StringUtils.repeat(" ", 1 + maxTitleLength - lh.getTitle().length()));
            sb.append("verbs=\"").append(lh.getVerbs()).append("\"");
        });
        return sb.toString();
    }


    private static Stream<Linkheader> getLinkheaderStream(Header h) {
        return Arrays.stream(h.getValue().split(",")).map(link -> Linkheader.valueOf(link));
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
