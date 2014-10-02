package de.twenty11.skysail.client.cli.test;

import org.junit.Test;

import com.tngtech.jgiven.junit.ScenarioTest;

public class MyShinyJGivenTest extends ScenarioTest<GivenSomeStage<?>, WhenSomeAction<?>, ThenSomeOutcome<?>> {

    @Test
    public void something_should_happen() {
        given().some_state().and().an_egg();
        when().some_action();
        then().some_outcome();
    }
    
}