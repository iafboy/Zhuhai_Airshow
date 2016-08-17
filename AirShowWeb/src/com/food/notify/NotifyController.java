package com.food.notify;

import java.io.IOException;
import java.sql.Timestamp;

import com.food.web.AndroidPushUseGeTui;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.food.config.Util;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

public class NotifyController extends Controller {
	public void index() {
		render("index.jsp");
	}
	
	@SuppressWarnings("deprecation")
	public void send(){
        String type = getPara("type");
		String content = getPara("content");
		System.out.println(content);
		if(content != null && !"".equals(content) ){
            AndroidPushUseGeTui.sendMessageWithTag("all",type+"#"+content);

			new Notify().set("type", type)
                        .set("content", content)
						.set("time", new Timestamp(System.currentTimeMillis()))
						.save();
		}
		
		
		redirect(Util.getUrl("notify", "history"));
	}
	

	public void history() {
		String pageStr = getPara("page_id");
		int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
		Page<Notify> pageUser= Notify.dao.paginate(page, Util.pageLines, "select * ","from notify where id > ?",0);
		setAttr("allPages", pageUser.getTotalPage());
		setAttr("curPage", page);
		setAttr("prePage", page == 1 ? 1 : page -1);
		setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
		setAttr("messageList", pageUser.getList());
		render("history.jsp");
	}
}
