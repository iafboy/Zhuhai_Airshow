package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.netty.channel.ChannelHandlerContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.airshow.server.db.pojo.UserCollection;
import com.airshow.server.request.GetUserCollectRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetUserCollectionResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.util.ResultUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

public class GetUserCollectRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetUserCollectRequestHandle.class);
	public GetUserCollectRequestHandle(ChannelHandlerContext ctx, String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {

		Gson gson = new Gson();
		GetUserCollectRequest getUserCollectRequest;
		try {
			getUserCollectRequest = gson.fromJson(contentStr,GetUserCollectRequest.class);
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
            String sql = "select * from user_action where uid = ? and atype = ? and result = 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, getUserCollectRequest.getUid());
            ps.setInt(2, getUserCollectRequest.getType());
            rs = ps.executeQuery();
			
			List<UserCollection> result = new ArrayList<UserCollection>();
			while(rs != null && rs.next()){
				UserCollection userCollection = new UserCollection();
				ResultUtil.getData(rs, userCollection);
				result.add(userCollection);
			}

			GetUserCollectionResponse getUserCollectionResponse = new GetUserCollectionResponse();
			getUserCollectionResponse.setReturnCode(1);
			getUserCollectionResponse.setReturnMessage("获取用户收藏成功");
			getUserCollectionResponse.setUserCollections(result);
			return gson.toJson(getUserCollectionResponse);
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
