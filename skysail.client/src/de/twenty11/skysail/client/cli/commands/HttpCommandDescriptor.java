package de.twenty11.skysail.client.cli.commands;
import java.util.Collections;
import java.util.Map;

import org.clamshellcli.api.Command.Descriptor;


public class HttpCommandDescriptor implements Descriptor {

    private String commandName;
    private String usage;
    private String description;

    public HttpCommandDescriptor(String commandName, String usage, String description) {
        this.commandName = commandName;
        this.usage = usage;
        this.description = description;
    }

    @Override
    public Map<String, String> getArguments() {
        return Collections.emptyMap();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.commandName;
    }

    @Override
    public String getNamespace() {
        return Const.NAMESPACE;
    }

    @Override
    public String getUsage() {
        return this.usage;
    }

}
