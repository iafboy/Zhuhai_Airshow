package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.pojo.Activity_People_Image;
import com.airshow.server.request.GetAllActivityPeopleImageRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetActivityPeopleImageResponse;
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

public class GetAllActivityPeopleImageRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetAllActivityPeopleImageRequestHandle.class);
	public GetAllActivityPeopleImageRequestHandle(ChannelHandlerContext ctx,
                                                  String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
        Gson gson = new Gson();
        GetAllActivityPeopleImageRequest getAllActivityPeopleImageRequest;
        try {
            getAllActivityPeopleImageRequest = gson.fromJson(contentStr,GetAllActivityPeopleImageRequest.class);
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
			String sql = "select d.*,i.path as image_path from activity_people_image d left join image i on d.image_id = i.id where d.pid = ? limit " + (getAllActivityPeopleImageRequest.getPage() -1)*10 + ",10";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,getAllActivityPeopleImageRequest.getPid());
            rs = ps.executeQuery();

			List<Activity_People_Image> result = new ArrayList<Activity_People_Image>();
			while(rs.next()){
                Activity_People_Image activity_people_image = new Activity_People_Image();
				ResultUtil.getData(rs, activity_people_image);
				result.add(activity_people_image);
			}

            sql = "select count(*) from activity_people_image where pid = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,getAllActivityPeopleImageRequest.getPid());
            rs = ps.executeQuery();

            GetActivityPeopleImageResponse getActivityPeopleImageResponse = new GetActivityPeopleImageResponse();

            if(rs.next()){
                getActivityPeopleImageResponse.setNumber(rs.getInt("count(*)"));
            }
            getActivityPeopleImageResponse.setReturnCode(1);
            getActivityPeopleImageResponse.setReturnMessage("获取档案成功");
            getActivityPeopleImageResponse.setActivity_people_images(result);
			return gson.toJson(getActivityPeopleImageResponse);
		} catch (SQLException e) {
            logger.error(e.getMessage(), e);;
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
