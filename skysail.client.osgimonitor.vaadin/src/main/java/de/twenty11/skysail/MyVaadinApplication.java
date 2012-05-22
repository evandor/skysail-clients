/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.twenty11.skysail;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinApplication extends Application {
    private Window window;

    @Override
    public void init() {
        window = new Window("My Vaadin Application");
        setMainWindow(window);
        Button button = new Button("Click Me!!!");
        button.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                window.addComponent(new Label("Thank you for clicking"));
            }
        });
        window.addComponent(button);

        /* Create the table with a caption. */
        Table table = new Table("This is my Table");

        /*
         * Define the names and data types of columns. The "default value" parameter is meaningless here.
         */
        table.addContainerProperty("First Name", String.class, null);
        table.addContainerProperty("Last Name", String.class, null);
        table.addContainerProperty("Year", Integer.class, null);

        /* Add a few items in the table. */
        table.addItem(new Object[] { "Nicolaus", "Copernicus", new Integer(1473) }, new Integer(1));
        table.addItem(new Object[] { "Tycho", "Brahe", new Integer(1546) }, new Integer(2));
        table.addItem(new Object[] { "Giordano", "Bruno", new Integer(1548) }, new Integer(3));
        table.addItem(new Object[] { "Galileo", "Galilei", new Integer(1564) }, new Integer(4));
        table.addItem(new Object[] { "Johannes", "Kepler", new Integer(1571) }, new Integer(5));
        table.addItem(new Object[] { "Isaac", "Newton", new Integer(1643) }, new Integer(6));

        window.addComponent(table);
    }

}
