package com.airshow.server.test;

import java.io.IOException;

import com.airshow.server.request.GetAllNewsRequest;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class TestGetAllNews {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetAllNews";
		PostMethod postMethod = new PostMethod(url);

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        GetAllNewsRequest getAllNewsRequest = new GetAllNewsRequest();
        getAllNewsRequest.setPage(1);
        System.out.println(new Gson().toJson(getAllNewsRequest));
		postMethod.setRequestBody(new Gson().toJson(getAllNewsRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
