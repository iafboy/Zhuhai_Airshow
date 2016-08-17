package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.airshow.server.request.LoginRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.CommonResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.CheckUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class LoginRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(LoginRequestHandle.class);
	public LoginRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		LoginRequest loginEntry;
		try {
			loginEntry = gson.fromJson(contentStr,LoginRequest.class);
		} catch (Exception e) {
			InvalidResponse invalidResponse = new InvalidResponse();
			invalidResponse.setReturnCode(-2);
			invalidResponse.setReturnMessage("参数错误！");
			return gson.toJson(invalidResponse);
		}

		if (!(CheckUtil.checkUnEmpty(loginEntry.getPhone())&&CheckUtil.checkUnEmpty(loginEntry.getPassword()))) {
			InvalidResponse invalidResponse = new InvalidResponse();
			invalidResponse.setReturnCode(-2);
			invalidResponse.setReturnMessage("参数错误！");
			return gson.toJson(invalidResponse);
		}

        DruidPooledConnection connection = DruidDBPool.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement ps=null;
		try {
			String sql = "select * from user where phone = ? and password = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginEntry.getPhone());
			ps.setString(2, loginEntry.getPassword());
			rs = ps.executeQuery();

			if (rs.next()) {
				String uid = rs.getString("id");
				CommonResponse registerResponse = new CommonResponse();
				registerResponse.setReturnCode(1);
				registerResponse.setReturnMessage(String.valueOf(uid));
				return gson.toJson(registerResponse);
			}else{
				InvalidResponse invalidResponse = new InvalidResponse();
				invalidResponse.setReturnCode(0);
				invalidResponse.setReturnMessage("用户名或密码错误！");
				return gson.toJson(invalidResponse);
			}

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
