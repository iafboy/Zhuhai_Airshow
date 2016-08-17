package com.airshow.server.requestHandle;

import io.netty.channel.ChannelHandlerContext;

import com.airshow.server.response.InvalidResponse;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class NotPostRequestHandle extends RequestHandle{
    private static Logger logger = Logger.getLogger(NotPostRequestHandle.class);
	public NotPostRequestHandle(ChannelHandlerContext ctx, String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		InvalidResponse invalidResponse = new InvalidResponse();
		invalidResponse.setReturnCode(0);
		invalidResponse.setReturnMessage("Only post method is allowed.只允许Post请求！");
		Gson gson = new Gson();
		return gson.toJson(invalidResponse);
	}

}
