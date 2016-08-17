package com.airshow.server.requestHandle;

import com.airshow.server.common.Constants;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadLogRequestHandle extends RequestHandle{
    private static Logger logger = Logger.getLogger(ReadLogRequestHandle.class);
	public ReadLogRequestHandle(ChannelHandlerContext ctx, String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		FileReader fr;
		StringBuffer buffer = new StringBuffer();
		try {
			
			fr = new FileReader(Constants.readlog);
			BufferedReader br=new BufferedReader(fr);
	        while(br.readLine()!=null){
	    		buffer.append(br.readLine()+"<br />");
	        }
	        br.close();
	        return buffer.toString();
		} catch (IOException e) {
			return "日志不存在！";
		}
	}

}
