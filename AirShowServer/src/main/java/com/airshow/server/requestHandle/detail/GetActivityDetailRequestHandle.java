package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.airshow.server.db.pojo.Activity;
import com.airshow.server.request.GetActivityDetailRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetActivityResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.ResultUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class GetActivityDetailRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetActivityDetailRequestHandle.class);
	public GetActivityDetailRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		GetActivityDetailRequest getActivityDetailRequest;
		try {
			getActivityDetailRequest = gson.fromJson(contentStr,GetActivityDetailRequest.class);
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
			String sql = "select * from activity,image where activity.image_id = image.id and activity.id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, getActivityDetailRequest.getId());
			rs = ps.executeQuery();
			
			List<Activity> result = new ArrayList<Activity>();
			while(rs.next()){
				Activity activity = new Activity();
				ResultUtil.getData(rs, activity);
				result.add(activity);
			}
			GetActivityResponse getActivityResponse = new GetActivityResponse();
			getActivityResponse.setReturnCode(1);
			getActivityResponse.setReturnMessage("获取活动成功");
			getActivityResponse.setActivity(result);
			return gson.toJson(getActivityResponse);
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
