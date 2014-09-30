package de.twenty11.skysail.client.cli.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.http.Header;

import com.jayway.jsonpath.JsonPath;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.AssertionCommands.Condition;
import de.twenty11.skysail.client.cli.commands.Context;
import de.twenty11.skysail.client.cli.domain.KeyValueAssertion;
import de.twenty11.skysail.client.cli.domain.LinkAssertion;

public class AssertionUtils {

    public static void handleHeader(Context context, KeyValueAssertion headerAssertion, StringBuilder sb) {
        if (headerAssertion == null) {
            return;
        }
        if (matchHeader(context, headerAssertion.getKey(), header -> {
            if (header.getValue().equals(headerAssertion.getExpectedValue())) {
                sb.append("matched " + headerAssertion);
                return true;
            }
            return false;
        }, sb)) {
            return;
        }

        String msg = "expected match '"
                + headerAssertion.getExpectedValue()
                + "' for key '"
                + headerAssertion.getKey()
                + "', but headers were set to "
                + Arrays.stream(context.getResponseHeaders()).map(h -> h.getName() + ": " + h.getValue())
                        .collect(Collectors.joining(",\n"));
        if (System.getProperty("disableAssertions") != null) {
            sb.append(msg);
            return;
        }
        throw new AssertionError(msg);
    }

    private static boolean matchHeader(Context context, String key,
            Predicate<? super Header> matchDefinition, StringBuilder sb) {
        Header[] responseHeaders = context.getResponseHeaders();
        return Arrays.stream(responseHeaders).filter(h -> h.getName().equals(key))
                .filter(matchDefinition).findFirst().isPresent();
    }

    public static void handleHeader(Context context, String headerKey, StringBuilder sb) {
        if (headerKey == null) {
            return;
        }
        if (matchHeader(context, headerKey, header -> {
                sb.append("matched key '" + headerKey + "'");
                return true;
        }, sb)) {
            return;
        }

        String msg = "expected match '"
                + headerKey
                + "', but headers were set to "
                + Arrays.stream(context.getResponseHeaders()).map(h -> h.getName() + ": " + h.getValue())
                        .collect(Collectors.joining(","));
        if (System.getProperty("disableAssertions") != null) {
            sb.append(msg);
            return;
        }
        throw new AssertionError(msg);
    }

    public static void handleBody(Context context, KeyValueAssertion bodyAssertion, StringBuilder sb) {
        if (bodyAssertion == null) {
            return;
        }
        Object match = JsonPath.read(context.getBody(), bodyAssertion.getKey());
        if (match == null) {
            throw new IllegalStateException("assertion didn't find any match");
        }
        if (match instanceof List) {
            throw new IllegalStateException("assertion found a list; try to match only one item");
        }
        if (!match.toString().equals(bodyAssertion.getExpectedValue())) {
            throw new IllegalStateException("expected match '" + bodyAssertion.getExpectedValue() + "', but '"
                    + bodyAssertion.getKey() + "' was '" + match.toString() + "'");
        } else {
            sb.append("matched " + bodyAssertion);
        }
    }

    public static void handleStatus(Context context, Integer expectedStatusCode, StringBuilder sb) {
        if (expectedStatusCode == null) {
            return;
        }
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

    private static Function<Linkheader, String> determineMapping(LinkAssertion linkAssertion) {
        Function<Linkheader, String> mapping = null;
        switch (linkAssertion.getIdentifier()) {
        case URI:
            mapping = lh -> lh.getUri();
            break;
        case REL:
            mapping = lh -> lh.getRel().toString();
            break;
        case TITLE:
            mapping = lh -> lh.getTitle();
            break;
        default:
            throw new IllegalStateException();
        }
        return mapping;
    }

    private static void processResult(LinkAssertion linkAssertion, StringBuilder sb, boolean matchFound) {
        if (linkAssertion.getCondition().equals(Condition.EXISTS)) {
            if (matchFound) {
                sb.append("matched " + linkAssertion);
            } else {
                throw new IllegalStateException("expected match '" + linkAssertion.getExpectedValue()
                        + "', but was not found");
            }
        } else {
            if (!matchFound) {
                sb.append("ok: did not match " + linkAssertion);
            } else {
                throw new IllegalStateException("expected match '" + linkAssertion.getExpectedValue()
                        + "' to be missing, but was found");
            }

        }
    }

    private static boolean foundMatch(Context context, LinkAssertion linkAssertion, Function<Linkheader, String> mapping) {
        return context.getLinks().stream().map(mapping).filter(uri -> uri.equals(linkAssertion.getExpectedValue()))
                .findFirst().isPresent();
    }

}
