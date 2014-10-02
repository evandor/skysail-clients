package de.twenty11.skysail.client.cli.test;

import com.tngtech.jgiven.Stage;

public class WhenSomeAction<SELF extends WhenSomeAction<?>> extends Stage<SELF> {
    public SELF some_action() {
        return self();
    }
}

