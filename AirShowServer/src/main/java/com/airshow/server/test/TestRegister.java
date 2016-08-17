package com.airshow.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.airshow.server.request.RegisterRequest;
import com.google.gson.Gson;

public class TestRegister {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/Register";
		PostMethod postMethod = new PostMethod(url);
		RegisterRequest registerEntry = new RegisterRequest();
        registerEntry.setId("3");
		registerEntry.setPhone("152013463721");
		registerEntry.setPassword("1234562");

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//        postMethod.addRequestHeader("Content-Type","text/html;charset=UTF-8");
//        postMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(registerEntry));
		System.out.println(new Gson().toJson(registerEntry));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
