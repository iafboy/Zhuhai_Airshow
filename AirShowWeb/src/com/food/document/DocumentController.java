package com.food.document;

import com.food.config.Util;
import com.food.product.Exhibit;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DocumentController extends Controller {
	public void index() {
		String pageStr = getPara("page_id");
		int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
		Page<Document> pageDocument= Document.dao.paginate(page, Util.pageLines, "select * ","from document where id > ?",0);
		System.out.println(pageDocument.getTotalPage());
        setAttr("allPages", pageDocument.getTotalPage());
		setAttr("curPage", page);
		setAttr("prePage", page == 1 ? 1 : page -1);
		setAttr("lasPage", page == pageDocument.getTotalPage() ? pageDocument.getTotalPage() : page + 1);
		setAttr("documentList", pageDocument.getList());
		render("index.jsp");
	}
	
	public void add(){
		render("add.jsp");
	}
	
	public void addCheck(){
        String id = getPara("id");
		String phone = getPara("phone");
		String password = getPara("password");
		String email = getPara("email");
		String introduction = getPara("introduction");
        if(Document.dao.find("select id from user where id = '"+id+"'").size() > 0){
            redirect(Util.getUrl("user","add"));
		}else{
			new Document().set("id", id)
                      .set("phone", phone)
					  .set("password", password)
					  .set("email", email)
					  .set("introduction", introduction)
					  .save();
			redirect(Util.getUrl("document","index"));
		}
	}
	
	public void edit(){
		if(getPara("id") != null){
            String id = getPara("id");
			if(Document.dao.findById(id) != null){
				setAttr("user", Document.dao.findById(id));
				render("edit.jsp");
			}else{
				redirect(Util.getUrl("user","index"));
			}
		}else{
			redirect(Util.getUrl("document","index"));
		}
	}

	public void editCheck(){
		if(getPara("id") != null){
            String id = getPara("id");
			String phone = getPara("phone");
			String email = getPara("email");
			String introduction = getPara("introduction");
			Document user = Document.dao.findById(id);
			if(user != null){
                user.set("id", id);
				user.set("phone", phone);
				user.set("introduction", introduction);
				user.set("email", email);
				user.update();
				redirect(Util.getUrl("document","index"));
			}else{
				redirect(Util.getUrl("document","index"));
			}
		}else{
			redirect(Util.getUrl("document","index"));
		}
	}
	
	public void delete(){
		if(getPara("id") != null){
			Document.dao.deleteById(getPara("id"));
		}
		redirect(Util.getUrl("document","index"));
	}

    public void uploadExcel() {
        render("uploadExcel.jsp");
    }

    public void uploadExcelCheck() {
        UploadFile file = getFile("file");
        if (file != null) {
            Db.update("delete from document where id > 0");
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
                    String type = typeCell.getContents();

                    Cell nameCell = sheet.getCell(2, x);
                    String image_id = nameCell.getContents();

                    Cell companyCell = sheet.getCell(3, x);
                    String image_name = companyCell.getContents();

                    Cell logo_idCell = sheet.getCell(4, x);
                    String introduction = logo_idCell.getContents();

                    Document document = new Document();
                    document.set("id", id)
                            .set("type", type)
                            .set("image_id", image_id)
                            .set("image_name", image_name)
                            .set("introduction", introduction)
                            .save();
                }

                book.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        redirect(Util.getUrl("document", "index"));
    }

}
