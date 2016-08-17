package com.airshow.server.test;

import com.airshow.server.request.GetDocumentIntroductionRequest;
import com.airshow.server.request.GetExhibitDetailRequest;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class TestGetDocumentIntroduction {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetDocumentIntroduction";
		PostMethod postMethod = new PostMethod(url);
        GetDocumentIntroductionRequest getDocumentIntroductionRequest = new GetDocumentIntroductionRequest();


		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(getDocumentIntroductionRequest));
		System.out.println(new Gson().toJson(getDocumentIntroductionRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
