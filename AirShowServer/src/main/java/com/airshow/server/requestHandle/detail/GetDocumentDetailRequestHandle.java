package com.airshow.server.requestHandle.detail;

import com.airshow.server.db.pojo.Document;
import com.airshow.server.request.GetDocumentDetailRequest;
import com.airshow.server.requestHandle.RequestHandle;
import com.airshow.server.response.GetDocumentResponse;
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

public class GetDocumentDetailRequestHandle extends RequestHandle {
    private static Logger logger = Logger.getLogger(GetDocumentDetailRequestHandle.class);
	public GetDocumentDetailRequestHandle(ChannelHandlerContext ctx,
                                          String contentStr) {
		super(ctx, contentStr);
	}

	@Override
	public String getHandleResult() {
        Gson gson = new Gson();
        GetDocumentDetailRequest getDocumentDetailRequest;
        try {
            getDocumentDetailRequest = gson.fromJson(contentStr,GetDocumentDetailRequest.class);
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
			String sql = "select d.*,i.path as image_path from document d left join image i on d.image_id = i.id where d.id = " + getDocumentDetailRequest.getId();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();

			List<Document> result = new ArrayList<Document>();
			while(rs.next()){
                Document document = new Document();
				ResultUtil.getData(rs, document);
				result.add(document);
			}

            sql = "select count(*) from document where id = " + getDocumentDetailRequest.getId();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            GetDocumentResponse getAllDocumentResponse = new GetDocumentResponse();

            if(rs.next()){
                getAllDocumentResponse.setNumber(rs.getInt("count(*)"));
            }

            getAllDocumentResponse.setReturnCode(1);
            getAllDocumentResponse.setReturnMessage("获取档案成功");
            getAllDocumentResponse.setDocuments(result);
			return gson.toJson(getAllDocumentResponse);
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
		InvalidResponse invalidResponse = new InvalidResponse();
		invalidResponse.setReturnCode(-3);
		invalidResponse.setReturnMessage("出现未知错误！");
		return gson.toJson(invalidResponse);
	}

}
