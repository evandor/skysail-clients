package de.twenty11.skysail.client.cli.test;

import org.junit.Before;
import org.junit.Test;

public class LoginTest extends TestBase {

	@Before
	public void setUp() {
		setServer("http://localhost:2016");
		cd(null);
		logout();
	}

	@Test
	public void unauthorized_user_cannot_access_usermanagement()
			throws Exception {
		pwd();
		// String um = get().andReturnLink("usermanagement");
		// String um =
		// get().andExpectHeader("Content-Type","application/json; charset=UTF-8").andReturnLink("usermanagement");
		get();
		cd("usermanagement");
		pwd();
		get().andExpectStatusCode(403);
	}

	@Test
	public void authenticated_user_can_access_usermanagement() throws Exception {
		login("admin", "skysail");
		cd("usermanagement");
		get().andExpectStatusCode(200);
	}

}
