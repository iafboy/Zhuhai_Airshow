package com.airshow.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.airshow.server.request.LoginRequest;
import com.google.gson.Gson;

public class TestLogin {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/Login";
		PostMethod postMethod = new PostMethod(url);
		LoginRequest loginEntry = new LoginRequest();
		loginEntry.setPhone("152013463721");
		loginEntry.setPassword("1234562");

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//        postMethod.addRequestHeader("Content-Type","text/html;charset=UTF-8");
//        postMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(loginEntry));
		System.out.println(new Gson().toJson(loginEntry));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
