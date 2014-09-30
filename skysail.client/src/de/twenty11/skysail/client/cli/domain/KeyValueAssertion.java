package de.twenty11.skysail.client.cli.domain;

public class KeyValueAssertion {

    private String expectedValue;
    private String jsonPath;

    public KeyValueAssertion(String jsonPath, String expectedValue) {
        this.jsonPath = jsonPath;
        this.expectedValue = expectedValue;
    }

    public String getExpectedValue() {
        return expectedValue;
    }
    
    public String getKey() {
        return jsonPath;
    }
    
    @Override
    public String toString() {
        return jsonPath + "=" + expectedValue;
    }
}
