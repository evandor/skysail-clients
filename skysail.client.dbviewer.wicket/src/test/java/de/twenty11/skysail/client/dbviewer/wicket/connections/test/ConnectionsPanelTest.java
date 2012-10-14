/**
 * 
 */
package de.twenty11.skysail.client.dbviewer.wicket.connections.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;

@RunWith(MockitoJUnitRunner.class)
/**
 * @author carsten
 *
 */
public class ConnectionsPanelTest {

    WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester();
    }

    @Test
    @Ignore
    public void canRenderListOfConnections() {
        tester.startPage(ConnectionsPanelTestPage.class);
        tester.assertRenderedPage(ConnectionsPanelTestPage.class);
    }

    @Test
    public void getsProperErrorMessageWhenServerIsDown() {
        ClientResource clientResource = mock(ClientResource.class);
        when(clientResource.wrap(RestfulConnections.class)).thenReturn(null);
        tester.startPage(ConnectionsPanelTestPage.class);
        tester.assertRenderedPage(ConnectionsPanelTestPage.class);
        TagTester msgTag = tester.getTagByWicketId("connectionsMessage");
        assertThat(msgTag.getValue(), is(equalTo("Communication Error")));
    }

    @Test
    public void getsProperErrorMessageWhenAuthentificationFails() {
        RestfulConnections mockedRestfulConnections = mock(RestfulConnections.class);
        when(mockedRestfulConnections.getConnections()).thenThrow(new IllegalArgumentException("test"));
        tester.startPage(ConnectionsPanelTestPage.class);
        tester.assertRenderedPage(ConnectionsPanelTestPage.class);
        TagTester msgTag = tester.getTagByWicketId("connectionsMessage");
        assertThat(msgTag.getValue(), is(equalTo("Communication Error")));
    }

}
