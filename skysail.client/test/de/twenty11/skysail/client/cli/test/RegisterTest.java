package de.twenty11.skysail.client.cli.test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.junit.Before;
import org.junit.Test;

import de.twenty11.skysail.client.cli.testsupport.LargeTestsBase;

public class RegisterTest extends LargeTestsBase {

    @Before
    public void setUp() {
        set("showHeader", "false");
        set("showBody", "true");
        setServer("http://localhost:2016");
        cd(null);
        logout();
        cd("/usermanagement/registrations/");
        //get().andExpectStatusCode(200);
    }

    @Test
    public void unauthorized_user_can_post_to_registrationPage() {
        Form form = createForm("testA@test.de", "pass123", "pass123");
        HttpResponse response = post(form);
        assertThat(getStatusCode(response), is(equalTo(201)));
        assertThat(extractFromBody(response, "$.data.confirmationUrl"), containsString("registration/{id}/confirmation"));
        assertThat(extractFromBody(response, "$.data.confirmationDate"), is(nullValue()));
        assertThat(extractFromBody(response, "$.data.password"), is(equalTo("******")));
        assertThat(extractFromBody(response, "$.data.pwdRepeated"), is(equalTo("******")));
        assertThat(extractFromBody(response, "$.data.status"), is(equalTo("NEW")));
    }

    @Test
    public void password_with_five_chars_is_not_accepted() {
        Form form = createForm("test@test.de", "pass1", "pass1");
        HttpResponse response = post(form);
        assertThat(getStatusCode(response), is(equalTo(400)));
        assertThat(extractFromBody(response, "$.data.confirmationUrl"), is(equalTo("")));
        assertThat(extractFromBody(response, "$.data.status"), is(equalTo("NEW")));
        assertThat(extractFromBody(response, "$.data.id"), is(nullValue()));
    }

    @Test
    public void username_which_is_not_an_email_address_is_not_accepted() {
        Form form = createForm("test@de", "pass1", "pass1");
        HttpResponse response = post(form);
        assertThat(getStatusCode(response), is(equalTo(400)));
        assertThat(extractFromBody(response, "$.data.confirmationUrl"), containsString(""));
        assertThat(extractFromBody(response, "$.data.status"), is(equalTo("NEW")));
        assertThat(extractFromBody(response, "$.data.id"), is(nullValue()));
    }

    @Test
    public void passwords_need_to_be_identical_for_registration() {
        Form form = createForm("test@test.de", "xxxxxx", "yyyyyy");
        HttpResponse response = post(form);
        assertThat(getStatusCode(response), is(equalTo(400)));
        assertThat(extractFromBody(response, "$.data.confirmationUrl"), containsString(""));
    }

    private Form createForm(String username, String pwd, String pwdRepeated) {
        Form form = Form.form();
        form.add("username", username);
        form.add("password", pwd);
        form.add("pwdRepeated", pwdRepeated);
        return form;
    }

}
