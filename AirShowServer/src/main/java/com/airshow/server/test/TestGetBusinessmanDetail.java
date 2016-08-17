package com.airshow.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.airshow.server.request.GetBusinessmanDetailRequest;
import com.google.gson.Gson;

public class TestGetBusinessmanDetail {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetBusinessmanDetail";
		PostMethod postMethod = new PostMethod(url);
		GetBusinessmanDetailRequest getBusinessmanDetailRequest = new GetBusinessmanDetailRequest();
		getBusinessmanDetailRequest.setId(1);


		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        System.out.println(new Gson().toJson(getBusinessmanDetailRequest));
		postMethod.setRequestBody(new Gson().toJson(getBusinessmanDetailRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
