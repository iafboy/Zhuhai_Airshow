package com.food.ads;

import java.sql.Timestamp;

import com.food.config.FileUtil;
import com.food.config.Util;
import com.food.image.Image;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class AdController extends Controller {
	public void index() {
		String pageStr = getPara("page_id");
		int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
		Page<Ad> pageUser= Ad.dao.paginate(page, Util.pageLines, "select * ","from ad where id > ?",0);
		setAttr("allPages", pageUser.getTotalPage());
		setAttr("curPage", page);
		setAttr("prePage", page == 1 ? 1 : page -1);
		setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
		setAttr("adList", pageUser.getList());
		render("index.jsp");
	}
	
	public void check() {
		if (getPara("id") != null) {
			int id = getParaToInt("id");
			String sql = "select a.*, i.path from ad a left join image i on a.image_id=i.id where a.id=?"; 
			setAttr("ad", Ad.dao.findFirst(sql, id));
			render("detail.jsp");
		} else {
			redirect(Util.getUrl("ad", "index"));
		}
	}
	
	public void add(){
		render("add.jsp");
	}
	
	public void addCheck(){
		UploadFile image = getFile("image");
		Ad ad = new Ad();
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
			ad.set("image_id", image_id);
		}
		
		String title = getPara("title");
        String tid = getPara("tid");
        String type = getPara("type");
		if(tid == null || "".equals(tid)){
			tid = "0";
		}
		
		String introduction = getPara("introduction");
		
		ad.set("title", title)
		  .set("type", type)
		  .set("introduction", introduction)
		  .set("time", new Timestamp(System.currentTimeMillis()))
		  .save();			
		
		
		redirect(Util.getUrl("ad", "index"));
	}
	
	public void edit(){
		if(getPara("id") != null){
			int id = getParaToInt("id");
			if(Ad.dao.findById(id) != null){
				Ad ad = Ad.dao.findById(id);
				setAttr("ad", ad);
				render("edit.jsp");
			}else{
				redirect(Util.getUrl("ad","index"));
			}
		}else{
			redirect(Util.getUrl("ad","index"));
		}
	}

	public void editCheck(){
		if(getPara("id") != null){
			UploadFile image = getFile("image");
			Ad ad = Ad.dao.findById(getPara("id"));
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
				ad.set("image_id", image_id);
			}
			
			String title = getPara("title");
			String introduction = getPara("introduction");
			
			ad.set("title", title)
			  .set("introduction", introduction)
			  .set("time", new Timestamp(System.currentTimeMillis()))
			  .update();			
			
			
			redirect(Util.getUrl("ad", "index"));
		}else{
			redirect(Util.getUrl("ad","index"));
		}
	}
	
	public void delete(){
		if(getPara("id") != null){
			Ad.dao.deleteById(getParaToInt("id"));
		}
		redirect(Util.getUrl("ad","index"));
	}
}
