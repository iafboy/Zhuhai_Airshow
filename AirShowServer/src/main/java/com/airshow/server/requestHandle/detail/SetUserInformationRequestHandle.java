package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.airshow.server.request.SetUserInformationRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.CommonResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.CheckUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class SetUserInformationRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(SetUserInformationRequestHandle.class);
	public SetUserInformationRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		SetUserInformationRequest setUserInformationRequest;
		try {
			setUserInformationRequest = gson.fromJson(contentStr,SetUserInformationRequest.class);
		} catch (Exception e) {
			InvalidResponse invalidResponse = new InvalidResponse();
			invalidResponse.setReturnCode(-2);
			invalidResponse.setReturnMessage("参数错误！");
			return gson.toJson(invalidResponse);
		}

		if (!(CheckUtil.checkUnEmpty(setUserInformationRequest.getPassword())&&CheckUtil.checkUnEmpty(setUserInformationRequest.getEmail()))&&CheckUtil.checkUnEmpty(setUserInformationRequest.getIntroduction())) {
			InvalidResponse invalidResponse = new InvalidResponse();
			invalidResponse.setReturnCode(-2);
			invalidResponse.setReturnMessage("参数错误！");
			return gson.toJson(invalidResponse);
		}

        DruidPooledConnection connection = DruidDBPool.getInstance().getConnection();
		try {
			String sql = "update user set password = ?,email = ?,introduction=? where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, setUserInformationRequest.getPassword());
			ps.setString(2, setUserInformationRequest.getEmail());
			ps.setString(3, setUserInformationRequest.getIntroduction());
			ps.setString(4, setUserInformationRequest.getId());
			ps.execute();

			if (connection != null) {
				connection.close();
			}

			CommonResponse registerResponse = new CommonResponse();
			registerResponse.setReturnCode(1);
			registerResponse.setReturnMessage("信息修改成功");
			return gson.toJson(registerResponse);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		InvalidResponse invalidResponse = new InvalidResponse();
		invalidResponse.setReturnCode(-3);
		invalidResponse.setReturnMessage("出现未知错误！");
		return gson.toJson(invalidResponse);
	}

}
