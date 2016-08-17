package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.airshow.server.db.pojo.Pavilion;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetPavilionResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.ResultUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class GetAllPavilionRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetAllPavilionRequestHandle.class);
	public GetAllPavilionRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {

        DruidPooledConnection connection = DruidDBPool.getInstance().getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
		try {
			String sql = "select p.*,i.path as path from pavilion p left join image i on p.image_id = i.id";
			 ps= connection.prepareStatement(sql);
			rs = ps.executeQuery();
			
			List<Pavilion> result = new ArrayList<Pavilion>();
			while(rs.next()){
				Pavilion pavilion = new Pavilion();
				ResultUtil.getData(rs, pavilion);
				result.add(pavilion);
			}
			
			Gson gson = new Gson();
			GetPavilionResponse getPavilionResponse = new GetPavilionResponse();
			getPavilionResponse.setReturnCode(1);
			getPavilionResponse.setReturnMessage("获取展馆成功");
			getPavilionResponse.setPavilion(result);
			return gson.toJson(getPavilionResponse);
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
		Gson gson = new Gson();
		InvalidResponse invalidResponse = new InvalidResponse();
		invalidResponse.setReturnCode(-3);
		invalidResponse.setReturnMessage("出现未知错误！");
		return gson.toJson(invalidResponse);
	}

}
