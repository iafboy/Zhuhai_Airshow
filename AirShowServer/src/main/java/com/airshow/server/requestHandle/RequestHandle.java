package com.airshow.server.requestHandle;

import io.netty.channel.ChannelHandlerContext;

import com.airshow.server.server.HTTPResponseBuilder;

public abstract class RequestHandle {
	protected ChannelHandlerContext ctx;
	protected String contentStr;
	
	public RequestHandle(ChannelHandlerContext ctx,String contentStr) {
		this.ctx = ctx;
		this.contentStr = contentStr;
	}
	
	public abstract String getHandleResult();
	
	public void handle(){
		ctx.writeAndFlush(HTTPResponseBuilder.getResponse(getHandleResult()));
		ctx.close();
	}
}
