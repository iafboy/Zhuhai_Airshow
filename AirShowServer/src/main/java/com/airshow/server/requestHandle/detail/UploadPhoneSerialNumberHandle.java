package com.airshow.server.requestHandle.detail;

import com.airshow.server.request.UploadPhoneSerialNumberRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.CommonResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.CheckUtil;
import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class UploadPhoneSerialNumberHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(UploadPhoneSerialNumberHandle.class);
	public UploadPhoneSerialNumberHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		UploadPhoneSerialNumberRequest uploadPhoneSerialNumberRequest;
		try {
			uploadPhoneSerialNumberRequest = gson.fromJson(contentStr,UploadPhoneSerialNumberRequest.class);
		} catch (Exception e) {
			InvalidResponse invalidResponse = new InvalidResponse();
			invalidResponse.setReturnCode(-2);
			invalidResponse.setReturnMessage("参数错误！");
            logger.error(e);
			return gson.toJson(invalidResponse);
		}

		if (!(CheckUtil.checkUnEmpty(uploadPhoneSerialNumberRequest.getId()))) {
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
			ps.setString(1, uploadPhoneSerialNumberRequest.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				InvalidResponse invalidResponse = new InvalidResponse();
				invalidResponse.setReturnCode(0);
				invalidResponse.setReturnMessage("手机信息已注册！");
				return gson.toJson(invalidResponse);
			}

			sql = "insert into user(id, time) values (?, ?);";
			ps = connection.prepareStatement(sql);
            ps.setString(1, uploadPhoneSerialNumberRequest.getId());

            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.execute();

            connection.commit();

			CommonResponse registerResponse = new CommonResponse();
			registerResponse.setReturnCode(1);
			registerResponse.setReturnMessage("上传手机串口id信息成功");
			return gson.toJson(registerResponse);

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
