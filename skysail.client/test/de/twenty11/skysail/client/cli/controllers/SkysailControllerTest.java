package de.twenty11.skysail.client.cli.controllers;

import org.clamshellcli.api.Context;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.twenty11.skysail.client.cli.controllers.NamedParamsController;


public class SkysailControllerTest {

	private NamedParamsController skysailController;
	private Context ctx = Mockito.mock(Context.class);

	
	@Before
	public void setUp() throws Exception {
		skysailController = new NamedParamsController();
	}
	
	@Test
	public void testName() throws Exception {
		Mockito.when(ctx.getValue(Context.KEY_COMMAND_LINE_INPUT)).thenReturn("setServer http://localhost:2016");
		skysailController.handle(ctx);
	}
}
