package de.twenty11.skysail.client.cli.utils;

import java.util.List;
import java.util.Optional;

import org.apache.http.Header;

public class ContextUtils {

    public static void addOrSetHeader(List<Header> requestHeaders, Header header) {
        Optional<Header> existingHeader = requestHeaders.stream().filter(h -> header.getName().equals(h.getName())).findFirst();
        existingHeader.ifPresent(h -> requestHeaders.remove(h));
        requestHeaders.add(header);
    }

    public static void removeHeader(List<Header> requestHeaders, String header) {
      Optional<Header> existingHeader = requestHeaders.stream().filter(h -> header.equals(h.getName())).findFirst();
      existingHeader.ifPresent(h -> requestHeaders.remove(h));
    }

}
