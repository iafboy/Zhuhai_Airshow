package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.airshow.server.db.pojo.News;
import com.airshow.server.request.GetNewsDetailRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetNewsResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.ResultUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class GetNewsDetailRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetNewsDetailRequestHandle.class);
	public GetNewsDetailRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		GetNewsDetailRequest getNewsDetailRequest;
		try {
			getNewsDetailRequest = gson.fromJson(contentStr,GetNewsDetailRequest.class);
		} catch (Exception e) {
			InvalidResponse invalidResponse = new InvalidResponse();
			invalidResponse.setReturnCode(-2);
			invalidResponse.setReturnMessage("参数错误！");
			return gson.toJson(invalidResponse);
		}

        DruidPooledConnection connection = DruidDBPool.getInstance().getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
		try {
			String sql = "select * from news,image where news.image_id = image.id and news.id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, getNewsDetailRequest.getId());
			rs = ps.executeQuery();
			
			List<News> result = new ArrayList<News>();
			while(rs.next()){
				News news = new News();
				ResultUtil.getData(rs, news);
				result.add(news);
			}

			GetNewsResponse getAllNewsResponse = new GetNewsResponse();
			getAllNewsResponse.setReturnCode(1);
			getAllNewsResponse.setReturnMessage("获取新闻成功");
			getAllNewsResponse.setNews(result);
			return gson.toJson(getAllNewsResponse);
		} catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
		InvalidResponse invalidResponse = new InvalidResponse();
		invalidResponse.setReturnCode(-3);
		invalidResponse.setReturnMessage("出现未知错误！");
		return gson.toJson(invalidResponse);
	}

}
