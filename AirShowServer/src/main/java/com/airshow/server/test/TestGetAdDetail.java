package com.airshow.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.airshow.server.request.GetAdDetailRequest;
import com.google.gson.Gson;

public class TestGetAdDetail {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetAdDetail";
		PostMethod postMethod = new PostMethod(url);
		GetAdDetailRequest getAdDetailRequest = new GetAdDetailRequest();
		getAdDetailRequest.setId(3);


		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(getAdDetailRequest));
		System.out.println(new Gson().toJson(getAdDetailRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
