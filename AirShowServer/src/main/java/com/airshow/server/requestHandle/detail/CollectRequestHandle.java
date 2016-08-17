package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.airshow.server.request.CollectRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.CommonResponse;
import com.airshow.server.response.InvalidResponse;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class CollectRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(CollectRequestHandle.class);
	public CollectRequestHandle(ChannelHandlerContext ctx,
			String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
		Gson gson = new Gson();
		CollectRequest collectRequest;
		try {
			collectRequest = gson.fromJson(contentStr,CollectRequest.class);
		} catch (Exception e) {
			InvalidResponse invalidResponse = new InvalidResponse();
			invalidResponse.setReturnCode(-2);
			invalidResponse.setReturnMessage("参数错误！");
			return gson.toJson(invalidResponse);
		}

        DruidPooledConnection connection = DruidDBPool.getInstance().getConnection();
        PreparedStatement ps=null;
        PreparedStatement ps2=null;
        ResultSet rs=null;
		try {
			String sql = "select * from user_action where uid = ? and atype = ? and target_type = ? and target_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, collectRequest.getUid());
			ps.setInt(2, collectRequest.getType());
			ps.setInt(3, collectRequest.getTtype());
			ps.setInt(4, collectRequest.getId());
			rs = ps.executeQuery();
			if(rs.next()){
				sql = "update user_action set result = ? where uid = ? and atype = ? and target_type = ? and target_id = ?";
				ps2 = connection.prepareStatement(sql);
				ps2.setInt(1, collectRequest.getResult());
				ps2.setString(2, collectRequest.getUid());
				ps2.setInt(3, collectRequest.getType());
				ps2.setInt(4, collectRequest.getTtype());
				ps2.setInt(5, collectRequest.getId());
				ps2.execute();
			}else{
				sql = "insert user_action(result,uid,atype,target_type,target_id,time) values (?,?,?,?,?,?)";
				ps2 = connection.prepareStatement(sql);
				ps2.setInt(1, collectRequest.getResult());
				ps2.setString(2, collectRequest.getUid());
				ps2.setInt(3, collectRequest.getType());
				ps2.setInt(4, collectRequest.getTtype());
				ps2.setInt(5, collectRequest.getId());
				ps2.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				ps2.execute();

			}
            connection.commit();
			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setReturnCode(1);
			commonResponse.setReturnMessage("收藏成功");
			return gson.toJson(commonResponse);
		} catch (Exception e) {
            logger.error(e.getMessage(),e);
		}finally {
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
                } catch (SQLException e) {
                    logger.error(e.getMessage(),e);
                }
            }
            if(ps2!=null){
                try {
                    ps2.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        InvalidResponse invalidResponse = new InvalidResponse();
		invalidResponse.setReturnCode(-3);
		invalidResponse.setReturnMessage("出现未知错误！");
		return gson.toJson(invalidResponse);
	}
}
