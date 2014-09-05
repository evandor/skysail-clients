package de.twenty11.skysail.client.cli.commands.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpUtils {

	public static HttpResponse get(String url, List<Header> requestHeaders) {
		
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpGet);
			requestHeaders = Arrays.asList(httpGet.getAllHeaders());
			return response;
			//return Request.Get(url).execute().returnResponse();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static HttpResponse post(String url, String username, String password) {
		try {
			return Request.Post(url)
					.bodyForm(Form.form().add("username",  username).add("password",  password).build())
					.execute().returnResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HttpResponse post(String url, Form form) {
		try {
			return Request.Post(url)
					.bodyForm(form.build())
					.execute().returnResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
