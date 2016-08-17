package com.food.pavilion;

import java.sql.Timestamp;

import com.food.config.FileUtil;
import com.food.config.Util;
import com.food.image.Image;
import com.food.product.Exhibit;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class PavilionController extends Controller {
	public void index() {
		String pageStr = getPara("page_id");
		int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
		Page<Pavilion> pageUser= Pavilion.dao.paginate(page, Util.pageLines, "select * ","from pavilion where id > ?",0);
		setAttr("allPages", pageUser.getTotalPage());
		setAttr("curPage", page);
		setAttr("prePage", page == 1 ? 1 : page -1);
		setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
		setAttr("pavilionList", pageUser.getList());
		render("index.jsp");
	}
	
	public void add(){
		render("add.jsp");
	}
	
	public void addCheck(){
        UploadFile image = getFile("image");

        int id = getParaToInt("id");
        if(Pavilion.dao.findById(id) == null) {
            Pavilion pavilion = new Pavilion();

            if(image != null){
                String imageFileName = FileUtil.createNewFile(image.getFile());
                new Image().set("name", imageFileName)
                        .set("introduction", imageFileName)
                        .set("path", "/upload/"+imageFileName)
                        .set("time", new Timestamp(System.currentTimeMillis()))
                        .save();
                Image ima = Image.dao.findFirst("select id from image where name = '"+imageFileName+"'");
                int image_id = ima.get("id");
                pavilion.set("image_id", image_id);
            }

            String name = getPara("name");
            String introduction = getPara("introduction");
            pavilion.set("id", id)
                    .set("name", name)
                    .set("introduction", introduction)
                    .set("time", new Timestamp(System.currentTimeMillis()))
                    .save();

            redirect(Util.getUrl("pavilion", "index"));
        }else{
            redirect(Util.getUrl("pavilion", "add"));
        }
	}
	
	public void edit(){
		if(getPara("id") != null){
			int id = getParaToInt("id");
            Pavilion pavilion = Pavilion.dao.findById(id);
            if(pavilion != null){
                setAttr("path", Image.dao.findById(pavilion.get("image_id")).get("path"));
				setAttr("pavilion", pavilion);
				render("edit.jsp");
			}else{
				redirect(Util.getUrl("pavilion","index"));
			}
		}else{
			redirect(Util.getUrl("pavilion","index"));
		}
	}

	public void editCheck(){
		if(getPara("id") != null){
			int id = getParaToInt("id");
			Pavilion pavilion = Pavilion.dao.findById(id);
			if(pavilion != null){
                UploadFile image = getFile("image");

                if(image != null){
                    String imageFileName = FileUtil.createNewFile(image.getFile());
                    new Image().set("name", imageFileName)
                            .set("introduction", imageFileName)
                            .set("path", "/upload/"+imageFileName)
                            .set("time", new Timestamp(System.currentTimeMillis()))
                            .save();
                    Image ima = Image.dao.findFirst("select id from image where name = '"+imageFileName+"'");
                    int image_id = ima.get("id");
                    pavilion.set("image_id", image_id);
                }

                String name = getPara("name");
                String introduction = getPara("introduction");
                pavilion.set("id", id)
                        .set("name", name)
                        .set("introduction", introduction)
                        .set("time", new Timestamp(System.currentTimeMillis()))
                        .update();

				redirect(Util.getUrl("pavilion","index"));
			}else{
				redirect(Util.getUrl("pavilion","index"));
			}
		}else{
			redirect(Util.getUrl("pavilion","index"));
		}
	}
	
	public void delete(){
		if(getPara("id") != null){
			Pavilion.dao.deleteById(getParaToInt("id"));
		}
		redirect(Util.getUrl("pavilion","index"));
	}

    public void uploadExcel() {
        render("uploadExcel.jsp");
    }

    public void uploadExcelCheck() {
        UploadFile file = getFile("file");
        if (file != null) {
            Db.update("delete from pavilion where id > 0");
            try {
                Workbook book = Workbook.getWorkbook(file.getFile());
                // 获得第一个工作表对象
                Sheet sheet = book.getSheet(0);
                // 得到第一列第一行的单元格

                for(int x = 1; x<Integer.MAX_VALUE; x++) {

                    Cell idCell = sheet.getCell(0, x);
                    String id = idCell.getContents();
                    if (id.trim().length() == 0) {
                        break;
                    }

                    Cell typeCell = sheet.getCell(1, x);
                    String name = typeCell.getContents();

                    Cell nameCell = sheet.getCell(2, x);
                    String introduction = nameCell.getContents();

                    Cell companyCell = sheet.getCell(3, x);
                    String time = companyCell.getContents();

                    Cell logo_idCell = sheet.getCell(4, x);
                    String image_id = logo_idCell.getContents();


                    Pavilion pavilion = new Pavilion();
                    pavilion.set("image_id", image_id)
                            .set("id", id)
                            .set("name", name)
                            .set("introduction", introduction)
                            .save();
                }

                book.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        redirect(Util.getUrl("pavilion", "index"));
    }
}
