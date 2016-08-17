package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.pojo.Exhibit;
import com.airshow.server.request.GetExhibitByTypeRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetExhibitResponse;
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

public class GetExhibitByTypeRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetExhibitByTypeRequestHandle.class);
	public GetExhibitByTypeRequestHandle(ChannelHandlerContext ctx,
                                         String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		GetExhibitByTypeRequest getExhibitByTypeRequest;
		try {
            getExhibitByTypeRequest = gson.fromJson(contentStr,GetExhibitByTypeRequest.class);
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
			String sql = "select e.*, i.path from exhibit e left join image i on e.image_id=i.id where e.type = ? and e.pavilion_id = ? order by priority desc";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, getExhibitByTypeRequest.getType());
            ps.setInt(2, getExhibitByTypeRequest.getPavilion_id());

            rs = ps.executeQuery();
			
			List<Exhibit> result = new ArrayList<Exhibit>();
			while(rs.next()){
				Exhibit exhibit = new Exhibit();
				ResultUtil.getData(rs, exhibit);
                //exhibit.setIntroduction(null);
				result.add(exhibit);
			}

            GetExhibitResponse getExhibitResponse = new GetExhibitResponse();

            sql = "select count(*) from exhibit where type = ? and pavilion_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, getExhibitByTypeRequest.getType());
            ps.setInt(2, getExhibitByTypeRequest.getPavilion_id());
            rs = ps.executeQuery();
            if(rs.next()){
                getExhibitResponse.setNumber(rs.getInt("count(*)"));
            }

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
