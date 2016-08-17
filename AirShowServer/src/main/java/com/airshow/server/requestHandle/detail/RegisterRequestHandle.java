package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.airshow.server.request.RegisterRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.CommonResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.CheckUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class RegisterRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(RegisterRequestHandle.class);
	public RegisterRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		RegisterRequest registerEntry;
		try {
			registerEntry = gson.fromJson(contentStr,RegisterRequest.class);
		} catch (Exception e) {
			InvalidResponse invalidResponse = new InvalidResponse();
			invalidResponse.setReturnCode(-2);
			invalidResponse.setReturnMessage("参数错误！");
			return gson.toJson(invalidResponse);
		}

		if (!(CheckUtil.checkUnEmpty(registerEntry.getPhone())&&CheckUtil.checkUnEmpty(registerEntry.getPassword()))) {
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
            ps.setString(1, registerEntry.getId());
            rs = ps.executeQuery();
            if (!rs.next()) {
                InvalidResponse invalidResponse = new InvalidResponse();
                invalidResponse.setReturnCode(0);
                invalidResponse.setReturnMessage("手机无注册信息！");
                if (connection != null) {
                    connection.close();
                }
                return gson.toJson(invalidResponse);
            }

            sql = "update user set phone = ?,password = ? where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, registerEntry.getPhone());
            ps.setString(2, registerEntry.getPassword());
            ps.setString(3, registerEntry.getId());
            ps.execute();

            connection.commit();

			CommonResponse registerResponse = new CommonResponse();
			registerResponse.setReturnCode(1);
			registerResponse.setReturnMessage("注册成功");
			return gson.toJson(registerResponse);

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
