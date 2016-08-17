package com.food.news;

import java.sql.Timestamp;

import com.food.config.FileUtil;
import com.food.config.Util;
import com.food.image.Image;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class NewsController extends Controller {
	public void index() {
		String pageStr = getPara("page_id");
		int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
		Page<News> pageUser= News.dao.paginate(page, Util.pageLines, "select * ","from news where id > ?",0);
		setAttr("allPages", pageUser.getTotalPage());
		setAttr("curPage", page);
		setAttr("prePage", page == 1 ? 1 : page -1);
		setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
		setAttr("newsList", pageUser.getList());
		render("index.jsp");
	}
	
	public void check() {
		if (getPara("id") != null) {
			int id = getParaToInt("id");
			String sql = "select n.*, i.path as path from news n left join image i on n.image_id=i.id where n.id=?"; 
			setAttr("news", News.dao.findFirst(sql, id));
			render("detail.jsp");
		} else {
			redirect(Util.getUrl("news", "index"));
		}
	}
	
	public void add(){
		render("add.jsp");
	}
	
	public void addCheck(){
		UploadFile image = getFile("image");
		News news = new News();
		int image_id = 0;
		
		if(image != null){
			String imageFileName = FileUtil.createNewFile(image.getFile());
			new Image().set("name", imageFileName)
						.set("introduction", imageFileName)
						.set("path", "/upload/"+imageFileName)
						.set("time", new Timestamp(System.currentTimeMillis()))
						.save();
			Image ima = Image.dao.findFirst("select id from image where name = '"+imageFileName+"'");
			image_id = ima.get("id");
			news.set("image_id", image_id);
		}
				
		String title = getPara("title");
		String introduction = getPara("introduction");
		
		news.set("title", title)
			.set("introduction", introduction)
			.set("time", new Timestamp(System.currentTimeMillis()))
			.save();
		
		redirect(Util.getUrl("news", "index"));
	}
	
	public void edit(){
		if(getPara("id") != null){
			int id = getParaToInt("id");
			if(News.dao.findById(id) != null){
				setAttr("user", News.dao.findById(id));
				render("edit.jsp");
			}else{
				redirect(Util.getUrl("news","index"));
			}
		}else{
			redirect(Util.getUrl("news","index"));
		}
	}

	public void editCheck(){
		if(getPara("id") != null){
			UploadFile image = getFile("image");
			News news = News.dao.findById(getPara("id"));
			int image_id = 0;
			
			if(image != null){
				String imageFileName = FileUtil.createNewFile(image.getFile());
				new Image().set("name", imageFileName)
							.set("introduction", imageFileName)
							.set("path", "/upload/"+imageFileName)
							.set("time", new Timestamp(System.currentTimeMillis()))
							.save();
				Image ima = Image.dao.findFirst("select id from image where name = '"+imageFileName+"'");
				image_id = ima.get("id");
				news.set("image_id", image_id);
			}
					
			String title = getPara("title");
			String introduction = getPara("introduction");
			
			news.set("title", title)
				.set("introduction", introduction)
				.set("time", new Timestamp(System.currentTimeMillis()))
				.update();
			
			redirect(Util.getUrl("news", "index"));
		}else{
			redirect(Util.getUrl("user","index"));
		}
	}
	
	public void delete(){
		if(getPara("id") != null){
			News.dao.deleteById(getParaToInt("id"));
		}
		redirect(Util.getUrl("news","index"));
	}
}
