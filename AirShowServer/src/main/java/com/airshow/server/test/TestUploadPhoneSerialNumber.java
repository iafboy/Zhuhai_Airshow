package com.airshow.server.test;

import java.io.IOException;

import com.airshow.server.request.UploadPhoneSerialNumberRequest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.google.gson.Gson;

public class TestUploadPhoneSerialNumber {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/UploadPhoneSerialNumber";
		PostMethod postMethod = new PostMethod(url);
		UploadPhoneSerialNumberRequest uploadPhoneSerialNumberRequest = new UploadPhoneSerialNumberRequest();
		uploadPhoneSerialNumberRequest.setId("abcdef123g");

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//        postMethod.addRequestHeader("Content-Type","text/html;charset=UTF-8");
//        postMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
        
		postMethod.setRequestBody(new Gson().toJson(uploadPhoneSerialNumberRequest));
		System.out.println(new Gson().toJson(uploadPhoneSerialNumberRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
