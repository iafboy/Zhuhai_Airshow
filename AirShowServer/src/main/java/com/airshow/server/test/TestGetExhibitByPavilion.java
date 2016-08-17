package com.airshow.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.airshow.server.request.GetExhibitByPavilionRequest;
import com.google.gson.Gson;

public class TestGetExhibitByPavilion {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetExhibitByPavilion";
		PostMethod postMethod = new PostMethod(url);
		GetExhibitByPavilionRequest getExhibitByPavilionRequest = new GetExhibitByPavilionRequest();
		getExhibitByPavilionRequest.setPavilion_id(1);
        getExhibitByPavilionRequest.setPage(1);


		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(getExhibitByPavilionRequest));
		System.out.println(new Gson().toJson(getExhibitByPavilionRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
