package de.twenty11.skysail.client.cli.utils;

import java.util.List;
import java.util.function.Function;

import com.jayway.jsonpath.JsonPath;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.AssertionCommands.Condition;
import de.twenty11.skysail.client.cli.commands.Context;
import de.twenty11.skysail.client.cli.domain.JsonAssertion;
import de.twenty11.skysail.client.cli.domain.LinkAssertion;

public class AssertionUtils {

    public static void handleBody(Context context, JsonAssertion bodyAssertion, StringBuilder sb) {
        if (bodyAssertion == null) {
            return;
        }
        Object match = JsonPath.read(context.getBody(),
                bodyAssertion.getJsonPath());
        if (match == null) {
            throw new IllegalStateException("assertion didn't find any match");
        }
        if (match instanceof List) {
            throw new IllegalStateException(
                    "assertion found a list; try to match only one item");
        }
        if (!match.toString().equals(bodyAssertion.getExpectedValue())) {
            throw new IllegalStateException("expected match '"
                    + bodyAssertion.getExpectedValue() + "', but '"
                    + bodyAssertion.getJsonPath() + "' was '"
                    + match.toString() + "'");
        } else {
            sb.append("matched " + bodyAssertion);
        }
    }

    public static void handleStatus(Context context, Integer expectedStatusCode, StringBuilder sb) {
        if (context.getStatus() == null) {
            throw new AssertionError("context status code was null!");
        }
        int currentStatusCode = context.getStatus().getStatusCode();
        if (!expectedStatusCode.equals(currentStatusCode)) {
           throw new AssertionError("expected code " + expectedStatusCode + ", but was " + currentStatusCode);
        }
        sb.append("matched status code " + expectedStatusCode);
    }
    
    public static void handleLinks(Context context, LinkAssertion linkAssertion, StringBuilder sb) {
        if (linkAssertion == null) {
            return;
        }
        Function<Linkheader, String> mapping = determineMapping(linkAssertion);
        boolean matchFound = foundMatch(context, linkAssertion, mapping);
        processResult(linkAssertion, sb, matchFound);        
    }

    private static Function<Linkheader, String> determineMapping(
            LinkAssertion linkAssertion) {
        Function<Linkheader, String> mapping = null;
        switch (linkAssertion.getIdentifier()) {
        case URI:
            mapping= lh -> lh.getUri();
            break;
        case REL:
            mapping= lh -> lh.getRel().toString();
            break;
        case TITLE:
            mapping= lh -> lh.getTitle();
            break;
        default:
            throw new IllegalStateException();
        }
        return mapping;
    }

    private static void processResult(LinkAssertion linkAssertion, StringBuilder sb,
            boolean matchFound) {
        if (linkAssertion.getCondition().equals(Condition.EXISTS)) {
            if (matchFound) {
                sb.append("matched " + linkAssertion);
            } else {
                throw new IllegalStateException("expected match '"
                    + linkAssertion.getExpectedValue() + "', but was not found");
            }
        } else {
            if (!matchFound) {
                sb.append("ok: did not match " + linkAssertion);
            } else {
                throw new IllegalStateException("expected match '"
                    + linkAssertion.getExpectedValue() + "' to be missing, but was found");
            }
            
        }
    }

    private static boolean foundMatch(Context context, LinkAssertion linkAssertion,
            Function<Linkheader, String> mapping) {
        return context.getLinks().stream().map(mapping)
                .filter(uri -> uri.equals(linkAssertion.getExpectedValue()))
                .findFirst().isPresent();
    }
   
}
