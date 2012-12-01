package de.twenty11.skysail.client.dbviewer.cli;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ConnectCommandTest extends CommandTest {
	
    private ConnectCommand cmd;

    public ConnectCommandTest() {
        cmd = new ConnectCommand();
    }

    @Before
    public void setUp() {
        // needs to be done as we get same ctx Object everytime
        reset(ctx);
    }

    @Test
    public void should_give_errormessage_if_server_is_unknown() throws Exception {
        //setArgument("http://unknownserver");
        String msg = (String) cmd.execute(ctx);
        assertThat(msg, is(equalTo("not connected")));
    }

//    @Test
//    public void should_set_connect_to_true_if_server_is_responsive() throws Exception {
//        setArgument("http://www.heise.de");
//        cmd.execute(ctx);
//        assertThat(Utils.isConnected(ctx), is(equalTo(true)));
//    }


}
