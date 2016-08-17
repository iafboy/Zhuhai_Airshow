package com.airshow.server.server;

import com.airshow.server.util.ByteBufToBytes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.airshow.server.requestHandle.EmptyContentRequestHandle;
import com.airshow.server.requestHandle.NotPostRequestHandle;
import com.airshow.server.requestHandle.ReadLogRequestHandle;
import com.airshow.server.requestHandle.RequestHandle;

public class HTTPServerHandler extends ChannelInboundHandlerAdapter {
	private Logger logger = Logger.getLogger(HTTPServerHandler.class);
	private boolean ONLY_POST = false;
	
	private int flag = 1;
	private ByteBufToBytes reader;
	private HttpRequest httpRequest;

	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof HttpRequest) {
			
			
			httpRequest = (HttpRequest) msg;

			//日志查看，上线删除
			if(httpRequest.getUri().equals("/GetLog")){
				RequestHandle requestHandle = new ReadLogRequestHandle(ctx, null);
				requestHandle.handle();
				return;
			}
			/*
			String paraStr = httpRequest.getUri().split("\\?")[1];
			
			Map<String,String[]> para = new HashMap<String, String[]>(); 
			try {
				UrlParse.parseParameters(para, paraStr, "utf-8");
				if(para.get("key") != null && para.get("sign") != null){
					String key = para.get("key")[0];
					String sign = para.get("sign")[0];
					if(!MD5Util.MD5(key+"food").equals(sign)){
						flag = 0;
						return;
					}
				}
				flag = 0;
				return;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			*/
			
			//拦截一切非POST请求
			if(ONLY_POST && httpRequest.getMethod() != io.netty.handler.codec.http.HttpMethod.POST){
				RequestHandle requestHandle = new NotPostRequestHandle(ctx, null);
				requestHandle.handle();
				flag = 0;
				return;
			}

			if (HttpHeaders.isContentLengthSet(httpRequest)) {
				reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(httpRequest));
			}
		}

		if (flag==1 && msg instanceof HttpContent) {
			
			HttpContent httpContent = (HttpContent) msg;
			ByteBuf content = httpContent.content();

			if (content.isReadable()) {
				reader.reading(content);
				content.release();

				if (reader.isEnd()) {
					String uri = httpRequest.getUri().split("\\?")[0];
					logger.warn("request uri:"+uri);
					String contentStr = null;
					
					try {
						contentStr = new String(reader.readFull(),"utf-8");
						logger.warn("request content:" + contentStr);
					} catch (UnsupportedEncodingException e) {
						RequestHandle requestHandle = new EmptyContentRequestHandle(ctx, null);
						requestHandle.handle();
						e.printStackTrace();
						return;
					}
					RequestHandleManager.forwardRequest(uri, contentStr, ctx);
				}
			} else {
				RequestHandle requestHandle = new EmptyContentRequestHandle(ctx, null);
				requestHandle.handle();
			}
		}
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
	}
}