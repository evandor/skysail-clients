//package de.twenty11.skysail.client.dbviewer.wicket;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.wicket.markup.html.basic.Label;
//import org.apache.wicket.markup.repeater.RepeatingView;
//import org.apache.wicket.model.IModel;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;
//import org.restlet.representation.Representation;
//
//import de.twenty11.skysail.client.common.ClientUtils;
//import de.twenty11.skysail.common.grids.GridData;
//import de.twenty11.skysail.common.grids.RowData;
//import de.twenty11.skysail.common.responses.SkysailResponse;
//
//public class ConnectionPanel extends TemplatePanel {
//
//    private static final long serialVersionUID = 7839954296496134865L;
//
//    /** deals with json objects */
//    private ObjectMapper mapper = new ObjectMapper();
//
//    public ConnectionPanel(String id) {
//        super(id);
//
//        RepeatingView rv = new RepeatingView("rv");
//        add(rv);
//        for (int i = 0; i < 5; i++) {
//            rv.add(new Label(String.valueOf(i), "Value " + i));
//        }
//        
//        try {
//            Representation representation = ClientUtils.restletCall("osgi/services/");
//            SkysailResponse<GridData> response = mapper.readValue(representation.getText(), 
//                    new TypeReference<SkysailResponse<GridData>>() { });
//            GridData payload = response.getData();
//            List<RowData> gridData = payload.getRows();
//            List<String> result = new ArrayList<String>();
//            for (RowData rowData : gridData) {
//                List<Object> columnData = rowData.getColumnData();
//                result.add((String)columnData.get(1));
//            }
//            //return result.toArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            //return new String[] { "Error" };
//        }
//
//
//    }
//
//    public ConnectionPanel(String id, IModel<?> model) {
//        super(id, model);
//    }
//
//}
