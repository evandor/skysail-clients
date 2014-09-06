package de.twenty11.skysail.client.cli.domain;

public class HeaderDefinition {

    private String value;
    private String name;

    public HeaderDefinition(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }
}
