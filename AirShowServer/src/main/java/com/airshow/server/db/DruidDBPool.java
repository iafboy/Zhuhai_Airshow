package com.airshow.server.db;

import com.airshow.server.common.Constants;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by DAbing on 2014-10-28.
 */
public class DruidDBPool {
    private static volatile DruidDBPool dbPool;
    private static DruidDataSource dataSource;
    private DruidDBPool() {
        Properties prop =  new  Properties();
        try  {
            InputStream ins = new BufferedInputStream(new FileInputStream(Constants.dbpoolCfg));
            prop.load(ins);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
        }catch(Exception ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }
    public static DruidDBPool getInstance(){
        if(dbPool==null){
            synchronized(DruidDBPool.class) {
                if(dbPool==null)
                    dbPool = new DruidDBPool();
            }
        }
        return dbPool;
    }
    public DruidPooledConnection getConnection(){
        DruidPooledConnection con=null;
        try {
            con=dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return con;
    }
    public void init() {
        try {
            dataSource.getConnection().recycle();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        if(dataSource!=null){
            dataSource.close();
        }
    }

    public static void main(String[] args) {
        DruidPooledConnection con = null;
        try {
            con = DruidDBPool.getInstance().getConnection();
            System.out.println("OK");
            System.out.println(con);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
