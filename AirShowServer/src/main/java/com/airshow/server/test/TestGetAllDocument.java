package com.airshow.server.test;

import com.airshow.server.request.GetAllDocumentRequest;
import com.airshow.server.request.GetAllNewsRequest;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class TestGetAllDocument {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetAllDocument";
		PostMethod postMethod = new PostMethod(url);

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        GetAllDocumentRequest getAllDocumentRequest = new GetAllDocumentRequest();
        getAllDocumentRequest.setPage(1);
        System.out.println(new Gson().toJson(getAllDocumentRequest));
		postMethod.setRequestBody(new Gson().toJson(getAllDocumentRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
