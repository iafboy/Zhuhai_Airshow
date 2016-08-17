package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.pojo.Activity_People;
import com.airshow.server.request.GetAllActivityPeopleRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetActivityPeopleResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.db.DruidDBPool;
import com.airshow.server.util.ResultUtil;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllActivityPeopleRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetAllActivityPeopleRequestHandle.class);
	public GetAllActivityPeopleRequestHandle(ChannelHandlerContext ctx,
                                             String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
        Gson gson = new Gson();
        GetAllActivityPeopleRequest getAllActivityPeopleRequest;
        try {
            getAllActivityPeopleRequest = gson.fromJson(contentStr,GetAllActivityPeopleRequest.class);
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
			String sql = "select d.*,i.path as path from activity_people d left join image i on d.image_id = i.id ";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();

			List<Activity_People> result = new ArrayList<Activity_People>();
			while(rs.next()){
                Activity_People activity_people = new Activity_People();
				ResultUtil.getData(rs, activity_people);
				result.add(activity_people);
			}

            sql = "select count(*) from activity_people";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            GetActivityPeopleResponse getActivityPeopleResponse = new GetActivityPeopleResponse();

            if(rs.next()) {
                getActivityPeopleResponse.setNumber(rs.getInt("count(*)"));
            }
            getActivityPeopleResponse.setReturnCode(1);
            getActivityPeopleResponse.setReturnMessage("获取档案成功");
            getActivityPeopleResponse.setActivity_peoples(result);
			return gson.toJson(getActivityPeopleResponse);
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
