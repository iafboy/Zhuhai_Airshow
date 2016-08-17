package com.food.share;

import com.food.businessman.Businessman;
import com.food.news.News;
import com.food.product.Exhibit;
import com.jfinal.core.Controller;

public class ShareController extends Controller {
	public void index() {
		String typeStr = getPara("type");
		int type = (typeStr == null ? 1 : Integer.parseInt(typeStr));
		String idStr = getPara("id");
		int id = (idStr == null ? 1 : Integer.parseInt(idStr));
		switch (type) {
			case 1:
				Exhibit product = Exhibit.dao.findFirst("select b.*,i.path from exhibit b left join image i on b.image_id=i.id where b.id=?", id);
				product.set("introduction", String.valueOf(product.get("introduction")).replace("\n", "<br />"));
				setAttr("target", product);
				break;
			case 2:
				Businessman businessman = Businessman.dao.findFirst("select b.*,i.path,ii.path as logo from businessman b left join image i on b.image_id=i.id left join image ii on b.logo_id = ii.id where b.id=?", id);
				businessman.set("introduction", String.valueOf(businessman.get("introduction")).replace("\n", "<br />"));
				setAttr("target", businessman);
				break;
			case 3:
				setAttr("target", News.dao.findFirst("select b.*,i.path from news b left join image i on b.image_id=i.id where b.id=?", id));
				break;
			default:
				break;
		}
		render("index.jsp");
	}
	
}
