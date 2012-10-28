/**
 * 
 */
package de.twenty11.skysail.client.dbviewer.wicket.connections.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import de.twenty11.skysail.client.dbviewer.wicket.connections.ConnectionsProxy;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;
import de.twenty11.skysail.common.responses.Response;
import de.twenty11.skysail.common.responses.SuccessResponse;

@RunWith(MockitoJUnitRunner.class)
/**
 * @author carsten
 *
 */
public class ConnectionsPanelTest {

    WicketTester tester;
    private ConnectionsPanelTestPage page;
    private ConnectionsProxy proxyMock;

    @Before
    public void setUp() {
        tester = new WicketTester();
        proxyMock = mock(ConnectionsProxy.class);
        page = new ConnectionsPanelTestPage(proxyMock);
    }

    @Test
    public void canRenderListOfConnections() {
        tester.startPage(new ConnectionsPanelTestPage(null));
        tester.assertRenderedPage(ConnectionsPanelTestPage.class);
    }

    @Test
    public void rendersListOfConnectionsSuccessfully() {
        final String identifier = "id";
        RestfulConnections answer = new RestfulConnections() {
            @Override
            @Get
            public Response<List<ConnectionDetails>> getConnections() {
                List<ConnectionDetails> data = new ArrayList<ConnectionDetails>();
                data.add(new ConnectionDetails(identifier, "username", "password", "url", "driverClassName"));
                Response<List<ConnectionDetails>> result = new SuccessResponse<List<ConnectionDetails>>(data);
                return result;
            }

            @Override
            @Post
            public Response<?> addConnection(ConnectionDetails entity) {
                return null;
            }
        };
        when(proxyMock.getRestfulConnections()).thenReturn(answer);
        tester.startPage(page);
        tester.assertRenderedPage(ConnectionsPanelTestPage.class);
        List<TagTester> connectionNames = tester.getTagsByWicketId("connectionName");
        assertThat(connectionNames.size(), is(equalTo(1)));
        assertThat(connectionNames.get(0).getValue(), is(equalTo(identifier)));
    }

    @Test
    public void getsProperErrorMessageWhenServerIsDown() {

        RestfulConnections answer = new RestfulConnections() {
            @Override
            @Get
            public Response<List<ConnectionDetails>> getConnections() {
                throw new WicketRuntimeException("Communication Error");
            }

            @Override
            @Post
            public Response<?> addConnection(ConnectionDetails entity) {
                return null;
            }
        };
        when(proxyMock.getRestfulConnections()).thenReturn(answer);
        tester.startPage(page);
        tester.assertRenderedPage(ConnectionsPanelTestPage.class);
        TagTester msgTag = tester.getTagByWicketId("errorMessage");
        assertThat(msgTag.getValue(), is(equalTo("Communication Error")));
    }

    @Test
    public void getsProperErrorMessageWhenAuthentificationFails() {
        RestfulConnections answer = new RestfulConnections() {
            @Override
            @Get
            public Response<List<ConnectionDetails>> getConnections() {
                throw new WicketRuntimeException("Communication Error");
            }

            @Override
            @Post
            public Response<?> addConnection(ConnectionDetails entity) {
                return null;
            }
        };
        when(proxyMock.getRestfulConnections()).thenReturn(answer);
        tester.startPage(page);
        tester.assertRenderedPage(ConnectionsPanelTestPage.class);
        TagTester msgTag = tester.getTagByWicketId("errorMessage");
        assertThat(msgTag.getValue(), is(equalTo("Communication Error")));
    }

}
