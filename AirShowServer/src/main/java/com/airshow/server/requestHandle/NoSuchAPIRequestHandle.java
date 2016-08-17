package com.airshow.server.requestHandle;

import io.netty.channel.ChannelHandlerContext;

import com.airshow.server.response.InvalidResponse;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class NoSuchAPIRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(NoSuchAPIRequestHandle.class);
	public NoSuchAPIRequestHandle(ChannelHandlerContext ctx, String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		InvalidResponse invalidResponse = new InvalidResponse();
		invalidResponse.setReturnCode(-1);
		invalidResponse.setReturnMessage("No such API");
		Gson gson = new Gson();
		return gson.toJson(invalidResponse);
	}

}
