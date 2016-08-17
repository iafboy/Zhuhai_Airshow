package com.food.product;

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

public class ProductController extends Controller {
	public void index() {
		String pageStr = getPara("page_id");
		int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
		Page<Exhibit> pageUser= Exhibit.dao.paginate(page, Util.pageLines, "select p.*,b.name as bname ","from exhibit p left join businessman b on p.business_id = b.id where p.id > ?",0);
		setAttr("allPages", pageUser.getTotalPage());
		setAttr("curPage", page);
		setAttr("prePage", page == 1 ? 1 : page -1);
		setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
		setAttr("productList", pageUser.getList());
		render("index.jsp");
	}

	public void check() {
		if (getPara("id") != null) {
			int id = getParaToInt("id");
			setAttr("productList", Exhibit.dao.find("select b.*,i.path from exhibit b left join image i on b.image_id=i.id where b.id=?", id));
			render("detail.jsp");
		} else {
			redirect(Util.getUrl("businessman", "index"));
		}
	}

	public void add() {
		render("add.jsp");
	}
	
	public void addCheck() {
		UploadFile image = getFile("image");

        Exhibit product = new Exhibit();
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

        int type = getParaToInt("type");
		String bid = getPara("bid");
		String name = getPara("name");
		String introduction = getPara("introduction");

		product.set("name", name)
                .set("type", type)
                .set("pavilion_id", Businessman.dao.findById(bid).get("pavilion_id"))
				.set("business_id", bid)
				.set("introduction", introduction)
				.save();
		redirect(Util.getUrl("product", "index"));
		
	}
	
	public void edit(){
		if(getPara("id") != null){
			int id = getParaToInt("id");
			if(Exhibit.dao.findById(id) != null){
                Exhibit product = Exhibit.dao.findById(id);
				setAttr("product", product);
				setAttr("target", Businessman.dao.findById(product.getInt("business_id")));
				render("edit.jsp");
			}else{
				redirect(Util.getUrl("product","index"));
			}
		}else{
			redirect(Util.getUrl("product","index"));
		}
	}
	
	public void editCheck(){
		if(getPara("id") != null){
			UploadFile image = getFile("image");
            Exhibit product = Exhibit.dao.findById(getPara("id"));

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

            int type = getParaToInt("type");
            String bid = getPara("bid");
			String name = getPara("name");
			String introduction = getPara("introduction");
			product.set("name", name)
                    .set("type", type)
                    .set("pavilion_id", Businessman.dao.findById(bid).get("pavilion_id"))
                    .set("business_id", bid)
					.set("introduction", introduction)
					.update();
			redirect(Util.getUrl("product", "index"));
		}else{
			redirect(Util.getUrl("product","index"));
		}
	}
	
	public void delete() {
		if (getPara("id") != null) {
            Exhibit.dao.deleteById(getParaToInt("id"));
		}
		redirect(Util.getUrl("product", "index"));
	}
	
	public void search() {
		if (getPara("keyword") != null) {
			renderJson(Exhibit.dao.find("select id,name from exhibit where name like '%"+getPara("keyword")+"%'"));
		}else{
			renderJson(Exhibit.dao.find("select id,name from exhibit"));
		}
		
	}

    public void uploadExcel() {
        render("uploadExcel.jsp");
    }

    public void uploadExcelCheck() {
        UploadFile file = getFile("file");
        if (file != null) {
            Db.update("delete from exhibit where id > 0");
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

                    Cell logo_idCell2 = sheet.getCell(5, x);
                    String pavilion_id = logo_idCell2.getContents();

                    Exhibit product = new Exhibit();
                    product.set("id", id)
                            .set("name", name)
                            .set("type", type)
                            .set("image_id", image_id)
                            .set("pavilion_id", pavilion_id)
                            .set("introduction", introduction)
                            .save();
                }


                book.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        redirect(Util.getUrl("exhibit", "index"));
    }
}
