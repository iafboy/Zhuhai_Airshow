package com.airshow.server.test;

import com.airshow.server.request.GetAllActivityPeopleImageRequest;
import com.airshow.server.request.GetAllActivityPeopleRequest;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class TestGetAllActivityPeopleImage {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://localhost:38080/API/GetAllActivityPeopleImage2";
		PostMethod postMethod = new PostMethod(url);

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        GetAllActivityPeopleImageRequest getAllActivityPeopleImageRequest = new GetAllActivityPeopleImageRequest();
        getAllActivityPeopleImageRequest.setPage(1);
        getAllActivityPeopleImageRequest.setPid(1);

        System.out.println(new Gson().toJson(getAllActivityPeopleImageRequest));
        postMethod.setRequestBody(new Gson().toJson(getAllActivityPeopleImageRequest));
		client.executeMethod(postMethod);
		String str = new String(postMethod.getResponseBody(),"utf-8");
		System.out.println(str);
		postMethod.releaseConnection();
	}
}
