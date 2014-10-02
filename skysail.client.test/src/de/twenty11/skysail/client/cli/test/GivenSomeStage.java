package de.twenty11.skysail.client.cli.test;

import java.util.ArrayList;
import java.util.List;

import com.tngtech.jgiven.Stage;

public class GivenSomeStage<SELF extends GivenSomeStage<?>> extends Stage<SELF> {
    
    List<String> ingredients = new ArrayList<String>();
    
    public SELF some_state() {
        return self();
    }
    
    public SELF an_egg() {
        ingredients.add("Egg");
        return self();
     }
}
