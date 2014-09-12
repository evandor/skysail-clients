package de.twenty11.skysail.client.cli.commands.utils;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import de.twenty11.skysail.client.cli.commands.Context;

public class HttpUtils {


	public static HttpResponse get(Context context) {
		
		try {
			Request get = Request.Get(context.getCurrentUrl());
			addRequestHeaders(context, get);
			return get.execute().returnResponse();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

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
	
	public static HttpResponse post(Context context, String data) {
		
		try {
			Request post = Request.Post(context.getCurrentUrl());
			addRequestHeaders(context, post);
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

    public static HttpResponse head(Context context) {
        try {
            Request head = Request.Head(context.getCurrentUrl());
            addRequestHeaders(context, head);
            return head.execute().returnResponse();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    private static void addRequestHeaders(Context context, Request request) {
        for (Header header : context.getRequestHeaders()) {
            request.addHeader(header.getName(), header.getValue());    
        }
    }

}
