package com.airshow.server.requestHandle.detail;

import com.airshow.server.request.GetDocumentIntroductionRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.CommonResponse;
import com.airshow.server.response.InvalidResponse;
import com.airshow.server.db.DruidDBPool;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetDocumentIntroductionRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetDocumentIntroductionRequestHandle.class);
	public GetDocumentIntroductionRequestHandle(ChannelHandlerContext ctx,
                                                String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
        Gson gson = new Gson();
        GetDocumentIntroductionRequest getDocumentIntroductionRequest;
        try {
            getDocumentIntroductionRequest = gson.fromJson(contentStr,GetDocumentIntroductionRequest.class);
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
			String sql = "select introduction from setting where name='document_introduction'";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();

            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setReturnCode(0);

            if(rs.next()){
                commonResponse.setReturnMessage(rs.getString("introduction"));
                commonResponse.setReturnCode(1);
            }

            return gson.toJson(commonResponse);
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
