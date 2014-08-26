package de.twenty11.skysail.client.cli.test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.junit.Before;
import org.junit.Test;

import de.twenty11.skysail.client.cli.testsupport.LargeTestsBase;

public class UsermanagementTest extends LargeTestsBase {

    @Before
    public void setUp() {
        set("showHeader", "true");
        set("showBody", "true");
        setServer("http://localhost:2016");
        cd(null);
        logout();
        login("admin", "skysail");
        cd("/usermanagement/users/");
    }

    @Test
    public void admin_cannot_create_new_user_without_username() {
        Form form = createForm("", "pass123");
        HttpResponse response = post(form);
        assertThat(getStatusCode(response), is(equalTo(400)));
        assertThat(extractFromBody(response, "$.violations[0].propertyPath"), is(equalTo("username")));
        assertThat(extractFromBody(response, "$.violations[0].message"), is(equalTo("username must have at least three characters")));
    }


    @Test
    public void admin_cannot_create_new_user_with_too_short_password() {
        
        pwd();
        
        Form form = createForm("username", "short");
        HttpResponse response = post(form);
        assertThat(getStatusCode(response), is(equalTo(400)));
        System.out.println(response);
    }

    @Test
    public void admin_can_create_new_user() {
        
        pwd();
        
        Form form = createForm("username", "pass123");
        HttpResponse response = post(form);
        assertThat(getStatusCode(response), is(equalTo(201)));
        System.out.println(response);
        assertThat(extractFromBody(response, "$.data.id"), is(notNullValue()));
        assertThat(extractFromBody(response, "$.data.username"), is(equalTo("username")));
        assertThat(extractFromBody(response, "$.data.password"), is(equalTo("******")));
    }

    private Form createForm(String username, String pwd) {
        Form form = Form.form();
        form.add("username", username);
        form.add("password", pwd);
        return form;
    }

}
