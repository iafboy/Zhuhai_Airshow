package com.airshow.server.test;

import com.airshow.server.request.GetBusinessmanByPavilionRequest;
import com.airshow.server.request.GetBusinessmanDetailRequest;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class TestGetBusinessmanByPavilion {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://202.10.79.164:38080/API/GetBusinessmanByPavilion";
		PostMethod postMethod = new PostMethod(url);
		GetBusinessmanByPavilionRequest getBusinessmanByPavilionRequest = new GetBusinessmanByPavilionRequest();
        getBusinessmanByPavilionRequest.setPavilion_id(1);
        getBusinessmanByPavilionRequest.setPage(1);


		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(getBusinessmanByPavilionRequest));
        System.out.println(new Gson().toJson(getBusinessmanByPavilionRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
