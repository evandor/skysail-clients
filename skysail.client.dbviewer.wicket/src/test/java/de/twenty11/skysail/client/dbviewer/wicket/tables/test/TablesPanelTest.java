/**
 * 
 */
package de.twenty11.skysail.client.dbviewer.wicket.tables.test;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import de.twenty11.skysail.client.dbviewer.wicket.tables.TablesProxy;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;
import de.twenty11.skysail.common.responses.Response;
import de.twenty11.skysail.common.responses.SuccessResponse;

@RunWith(MockitoJUnitRunner.class)
/**
 * @author carsten
 *
 */
public class TablesPanelTest {

    WicketTester tester;
    private TablesPanelTestPage page;
    private TablesProxy proxyMock;

    private RestfulConnections successAnswer = new RestfulConnections() {
        @Override
        @Get
        public Response<List<ConnectionDetails>> getConnections() {
            List<ConnectionDetails> data = new ArrayList<ConnectionDetails>();
            data.add(new ConnectionDetails("name", "username", "password", "url", "driverClassName"));
            Response<List<ConnectionDetails>> result = new SuccessResponse<List<ConnectionDetails>>(data);
            return result;
        }

        @Override
        @Post
        public Response<?> addConnection(ConnectionDetails entity) {
            return null;
        }
    };

    @Before
    public void setUp() {
        tester = new WicketTester();
        proxyMock = mock(TablesProxy.class);
        page = new TablesPanelTestPage(proxyMock);
    }

    @Test
    public void canRenderListOfConnections() {
        tester.startPage(page);
        tester.assertRenderedPage(TablesPanelTestPage.class);
    }

}
