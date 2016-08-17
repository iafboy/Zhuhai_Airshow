package com.airshow.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.airshow.server.request.CollectRequest;
import com.google.gson.Gson;

public class TestCollect {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://202.10.79.164:38080/API/Collect";
		PostMethod postMethod = new PostMethod(url);
		CollectRequest collectRequest = new CollectRequest();
		collectRequest.setUid("1");
		collectRequest.setType(1);
		collectRequest.setTtype(1);
		collectRequest.setId(1);
		collectRequest.setResult(1);

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(collectRequest));
		System.out.println(new Gson().toJson(collectRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
