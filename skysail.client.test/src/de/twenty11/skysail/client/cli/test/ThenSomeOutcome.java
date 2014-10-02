package de.twenty11.skysail.client.cli.test;

import com.tngtech.jgiven.Stage;

public class ThenSomeOutcome<SELF extends ThenSomeOutcome<?>> extends Stage<SELF> {
    public SELF some_outcome() {
        return self();
    }
}