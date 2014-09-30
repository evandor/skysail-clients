package de.twenty11.skysail.client.cli.utils;

import java.util.stream.Collectors;

import de.twenty11.skysail.client.cli.commands.Context;

public class LinksUtils {

    public static String links(Context context) {
        return context.getLinks().stream().map(link -> link.toString()).collect(Collectors.joining("\n"));
    }

}
