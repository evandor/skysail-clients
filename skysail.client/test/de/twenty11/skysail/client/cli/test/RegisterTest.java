package de.twenty11.skysail.client.cli.test;

import org.apache.http.client.fluent.Form;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.twenty11.skysail.client.cli.testsupport.TestBase;

@Ignore
public class RegisterTest extends TestBase {

	private Form form;

	@Before
	public void setUp() {
		setServer("http://localhost:2016");
		cd(null);
		logout();
		form = Form.form();
	}

	@Test
	public void unauthorized_user_can_access_registrationPage() {
		cd("/usermanagement/registrations/");
		pwd();
		get().andExpectStatusCode(200);

		form.add("username", "test@test.de");
		form.add("password", "pass");
		form.add("pwdRepeated", "pass");

		String andExtractFromBody = post(form).andExtractFromBody("$.data.confirmationUrl");

	}

}
