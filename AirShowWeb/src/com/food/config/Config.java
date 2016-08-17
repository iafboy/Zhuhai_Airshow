package com.food.config;

import com.food.activity.Activity;
import com.food.activity.ActivityController;
import com.food.activity.ActivityPeople;
import com.food.activity.ActivityPeopleImage;
import com.food.ads.Ad;
import com.food.ads.AdController;
import com.food.businessman.Businessman;
import com.food.businessman.BusinessmanController;
import com.food.document.Document;
import com.food.document.DocumentController;
import com.food.exit.ExitController;
import com.food.image.Image;
import com.food.image.ImageController;
import com.food.index.IndexController;
import com.food.news.News;
import com.food.news.NewsController;
import com.food.notify.Notify;
import com.food.notify.NotifyController;
import com.food.pavilion.Pavilion;
import com.food.pavilion.PavilionController;
import com.food.product.Exhibit;
import com.food.product.ProductController;
import com.food.share.ShareController;
import com.food.user.User;
import com.food.user.UserController;
import com.food.web.Setting;
import com.food.web.WebController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

public class Config extends JFinalConfig {
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.JSP);
		me.setBaseViewPath("/WEB-INF/pages/");
	}

	public void configRoute(Routes me) {
		me.add("/index", IndexController.class, "/index/");
		me.add("/user", UserController.class, "/user/");
		me.add("/pavilion", PavilionController.class, "/pavilion/");
		me.add("/businessman", BusinessmanController.class, "/businessman/");
		me.add("/news", NewsController.class, "/news/");
		me.add("/ad", AdController.class, "/ad/");
		me.add("/notify", NotifyController.class, "/notify/");
		me.add("/exit", ExitController.class, "/exit/");
		me.add("/product", ProductController.class, "/product/");
        me.add("/activity", ActivityController.class, "/activity/");
        me.add("/web", WebController.class, "/web/");
        me.add("/share", ShareController.class, "/share/");
        me.add("/document", DocumentController.class, "/document/");
        me.add("/image", ImageController.class, "/image/");
	}

	public void configPlugin(Plugins me) {
		C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://localhost:3306/airshow?autoReconnect=true&useUnicode=true&characterEncoding=utf8","root", "toor#@!");
		me.add(c3p0Plugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("user", User.class);
		arp.addMapping("pavilion", Pavilion.class);
		arp.addMapping("businessman", Businessman.class);
        arp.addMapping("exhibit", Exhibit.class);
		arp.addMapping("image", Image.class);
		arp.addMapping("news", News.class);
        arp.addMapping("notify", Notify.class);
		arp.addMapping("ad", Ad.class);
        arp.addMapping("activity", Activity.class);
        arp.addMapping("document", Document.class);
        arp.addMapping("activity_people", ActivityPeople.class);
        arp.addMapping("activity_people_image", ActivityPeopleImage.class);
        arp.addMapping("setting", Setting.class);
	}

	public void configInterceptor(Interceptors me) {
		me.add(new AuthInterceptor());
	}

	public void configHandler(Handlers me) {
	}

	public static void main(String[] args) {
		JFinal.start("WebRoot", 8080, "/", 5);
	}
}
