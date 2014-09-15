package de.twenty11.skysail.client.cli.domain;

import de.twenty11.skysail.client.cli.commands.AssertionCommands.Condition;
import de.twenty11.skysail.client.cli.commands.AssertionCommands.Identifier;


public class LinkAssertion {

    private String expectedValue;
	private Identifier identifier;
	private Condition condition;

    public LinkAssertion(Identifier identifier, String expectedValue, Condition condition) {
        this.identifier = identifier;
		this.expectedValue = expectedValue;
		this.condition = condition;
    }

    public String getExpectedValue() {
        return expectedValue;
    }

	public Condition getCondition() {
		return condition;
	}

	public Identifier getIdentifier() {
		return identifier;
	}
	
    
}
