package de.twenty11.skysail.client.cli.commands.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import de.twenty11.skysail.client.cli.commands.Response;

public class HttpUtils {

	private static CloseableHttpClient httpclient = HttpClients.createDefault();

	public static Response get(String url) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httpGet);
			System.out.println(httpResponse.getStatusLine());
			HttpEntity entity = httpResponse.getEntity();
			EntityUtils.consume(entity);
			//Response response = new Response(httpResponse.get
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
