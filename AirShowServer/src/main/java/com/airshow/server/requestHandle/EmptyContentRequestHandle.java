package com.airshow.server.requestHandle;

import io.netty.channel.ChannelHandlerContext;

import com.airshow.server.response.InvalidResponse;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class EmptyContentRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(EmptyContentRequestHandle.class);
	public EmptyContentRequestHandle(ChannelHandlerContext ctx, String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		InvalidResponse invalidResponse = new InvalidResponse();
		invalidResponse.setReturnCode(-1);
		invalidResponse.setReturnMessage("Content empty.");
		Gson gson = new Gson();
		return gson.toJson(invalidResponse);
	}

}
