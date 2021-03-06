package com.airshow.server.server;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.apache.log4j.Logger;

public class HTTPResponseBuilder {
    private static Logger logger = Logger.getLogger(HTTPResponseBuilder.class);
	public static HttpResponse getResponse(String responseStr) {

		FullHttpResponse response = null;
		try {
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.wrappedBuffer(responseStr.getBytes("UTF-8")));
			response.headers().set("CONTENT_TYPE", "text/plain; charset=utf-8");
			response.headers().set("CONTENT_LENGTH",response.content().readableBytes());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
		
		return response;
	}
}
