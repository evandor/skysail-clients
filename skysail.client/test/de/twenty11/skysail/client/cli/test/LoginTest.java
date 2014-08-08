package de.twenty11.skysail.client.cli.test;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.twenty11.skysail.client.cli.testsupport.TestBase;

@Ignore
public class LoginTest extends TestBase {

	@Before
	public void setUp() {
		set("showHeader", "false");
		set();
		setServer("http://localhost:2016");
		cd(null);
		logout();
	}

	@Test
	public void unauthorized_user_cannot_access_usermanagement()
			throws Exception {
		get();
		cd("usermanagement");
		get().andExpectStatusCode(403);
	}

	@Test
	public void authenticated_user_can_access_usermanagement() throws Exception {
		login("admin", "skysail");
		cd("usermanagement");
		get().andExpectStatusCode(200);
	}

	@Test
	public void previously_authenticated_user_cannot_access_usermanagement_after_logout() throws Exception {
		login("admin", "skysail");
		cd("usermanagement");
		get().andExpectStatusCode(200);
		logout();
		pwd();
		get().andExpectStatusCode(403);
	}

}
