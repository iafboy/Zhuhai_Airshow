package com.airshow.server.test;

import com.airshow.server.request.GetExhibitDetailRequest;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class TestGetDocumentDetail {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetDocumentDetail";
		PostMethod postMethod = new PostMethod(url);
		GetExhibitDetailRequest getExhibitDetailRequest = new GetExhibitDetailRequest();
		getExhibitDetailRequest.setId(1);


		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(getExhibitDetailRequest));
		System.out.println(new Gson().toJson(getExhibitDetailRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
