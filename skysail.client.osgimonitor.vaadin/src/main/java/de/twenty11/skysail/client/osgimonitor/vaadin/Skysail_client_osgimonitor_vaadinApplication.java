package de.twenty11.skysail.client.osgimonitor.vaadin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import de.skysail.client.ClientUtils;
import de.twenty11.skysail.common.grids.GridData;
import de.twenty11.skysail.common.grids.RowData;
import de.twenty11.skysail.common.responses.SkysailResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.representation.Representation;

import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

public class Skysail_client_osgimonitor_vaadinApplication extends Application {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() {
        Window mainWindow = new Window("Skysail_client_osgimonitor_vaadin Application");
        Label label = new Label("Hello Skysail user");
        mainWindow.addComponent(label);

        final Button button = new Button("Push it!");

        button.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                button.setCaption("You pushed it!");
            }
        });

        mainWindow.addComponent(button);

        Window mywindow = new Window("My Window");
        mainWindow.addWindow(mywindow);

        Button closeButton = new Button("Logout");

        closeButton.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                getMainWindow().getApplication().close();
            }
        });

        mainWindow.addComponent(closeButton);

        final Table table = new Table("The Brightest Stars");

        Representation representation;
        try {
            representation = ClientUtils.restletCall("osgi/bundles/");
            SkysailResponse<GridData> response = mapper.readValue(representation.getText(),
                    new TypeReference<SkysailResponse<GridData>>() {
                    });
            GridData payload = response.getData();
            List<RowData> rows = payload.getRows();
            for (RowData rowData : rows) {
                Map<String, String> properties = rowData.getCells();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Define two columns for the built-in container
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Mag", Float.class, null);

        // Add a row the hard way
        Object newItemId = table.addItem();
        Item row1 = table.getItem(newItemId);
        row1.getItemProperty("Name").setValue("Sirius");
        row1.getItemProperty("Mag").setValue(-1.46);

        // Add a few other rows using shorthand addItem()
        table.addItem(new Object[] { "Canopus", -0.72 }, 2);
        table.addItem(new Object[] { "Arcturus", -0.04 }, 3);
        table.addItem(new Object[] { "Alpha Centauri", -0.01 }, 4);

        // Show 5 rows
        table.setPageLength(5);

        mainWindow.addComponent(table);

        setMainWindow(mainWindow);
    }

}
