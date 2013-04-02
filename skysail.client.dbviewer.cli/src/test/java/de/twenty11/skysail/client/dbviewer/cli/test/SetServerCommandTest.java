package de.twenty11.skysail.client.dbviewer.cli.test;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.twenty11.skysail.client.dbviewer.cli.CommandTest;
import de.twenty11.skysail.client.dbviewer.cli.SetServerCommand;
import de.twenty11.skysail.client.dbviewer.cli.internal.Utils;

public class SetServerCommandTest extends CommandTest {

    private SetServerCommand cmd;

    public SetServerCommandTest() {
        cmd = new SetServerCommand();
    }

    @Before
    public void setUp() {
        reset(ctx);
    }

    @Test
    public void should_throw_exception_for_no_url_parameter() throws Exception {
        setArgument("server:2011");
        Object result = cmd.execute(ctx);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void should_set_server_url_to_provided_value() throws Exception {
        setArgument("http://server:2011");
        cmd.execute(ctx);
        assertThat(Utils.getServer(ctx), is(equalTo("http://server:2011")));
    }

    @Test
    public void should_remove_training_slash() throws Exception {
        setArgument("http://server:2011/");
        cmd.execute(ctx);
        assertThat(Utils.getServer(ctx), is(equalTo("http://server:2011")));
    }

    @Test
    public void should_set_url_to_protocol_server_and_port() throws Exception {
        setArgument("http://server:2011/something");
        cmd.execute(ctx);
        assertThat(Utils.getServer(ctx), is(equalTo("http://server:2011")));
    }

}
