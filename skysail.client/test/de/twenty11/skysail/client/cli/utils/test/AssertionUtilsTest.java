package de.twenty11.skysail.client.cli.utils.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.twenty11.skysail.client.cli.commands.Context;
import de.twenty11.skysail.client.cli.domain.KeyValueAssertion;
import de.twenty11.skysail.client.cli.utils.AssertionUtils;

public class AssertionUtilsTest {

    private Context context;
    private StringBuilder sb;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        context = new Context();
        sb = new StringBuilder();
        Header[] requestHeaders = new Header[2];
        requestHeaders[0] = new BasicHeader("a", "b");
        requestHeaders[1] = new BasicHeader("Set-Cookie", "Credentials=JA181qYp7nSX1H15klaqiYgYUkXdY+cZ3Grd5q1E7bCOtW6fnmWqIbaChPIEV7eX; Path=/; HttpOnly");
        context.setResponseHeaders(requestHeaders);

    }

    @Test
    public void matches_existing_keyValueAssertion_in_header() {
        KeyValueAssertion headerAssertion = new KeyValueAssertion("a", "b");
        AssertionUtils.handleHeader(context, headerAssertion, sb);
        assertThat(sb.toString(), is(equalTo("matched a=b")));
    }
    
    @Test(expected = AssertionError.class)
    public void throwsAssertionError_for_nonexisting_keyValueAssertion_in_header() {
        KeyValueAssertion headerAssertion = new KeyValueAssertion("notThere", "b");
        //thrown.expect(AssertionError.class);
        AssertionUtils.handleHeader(context, headerAssertion, sb);
    }
    
    @Test
    public void matches_existing_key_in_header() {
        AssertionUtils.handleHeader(context, "a", sb);
        assertThat(sb.toString(), is(equalTo("matched key 'a'")));
    }
    
    @Test(expected = AssertionError.class)
    public void throwsAssertionError_for_nonexisting_key_in_header() {
        KeyValueAssertion headerAssertion = new KeyValueAssertion("notThere", "b");
        //thrown.expect(AssertionError.class);
        AssertionUtils.handleHeader(context, headerAssertion, sb);
    }
}
