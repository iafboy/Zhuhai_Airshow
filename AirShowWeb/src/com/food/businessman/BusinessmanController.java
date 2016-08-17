package com.food.businessman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import com.food.config.FileUtil;
import com.food.config.Util;
import com.food.image.Image;
import com.food.pavilion.Pavilion;
import com.food.product.Exhibit;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class BusinessmanController extends Controller {
	public void index() {
		String pageStr = getPara("page_id");
		int page = (pageStr == null ? 1 : Integer.parseInt(pageStr));
		Page<Businessman> pageUser= Businessman.dao.paginate(page, Util.pageLines, "select * ","from businessman where id > ?",0);
		setAttr("allPages", pageUser.getTotalPage());
		setAttr("curPage", page);
		setAttr("prePage", page == 1 ? 1 : page -1);
		setAttr("lasPage", page == pageUser.getTotalPage() ? pageUser.getTotalPage() : page + 1);
		setAttr("businessmanList", pageUser.getList());
		render("index.jsp");
	}

	public void check() {
		if (getPara("id") != null) {
			int id = getParaToInt("id");
			String sql = "select b.*,i.path,ii.path as logo from businessman b left join image i on b.image_id=i.id left join image ii on b.logo_id = ii.id where b.id=?";
			Businessman businessman = Businessman.dao.findFirst(sql, id); 
			setAttr("businessman", businessman);
			render("detail.jsp");
		} else {
			redirect(Util.getUrl("businessman", "index"));
		}
	}

	public void add() {
        setAttr("pavilionList", Pavilion.dao.find("select * from pavilion"));
		render("add.jsp");
	}

	@Before(BusinessAddValidator.class)
	public void addCheck(){
		UploadFile image = getFile("image");
        UploadFile logo = getFile("logo");
        Businessman businessman = new Businessman();

        if(image != null){
            String imageFileName = FileUtil.createNewFile(image.getFile());
            new Image().set("name", imageFileName)
                    .set("introduction", imageFileName)
                    .set("path", "/upload/"+imageFileName)
                    .set("time", new Timestamp(System.currentTimeMillis()))
                    .save();
            Image ima = Image.dao.findFirst("select id from image where name = '"+imageFileName+"'");
            int image_id = ima.get("id");
            businessman.set("image_id", image_id);
        }

        if(logo != null){
            String logoFileName = FileUtil.createNewFile(logo.getFile());
            new Image().set("name", logoFileName)
                    .set("introduction", logoFileName)
                    .set("path", "/upload/"+logoFileName)
                    .set("time", new Timestamp(System.currentTimeMillis()))
                    .save();
            Image ima = Image.dao.findFirst("select id from image where name = '"+logoFileName+"'");
            int logo_id = ima.get("id");
            businessman.set("logo_id", logo_id);
        }

        String pavilion_id = getPara("pavilion_id");
		String name = getPara("name");
		String website = getPara("website");
		String phone = getPara("phone");
		String email = getPara("email");
		String address = getPara("address");
		String fax = getPara("fax");
		String weixin = getPara("weixin");
		String weibo = getPara("weibo");
		String introduction = getPara("introduction");
			
		businessman.set("name", name)
                    .set("pavilion_id", pavilion_id)
					.set("website", website)
					.set("phone", phone)
					.set("email", email)
					.set("address", address)
					.set("fax", fax)
					.set("weixin", weixin)
					.set("weibo", weibo)
					.set("introduction", introduction);
		
		businessman.save();
		
		redirect(Util.getUrl("businessman", "index"));
	}

	public void edit() {
		if (getPara("id") != null) {
			int id = getParaToInt("id");
			if (Businessman.dao.findById(id) != null) {
				setAttr("user", Businessman.dao.findById(id));
                setAttr("pavilionList", Pavilion.dao.find("select * from pavilion"));
				render("edit.jsp");
			} else {
				redirect(Util.getUrl("businessman", "index"));
			}
		} else {
			redirect(Util.getUrl("businessman", "index"));
		}
	}

	public void editCheck() {
		if (getPara("id") != null) {
			int id = getParaToInt("id");
            UploadFile logo = getFile("logo");
			UploadFile image = getFile("image");
			Businessman businessman = Businessman.dao.findById(id);

			if(image != null){
				String imageFileName = FileUtil.createNewFile(image.getFile());
				new Image().set("name", imageFileName)
							.set("introduction", imageFileName)
							.set("path", "/upload/"+imageFileName)
							.set("time", new Timestamp(System.currentTimeMillis()))
							.save();
				Image ima = Image.dao.findFirst("select id from image where name = '"+imageFileName+"'");
				int image_id = ima.get("id");
				businessman.set("image_id", image_id);
			}

            if(logo != null){
                String logoFileName = FileUtil.createNewFile(logo.getFile());
                new Image().set("name", logoFileName)
                        .set("introduction", logoFileName)
                        .set("path", "/upload/"+logoFileName)
                        .set("time", new Timestamp(System.currentTimeMillis()))
                        .save();
                Image ima = Image.dao.findFirst("select id from image where name = '"+logoFileName+"'");
                int logo_id = ima.get("id");
                businessman.set("logo_id", logo_id);
            }

            String pavilion_id = getPara("pavilion_id");
            String name = getPara("name");
			String website = getPara("website");
			String phone = getPara("phone");
			String email = getPara("email");
			String address = getPara("address");
			String fax = getPara("fax");
			String weixin = getPara("weixin");
			String weibo = getPara("weibo");
			String introduction = getPara("introduction");
				
			businessman.set("name", name)
                        .set("pavilion_id", pavilion_id)
						.set("website", website)
						.set("phone", phone)
						.set("email", email)
						.set("address", address)
						.set("fax", fax)
						.set("weixin", weixin)
						.set("weibo", weibo)
						.set("introduction", introduction);
			businessman.update();
		}
		
		redirect(Util.getUrl("businessman", "index"));
	}

	public void delete() {
		if (getPara("id") != null) {
			Businessman.dao.deleteById(getParaToInt("id"));
		}
		redirect(Util.getUrl("businessman", "index"));
	}
	
	public void addProduct() {
		if (getPara("id") != null) {
			int bid = getParaToInt("id");
			setAttr("bid", bid);
			render("addProduct.jsp");
		}else{
			redirect(Util.getUrl("businessman", "index"));
		}
		
	}
	
	public void addProductCheck() {
		if (getPara("bid") != null) {
			int bid = getParaToInt("bid");
			UploadFile uploadFile = getFile("image");
			File file = uploadFile.getFile();
			String lastName = "jpg";
			if(file.getName().split("\\.").length>1){
				lastName = file.getName().split("\\.")[1];
			}
			InputStream inStream;
			String newFileName = System.currentTimeMillis()+"."+lastName;
			try {
				inStream = new FileInputStream(file);
		        FileOutputStream fs = new FileOutputStream(file.getParent()+"/"+newFileName); 
		        byte[] buffer = new byte[1444]; 
		        int bytesum = 0; 
		        int byteread = 0; 
		        while ( (byteread = inStream.read(buffer)) != -1) { 
		             bytesum += byteread;  
		             System.out.println(bytesum); 
		             fs.write(buffer, 0, byteread); 
		        } 
		        inStream.close(); 
		        fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
	         
			file.delete();
			
			String name = getPara("name");
			String introduction = getPara("introduction");
			
			new Image().set("name", newFileName)
						.set("introduction", newFileName)
						.set("path", "/upload/"+newFileName)
						.set("time", new Timestamp(System.currentTimeMillis()))
						.save();

			Image image = Image.dao.findFirst("select id from image where name = '"+newFileName+"'");
			int image_id = image.get("id");
			
			new Exhibit().set("name", name)
							.set("bid", bid)
							.set("introduction", introduction)
							.set("image_id", image_id)
							.save();
			redirect(Util.getUrl("businessman", "index"));
		}else{
			redirect(Util.getUrl("businessman", "index"));
		}
		
	}
	
	public void deleteProduct() {
		if (getPara("id") != null) {
            Exhibit.dao.deleteById(getParaToInt("id"));
		}
		redirect(Util.getUrl("businessman", "index"));
	}
	
	public void search() {
		if (getPara("keyword") != null) {
			renderJson(Businessman.dao.find("select id,name from businessman where name like '%"+getPara("keyword")+"%'"));
		}else{
			renderJson(Businessman.dao.find("select id,name from businessman"));
		}
		
	}

    public void uploadExcel() {
        render("uploadExcel.jsp");
    }

    public void uploadExcelCheck() {
        UploadFile file = getFile("file");
        if (file != null) {
            Db.update("delete from businessman where id > 0");
            try {
                Workbook book = Workbook.getWorkbook(file.getFile());
                // 获得第一个工作表对象
                Sheet sheet = book.getSheet(0);
                // 得到第一列第一行的单元格

	            for(int x = 1; x<Integer.MAX_VALUE; x++){
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
                        String website = companyCell.getContents();

                        Cell logo_idCell = sheet.getCell(4, x);
                        String phone = logo_idCell.getContents();

                        Cell image_idCell = sheet.getCell(5, x);
                        String address = image_idCell.getContents();

                        Cell positionCell = sheet.getCell(6, x);
                        String email = positionCell.getContents();

                        Cell position_idCell = sheet.getCell(7, x);
                        String weixin = position_idCell.getContents();

                        Cell introductionCell = sheet.getCell(8, x);
                        String weibo = introductionCell.getContents();

                        Cell addressCell = sheet.getCell(9, x);
                        String image_id = addressCell.getContents();

                        Cell zipcodeCell = sheet.getCell(10, x);
                        String logo_id = zipcodeCell.getContents();

                        Cell phoneCell = sheet.getCell(11, x);
                        String pavilion_id = phoneCell.getContents();


                        Businessman businessman = new Businessman();
                        businessman.set("id", id)
                                .set("name", name)
                                .set("introduction", introduction)
                                .set("website", website)
                                .set("phone", phone)
                                .set("address", address)
                                .set("email", email)
                                .set("weixin", weixin)
                                .set("weibo", weibo);

                        if(pavilion_id != null && pavilion_id.trim().length() > 0){
                            businessman.set("pavilion_id", pavilion_id);
                        }else{
                            businessman.set("pavilion_id", null);
                        }

                        if(image_id != null && image_id.trim().length() > 0){
                            businessman.set("image_id", image_id);
                        }else{
                            businessman.set("image_id", null);
                        }
                        businessman.save();

	            }

                book.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        redirect(Util.getUrl("businessman", "index"));
    }
}
