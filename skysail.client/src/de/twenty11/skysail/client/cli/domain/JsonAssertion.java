package de.twenty11.skysail.client.cli.domain;

public class JsonAssertion {

    private String expectedValue;
    private String jsonPath;

    public JsonAssertion(String jsonPath, String expectedValue) {
        this.jsonPath = jsonPath;
        this.expectedValue = expectedValue;
    }

    public String getExpectedValue() {
        return expectedValue;
    }
    
    public String getJsonPath() {
        return jsonPath;
    }
    
    @Override
    public String toString() {
        return jsonPath + "=" + expectedValue;
    }
}
