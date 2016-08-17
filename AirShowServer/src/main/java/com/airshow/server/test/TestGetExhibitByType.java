package com.airshow.server.test;

import com.airshow.server.request.GetExhibitByPavilionRequest;
import com.airshow.server.request.GetExhibitByTypeRequest;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class TestGetExhibitByType {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetExhibitByType";
		PostMethod postMethod = new PostMethod(url);
		GetExhibitByTypeRequest getExhibitByTypeRequest = new GetExhibitByTypeRequest();
        getExhibitByTypeRequest.setType(1);
        getExhibitByTypeRequest.setPavilion_id(3);
        getExhibitByTypeRequest.setPage(1);


		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(getExhibitByTypeRequest));
		System.out.println(new Gson().toJson(getExhibitByTypeRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
