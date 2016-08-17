package com.airshow.server.server;

import com.airshow.server.db.DruidDBPool;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.airshow.server.common.Constants;

public class HTTPServer {
    private static Logger logger = Logger.getLogger(HTTPServer.class);
    private final int port;
    static{
		try{
                File file = new File(Constants.log4jCfg);
                if(file.exists())
				PropertyConfigurator.configure(Constants.log4jCfg);
                else
                    PropertyConfigurator.configure("log4j.properties");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
            System.exit(1);
		}
	}

	public HTTPServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new HTTPServerInitializer());
            logger.info("binding to port "+port);
			ChannelFuture f = b.bind(port).sync();
			logger.info("Server start OK!");
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
            logger.info("Server shutdown!");
		}
	}

    private static void initParam(String input){
        Properties prop =  new  Properties();
        try  {
            InputStream ins = new BufferedInputStream(new FileInputStream(input));
            prop.load(ins);
            Constants.log4jCfg = prop.getProperty( "airshowcfg.log4j" ).trim();
            Constants.servicePort = Integer.parseInt(prop.getProperty( "arishowcfg.serviceport" ).trim());
            Constants.readlog= prop.getProperty( "airshowcfg.log.rqhandeler" ).trim();
            Constants.dbpoolCfg= prop.getProperty( "arishowcfg.dbpoolcfg" ).trim();
        }  catch  (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
	public static void main(String[] args) {
		int port;
		if (args.length > 0) {
            System.out.println(args[0]);
            initParam(args[0]);
		}
        port = Constants.servicePort;
        DruidDBPool.getInstance().init();
        logger.info("data pool init. finished");
        try {
            new HTTPServer(port).run();
        }catch(Exception ex){
            logger.error(ex.getMessage(),ex);
        }finally {
            DruidDBPool.getInstance().close();
        }
    }
}