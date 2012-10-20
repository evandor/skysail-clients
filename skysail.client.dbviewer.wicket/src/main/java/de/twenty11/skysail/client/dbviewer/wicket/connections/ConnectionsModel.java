//package de.twenty11.skysail.client.dbviewer.wicket.connections;
//
//import java.util.Collections;
//import java.util.List;
//
//import org.apache.wicket.Component;
//import org.apache.wicket.markup.html.basic.Label;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IComponentAssignedModel;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.IWrapModel;
//import org.apache.wicket.model.LoadableDetachableModel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
//import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;
//import de.twenty11.skysail.common.responses.Response;
//
//public class ConnectionsModel extends LoadableDetachableModel<List<ConnectionDetails>> implements IComponentAssignedModel<List<ConnectionDetails>> {
//
//    private static final long serialVersionUID = 7234968751585133910L;
//    private static final Logger logger = LoggerFactory.getLogger(ConnectionsModel.class);
//    private Label messageLabel;
//    private ConnectionsProxy connectionsProxy;
//    private BookmarkablePageLink<String> addNewConnectionButton;
//    private Component panel;
//    private Component component;
//
//    @Override
//    protected List<ConnectionDetails> load() {
//
//        try {
//            RestfulConnections proxy = connectionsProxy.getRestfulConnections();
//            Response<List<ConnectionDetails>> response = proxy.getConnections();
//            logger.info("found {} connections", response.getData().size());
//            addNewConnectionButton.setVisible(true);
//            return response.getData();
//        } catch (Exception e) {
//            logger.error("Exception thrown trying to access skysail server: {}", e.getMessage(), e);
//            //panel.getMessageLabel().setVisible(true);
//            //messageLabel.setVisible(true);
//            //panel.getPage().get("connectionsPanel:" + ConnectionsPanel.CONNECTIONS).setVisible(false);
//            //IModel<String> defaultModel = (IModel<String>) messageLabel.getDefaultModel();
//            //defaultModel.setObject(e.getMessage());
//            //addNewConnectionButton.setVisible(false);
//            return Collections.emptyList();
//        }
//    }
//
//    public void setConnectionsProxy(ConnectionsProxy proxy) {
//        this.connectionsProxy = proxy;
//    }
//
//    public void setNewConnectionButton(BookmarkablePageLink addNewConnectionButton) {
//        this.addNewConnectionButton = addNewConnectionButton;
//    }
//
//    @Override
//    public IWrapModel<List<ConnectionDetails>> wrapOnAssignment(final Component component) {
//        this.component = component;
//        return new IWrapModel<List<ConnectionDetails>>() {
//
//            @Override
//            public List<ConnectionDetails> getObject() {
//                try {
//                    RestfulConnections proxy = connectionsProxy.getRestfulConnections();
//                    Response<List<ConnectionDetails>> response = proxy.getConnections();
//                    logger.info("found {} connections", response.getData().size());
//                    addNewConnectionButton.setVisible(true);
//                    return response.getData();
//                } catch (Exception e) {
//                    logger.error("Exception thrown trying to access skysail server: {}", e.getMessage(), e);
//                    //messageLabel.setVisible(true);
//                    //Object defaultModelObject = component.getDefaultModelObject();
//                    component.getPage().get("connectionsPanel:" + ConnectionsPanel.CONNECTIONS).setVisible(false);
//                    //IModel<String> defaultModel = (IModel<String>) messageLabel.getDefaultModel();
//                    //defaultModel.setObject(e.getMessage());
//                    //addNewConnectionButton.setVisible(false);
//                    return Collections.emptyList();
//                }
//            }
//
//            @Override
//            public void setObject(List<ConnectionDetails> object) {
//                // TODO Auto-generated method stub
//                
//            }
//
//            @Override
//            public void detach() {
//            }
//
//            @Override
//            public IModel<?> getWrappedModel() {
//                // TODO Auto-generated method stub
//                return null;
//            }
//            
//        };
//    }
//
//
//}
