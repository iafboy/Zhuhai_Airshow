package com.airshow.server.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class ResultUtil {

	public static boolean getData(ResultSet rs, Object o) {
		Class<? extends Object> cls = o.getClass();
		Field[] flds = cls.getDeclaredFields();
		for(Field f : flds) {
			f.setAccessible(true);
			try {
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(), cls);
				Method wM = pd.getWriteMethod();
				if(f.getType() == String.class) {
					wM.invoke(o,rs.getString(f.getName()));
				}else if(f.getType().toString().equals("int")){
					wM.invoke(o,rs.getInt(f.getName()));
				}else if(f.getType().toString().equals("double")){
					wM.invoke(o,rs.getDouble(f.getName()));
				}else if(f.getType() == Timestamp.class){
					wM.invoke(o,rs.getTimestamp(f.getName()));
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
