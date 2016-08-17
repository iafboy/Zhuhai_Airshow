package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.pojo.Businessman;
import com.airshow.server.request.GetBusinessmanByPavilionRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetBusinessmanResponse;
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

public class GetBusinessmanByPavilionRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetBusinessmanByPavilionRequestHandle.class);
	public GetBusinessmanByPavilionRequestHandle(ChannelHandlerContext ctx,
                                                 String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
        Gson gson = new Gson();
        GetBusinessmanByPavilionRequest getBusinessmanByPavilionRequest;
        try {
            getBusinessmanByPavilionRequest = gson.fromJson(contentStr,GetBusinessmanByPavilionRequest.class);
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
			String sql = "select b.*,i.path,ii.path as logo from businessman b left join image i on b.image_id=i.id left join image ii on b.logo_id = ii.id where b.pavilion_id = ? limit " + (getBusinessmanByPavilionRequest.getPage() -1)*10 + ",10";
			ps = connection.prepareStatement(sql);
            ps.setInt(1,getBusinessmanByPavilionRequest.getPavilion_id());
			rs = ps.executeQuery();
			
			List<Businessman> result = new ArrayList<Businessman>();
			while(rs.next()){
				Businessman businessman = new Businessman();
				ResultUtil.getData(rs, businessman);
				result.add(businessman);
			}

            GetBusinessmanResponse getBusinessmanResponse = new GetBusinessmanResponse();
            sql = "select count(*) from businessman where pavilion_id = ? ";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, getBusinessmanByPavilionRequest.getPavilion_id());
            rs = ps.executeQuery();

            if(rs.next()){
                getBusinessmanResponse.setNumber(rs.getInt("count(*)"));
            }

			getBusinessmanResponse.setReturnCode(1);
			getBusinessmanResponse.setReturnMessage("获取展商成功");
			getBusinessmanResponse.setBusinessman(result);
			return gson.toJson(getBusinessmanResponse);
		}  catch (SQLException e) {
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
