package com.airshow.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.airshow.server.request.SetUserInformationRequest;
import com.google.gson.Gson;

public class TestSetUserInformation {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/SetUserInformation";
		PostMethod postMethod = new PostMethod(url);
		SetUserInformationRequest setUserInformationRequest = new SetUserInformationRequest();
		setUserInformationRequest.setId("1");
		setUserInformationRequest.setPassword("123456");
		setUserInformationRequest.setEmail("adsfasdf@163.com");
		setUserInformationRequest.setIntroduction("aaa");

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(setUserInformationRequest));
		System.out.println(new Gson().toJson(setUserInformationRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
