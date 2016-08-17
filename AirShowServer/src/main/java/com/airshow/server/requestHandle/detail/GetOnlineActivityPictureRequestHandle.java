package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.airshow.server.request.GetOnlineActivityPictureRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetPictureResponse;
import com.airshow.server.response.InvalidResponse;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class GetOnlineActivityPictureRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetOnlineActivityPictureRequestHandle.class);
	public GetOnlineActivityPictureRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		GetOnlineActivityPictureRequest getOnlineActivityPicture;
		try {
			getOnlineActivityPicture = gson.fromJson(contentStr,GetOnlineActivityPictureRequest.class);
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
			String sql = "select path from activity,image where activity.image_id = image.id and activity.type = ? order by activity.id desc  limit ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, getOnlineActivityPicture.getType());
			ps.setInt(2, getOnlineActivityPicture.getNumber());
			rs = ps.executeQuery();
			
			List<String> result = new ArrayList<String>();
			while(rs.next()){
				result.add(rs.getString("path"));
			}

			GetPictureResponse getPictureResponse = new GetPictureResponse();
			getPictureResponse.setReturnCode(1);
			getPictureResponse.setReturnMessage("获取活动成功");
			getPictureResponse.setPicture(result);
			return gson.toJson(getPictureResponse);
		}catch (SQLException e) {
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
