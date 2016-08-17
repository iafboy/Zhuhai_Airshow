package com.food.user;

import com.food.config.Util;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

public class UserController extends Controller {
	public void index() {
		String pageStr = getPara("page_id");
		int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
		Page<User> pageUser= User.dao.paginate(page, Util.pageLines, "select * ","from user where id > ?",0);
		setAttr("allPages", pageUser.getTotalPage());
		setAttr("curPage", page);
		setAttr("prePage", page == 1 ? 1 : page -1);
		setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
		setAttr("userList", pageUser.getList());
		render("index.jsp");
	}
	
	public void add(){
		render("add.jsp");
	}
	
	@Before(UserAddValidator.class)
	public void addCheck(){
        String id = getPara("id");
		String phone = getPara("phone");
		String password = getPara("password");
		String email = getPara("email");
		String introduction = getPara("introduction");
        if(User.dao.find("select id from user where id = '"+id+"'").size() > 0){
            redirect(Util.getUrl("user","add"));
		}else{
			new User().set("id", id)
                      .set("phone", phone)
					  .set("password", password)
					  .set("email", email)
					  .set("introduction", introduction)
					  .save();
			redirect(Util.getUrl("user","index"));
		}
	}
	
	public void edit(){
		if(getPara("id") != null){
            String id = getPara("id");
			if(User.dao.findById(id) != null){
				setAttr("user", User.dao.findById(id));
				render("edit.jsp");
			}else{
				redirect(Util.getUrl("user","index"));
			}
		}else{
			redirect(Util.getUrl("user","index"));
		}
	}

	public void editCheck(){
		if(getPara("id") != null){
            String id = getPara("id");
			String phone = getPara("phone");
			String email = getPara("email");
			String introduction = getPara("introduction");
			User user = User.dao.findById(id);
			if(user != null){
                user.set("id", id);
				user.set("phone", phone);
				user.set("introduction", introduction);
				user.set("email", email);
				user.update();
				redirect(Util.getUrl("user","index"));
			}else{
				redirect(Util.getUrl("user","index"));
			}
		}else{
			redirect(Util.getUrl("user","index"));
		}
	}
	
	public void delete(){
		if(getPara("id") != null){
			User.dao.deleteById(getPara("id"));
		}
		redirect(Util.getUrl("user","index"));
	}
}
