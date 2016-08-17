package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.airshow.server.db.pojo.Exhibit;
import com.airshow.server.request.GetExhibitDetailRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetExhibitResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.ResultUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class GetExhibitDetailRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetExhibitDetailRequestHandle.class);
	public GetExhibitDetailRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		GetExhibitDetailRequest getExhibitDetailRequest;
		try {
			getExhibitDetailRequest = gson.fromJson(contentStr,GetExhibitDetailRequest.class);
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
			String sql = "select e.*, i.path from exhibit e left join image i on e.image_id=i.id where e.id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, getExhibitDetailRequest.getId());
			rs = ps.executeQuery();
			
			List<Exhibit> result = new ArrayList<Exhibit>();
			while(rs.next()){
				Exhibit exhibit = new Exhibit();
				ResultUtil.getData(rs, exhibit);
				result.add(exhibit);
			}
			
			GetExhibitResponse getExhibitResponse = new GetExhibitResponse();
			getExhibitResponse.setReturnCode(1);
			getExhibitResponse.setReturnMessage("获取展品成功");
			getExhibitResponse.setExhibit(result);
			return gson.toJson(getExhibitResponse);
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
