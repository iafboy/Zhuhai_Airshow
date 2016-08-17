package com.food.web;

import com.food.activity.Activity;
import com.food.config.FileUtil;
import com.food.config.Util;
import com.food.image.Image;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import java.sql.Timestamp;

public class WebController extends Controller {
	public void upload() {
		UploadFile image = getFile("image");

        Activity product = new Activity();
		if(image != null){
			String imageFileName = FileUtil.createNewFile(image.getFile());
			new Image().set("name", imageFileName)
						.set("introduction", imageFileName)
						.set("path", "/upload/"+imageFileName)
						.set("time", new Timestamp(System.currentTimeMillis()))
						.save();
			Image ima = Image.dao.findFirst("select id from image where name = '"+imageFileName+"'");
			int image_id = ima.get("id");
			product.set("image_id", image_id);
		}

		String title = getPara("id");

		product.set("type", "-1")
				.set("title", title)
				.set("introduction", "")
                .set("publish_time", new Timestamp(System.currentTimeMillis()))
                .set("time", new Timestamp(System.currentTimeMillis()))
				.save();
		renderText("OK");
	}

    public void getImage() {
        Setting logo = Setting.dao.findFirst("select introduction from setting where name = 'image_logo'");
        String logoPath = logo.get("introduction");
        renderText("/upload/"+logoPath);
    }
}
