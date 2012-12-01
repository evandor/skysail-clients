package de.twenty11.skysail.client.dbviewer.cli;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.twenty11.skysail.client.dbviewer.cli.internal.Utils;

public class SetHostCommandTest extends CommandTest {
	
    private SetHostCommand cmd;

    public SetHostCommandTest() {
        cmd = new SetHostCommand();
    }

    @Before
    public void setUp() {
        // needs to be done as we get same ctx Object everytime
        reset(ctx);
    }

    @Test
    public void should_set_host_to_provided_value() throws Exception {
        setArgument("host");
        cmd.execute(ctx);
        assertThat(Utils.getHost(ctx), is(equalTo("host")));
    }


}
