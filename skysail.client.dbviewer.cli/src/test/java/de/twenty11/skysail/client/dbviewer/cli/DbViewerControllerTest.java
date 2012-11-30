package de.twenty11.skysail.client.dbviewer.cli;

import org.clamshellcli.test.MockContext;
import org.junit.Test;


public class DbViewerControllerTest {

	@Test
	public void test () {
		DbViewerController ctrl = new DbViewerController();
		MockContext ctx = MockContext.createInstance();
		ctrl.handle(ctx);
	}
}
