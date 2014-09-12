package de.twenty11.skysail.client.cli.commands.utils;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

public class HttpUtils {

	public static HttpResponse options(String url, List<Header> requestHeaders) {

		try {
			Request get = Request.Options(url);
			for (Header header : requestHeaders) {
				get.addHeader(header.getName(), header.getValue());
			}
			return get.execute().returnResponse();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static HttpResponse get(String url, List<Header> requestHeaders) {

		try {
			Request get = Request.Get(url);
			for (Header header : requestHeaders) {
				get.addHeader(header.getName(), header.getValue());
			}
			return get.execute().returnResponse();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static HttpResponse post(String url, String data,
			List<Header> requestHeaders) {

		try {
			Request post = Request.Post(url);
			for (Header header : requestHeaders) {
				post.addHeader(header.getName(), header.getValue());
			}
			post.bodyString(data, ContentType.APPLICATION_JSON);
			return post.execute().returnResponse();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static HttpResponse postForLogin(String url, String username,
			String password) {
		try {
			return Request
					.Post(url)
					.bodyForm(
							Form.form().add("username", username)
									.add("password", password).build())
					.execute().returnResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HttpResponse post(String url, Form form) {
		try {
			return Request.Post(url).bodyForm(form.build()).execute()
					.returnResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HttpResponse head(String url, List<Header> requestHeaders) {
		try {
			Request get = Request.Head(url);
			for (Header header : requestHeaders) {
				get.addHeader(header.getName(), header.getValue());
			}
			return get.execute().returnResponse();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
