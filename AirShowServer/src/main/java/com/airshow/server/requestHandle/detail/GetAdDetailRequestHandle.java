package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.airshow.server.db.pojo.Ad;
import com.airshow.server.request.GetAdDetailRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetAdResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.ResultUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class GetAdDetailRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetAdDetailRequestHandle.class);
	public GetAdDetailRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		GetAdDetailRequest adDetailRequest;
		try {
			adDetailRequest = gson.fromJson(contentStr,GetAdDetailRequest.class);
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
			String sql = "select * from ad,image where ad.image_id = image.id and ad.id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, adDetailRequest.getId());
			rs = ps.executeQuery();
			
			List<Ad> result = new ArrayList<Ad>();
			while(rs.next()){
				Ad ad = new Ad();
				ResultUtil.getData(rs, ad);
				result.add(ad);
			}
			GetAdResponse getAdResponse = new GetAdResponse();
			getAdResponse.setReturnCode(1);
			getAdResponse.setReturnMessage("获取广告成功");
			getAdResponse.setAd(result);
			return gson.toJson(getAdResponse);
		} catch (SQLException e) {
            logger.error(e.getMessage(), e);
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
