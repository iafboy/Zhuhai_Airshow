package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.airshow.server.db.pojo.User;
import com.airshow.server.request.GetUserInformationRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetUserInformationResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.ResultUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class GetUserInformationRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetUserInformationRequestHandle.class);
	public GetUserInformationRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		GetUserInformationRequest retUserInformationRequest;
		try {
			retUserInformationRequest = gson.fromJson(contentStr,GetUserInformationRequest.class);
		} catch (Exception e) {
			InvalidResponse invalidResponse = new InvalidResponse();
			invalidResponse.setReturnCode(-2);
			invalidResponse.setReturnMessage("参数错误！");
			return gson.toJson(invalidResponse);
		}

        DruidPooledConnection connection = DruidDBPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps=null;
		try {
			String sql = "select * from user where id = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, retUserInformationRequest.getId());
			rs = ps.executeQuery();
			User user = null;
			if(rs.next()){
				user = new User();
				ResultUtil.getData(rs, user);
			}
			
			if(user != null){
				GetUserInformationResponse getUserInformationResponse = new GetUserInformationResponse();
				getUserInformationResponse.setReturnCode(1);
				getUserInformationResponse.setReturnMessage("获取用户信息成功");
				getUserInformationResponse.setUser(user);
				return gson.toJson(getUserInformationResponse);
			}
		}catch (SQLException e) {
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
