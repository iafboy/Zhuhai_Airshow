package com.airshow.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.airshow.server.request.GetAdByTypeRequest;
import com.google.gson.Gson;

public class TestGetAdByType {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetAdByType";
		PostMethod postMethod = new PostMethod(url);
		GetAdByTypeRequest getAdByTypeRequest = new GetAdByTypeRequest();
		getAdByTypeRequest.setType(1);


		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(getAdByTypeRequest));
		System.out.println(new Gson().toJson(getAdByTypeRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
