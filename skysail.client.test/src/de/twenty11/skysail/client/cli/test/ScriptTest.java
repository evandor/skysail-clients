package de.twenty11.skysail.client.cli.test;

import org.junit.Before;
import org.junit.Test;

import de.twenty11.skysail.client.cli.testsupport.LargeTestsBase;

public class ScriptTest extends LargeTestsBase {

	@Before
	public void setUp() {
		set("showHeader", "false");
		set("showBody", "false");
		set();
		setServer("http://localhost:2016");
		cd(null);
		logout();
	}

	@Test
	public void one_line_script_is_loaded_and_excuted()
			throws Exception {
		script("test.run");
	}

}
