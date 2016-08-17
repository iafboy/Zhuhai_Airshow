package com.airshow.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.airshow.server.request.GetUserCollectRequest;
import com.google.gson.Gson;

public class TestGetUserCollection {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetUserCollection";
		PostMethod postMethod = new PostMethod(url);
		GetUserCollectRequest getUserCollectRequest = new GetUserCollectRequest();
		getUserCollectRequest.setUid("1");
		getUserCollectRequest.setType(1);

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(getUserCollectRequest));
		System.out.println(new Gson().toJson(getUserCollectRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
