package com.food.activity;

import java.sql.Timestamp;

import com.food.businessman.Businessman;
import com.food.config.FileUtil;
import com.food.config.Util;
import com.food.image.Image;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ActivityController extends Controller {
	public void index() {
		String pageStr = getPara("page_id");
		int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
        String typeStr = getPara("type");
        int type = (typeStr == null ? 0 : Integer.parseInt(typeStr));
        Page<Activity> pageUser;
        if(type == 0) {
            pageUser = Activity.dao.paginate(page, Util.pageLines, "select * ", " from activity where type > ?", 0);
        }else {
            pageUser = Activity.dao.paginate(page, Util.pageLines, "select * ", " from activity where type = ?", type);
        }
        setAttr("allPages", pageUser.getTotalPage());
		setAttr("curPage", page);
		setAttr("prePage", page == 1 ? 1 : page -1);
		setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
		setAttr("activityList", pageUser.getList());
        setAttr("type", type);
		render("index.jsp");
	}

	public void check() {
		if (getPara("id") != null) {
			int id = getParaToInt("id");
            setAttr("activity", Activity.dao.findFirst("select b.*,i.path from activity b left join image i on b.image_id=i.id where b.id=?", id));
			render("detail.jsp");
		} else {
			redirect(Util.getUrl("activity", "index"));
		}
	}

	public void add() {
		render("add.jsp");
	}
	
	public void addCheck() {
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

		String type = getPara("type");
		String title = getPara("title");
		String introduction = getPara("introduction");
        String time = getPara("time");

		product.set("type", type)
				.set("title", title)
				.set("introduction", introduction)
                .set("publish_time", new Timestamp(System.currentTimeMillis()))
                .set("time", time)
				.save();
		redirect(Util.getUrl("activity", "index"));
		
	}
	
	public void edit(){
		if(getPara("id") != null){
			int id = getParaToInt("id");
			if(Activity.dao.findById(id) != null){
                setAttr("activity", Activity.dao.findFirst("select b.*,i.path from activity b left join image i on b.image_id=i.id where b.id=?", id));
            }else{
				redirect(Util.getUrl("product","index"));
			}
		}else{
			redirect(Util.getUrl("product","index"));
		}
	}
	
	public void editCheck(){
        UploadFile image = getFile("image");
        if(getPara("id") != null) {
            Activity product = Activity.dao.findById(getPara("id"));

            if (image != null) {
                String imageFileName = FileUtil.createNewFile(image.getFile());
                new Image().set("name", imageFileName)
                        .set("introduction", imageFileName)
                        .set("path", "/upload/" + imageFileName)
                        .set("time", new Timestamp(System.currentTimeMillis()))
                        .save();
                Image ima = Image.dao.findFirst("select id from image where name = '" + imageFileName + "'");
                int image_id = ima.get("id");
                product.set("image_id", image_id);
            }

            String type = getPara("type");
            String title = getPara("title");
            String introduction = getPara("introduction");
            String time = getPara("time");

            product.set("type", type)
                    .set("title", title)
                    .set("introduction", introduction)
                    .set("publish_time", new Timestamp(System.currentTimeMillis()))
                    .set("time", time)
                    .update();
        }
        redirect(Util.getUrl("activity", "index"));
	}
	
	public void delete() {
		if (getPara("id") != null) {
            Activity.dao.deleteById(getParaToInt("id"));
		}
		redirect(Util.getUrl("product", "index"));
	}
	
	public void search() {
		if (getPara("keyword") != null) {
			renderJson(Activity.dao.find("select id,name from activity where name like '%"+getPara("keyword")+"%'"));
		}else{
			renderJson(Activity.dao.find("select id,name from activity"));
		}
		
	}

    public void indexPeople(){
        String pageStr = getPara("page_id");
        int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
        String typeStr = getPara("type");
        int type = (typeStr == null ? 0 : Integer.parseInt(typeStr));
        Page<ActivityPeople> pageUser;
        if(type == 0) {
            pageUser = ActivityPeople.dao.paginate(page, Util.pageLines, "select * ", " from activity_people where type > ?", 0);
        }else {
            pageUser = ActivityPeople.dao.paginate(page, Util.pageLines, "select * ", " from activity_people where type = ?", type);
        }
        setAttr("allPages", pageUser.getTotalPage());
        setAttr("curPage", page);
        setAttr("prePage", page == 1 ? 1 : page -1);
        setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
        setAttr("activityList", pageUser.getList());
        setAttr("type", type);
        render("indexPeople.jsp");
    }

    public void uploadExcelPeople() {
        render("uploadExcelPeople.jsp");
    }

    public void uploadExcelPeopleCheck() {
        UploadFile file = getFile("file");
        if (file != null) {
            Db.update("delete from activity_people where id > 0");
            try {
                Workbook book = Workbook.getWorkbook(file.getFile());
                // 获得第一个工作表对象
                Sheet sheet = book.getSheet(0);
                // 得到第一列第一行的单元格
                for(int x = 1; x<Integer.MAX_VALUE; x++){

                    Cell idCell = sheet.getCell(0, x);
                    String id = idCell.getContents();
                    if(id.trim().length()  == 0){
                        break;
                    }

                    Cell typeCell = sheet.getCell(1, x);
                    String type = typeCell.getContents();

                    Cell nameCell = sheet.getCell(2, x);
                    String name = nameCell.getContents();

                    Cell companyCell = sheet.getCell(3, x);
                    String introduction = companyCell.getContents();

                    Cell logo_idCell = sheet.getCell(4, x);
                    String image_id = logo_idCell.getContents();

                    ActivityPeople activityPeople = new ActivityPeople();
                    activityPeople
                            .set("id", id)
                            .set("type", type)
                            .set("name", name)
                            .set("introduction", introduction)
                            .set("image_id", image_id).save();
                }
                book.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        redirect(Util.getUrl("activity", "indexPeople"));
    }

    public void indexPeopleImage(){
        String pageStr = getPara("page_id");
        int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
        Page<ActivityPeopleImage> pageUser;
        pageUser = ActivityPeopleImage.dao.paginate(page, Util.pageLines, "select * ", " from activity_people_image where id > ?", 0);
        setAttr("allPages", pageUser.getTotalPage());
        setAttr("curPage", page);
        setAttr("prePage", page == 1 ? 1 : page -1);
        setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
        setAttr("activityList", pageUser.getList());
        render("indexPeopleImage.jsp");
    }

    public void uploadExcelPeopleImage() {
        render("uploadExcelPeopleImage.jsp");
    }

    public void uploadExcelPeopleImageCheck() {
        UploadFile file = getFile("file");
        if (file != null) {
            Db.update("delete from activity_people_image where id > 0");
            try {
                Workbook book = Workbook.getWorkbook(file.getFile());
                // 获得第一个工作表对象
                Sheet sheet = book.getSheet(0);
                // 得到第一列第一行的单元格
                for(int x = 1; x<Integer.MAX_VALUE; x++){

                    Cell idCell = sheet.getCell(0, x);
                    String id = idCell.getContents();
                    if(id.trim().length()  == 0){
                        break;
                    }

                    Cell typeCell = sheet.getCell(1, x);
                    String pid = typeCell.getContents();

                    Cell nameCell = sheet.getCell(2, x);
                    String image_id = nameCell.getContents();

                    Cell companyCell = sheet.getCell(3, x);
                    String image_name = companyCell.getContents();

                    Cell logo_idCell = sheet.getCell(4, x);
                    String image_introduction = logo_idCell.getContents();

                    ActivityPeopleImage image = new ActivityPeopleImage();
                    image
                            .set("id", id)
                            .set("pid", pid)
                            .set("image_id", image_id)
                            .set("image_name", image_name)
                            .set("image_introduction", image_introduction).save();
                }
                book.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        redirect(Util.getUrl("activity", "indexPeopleImage"));
    }
}
