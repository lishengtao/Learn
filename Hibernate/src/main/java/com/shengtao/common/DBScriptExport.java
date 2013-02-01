package com.shengtao.common;

import org.hibernate.cfg.Configuration;  
import org.hibernate.tool.hbm2ddl.SchemaExport;  
  
/** 
 * 根据对象关系映射文件直接生成数据库表或生成建表的脚本 
 *  
 */  
public class DBScriptExport {  
  
    public static void main(String[] args) {  
        //export2File("dbcript.sql");  
    	export2DB();
    }  
      
    public static void export2DB(){  
    	//加载Hibernate的全局配置文件 
        Configuration config = new Configuration().configure();  
        SchemaExport export = new SchemaExport(config);  
        export.create(true, true);  
    }  
      
    public static void export2File(String dest){  
        Configuration config = new Configuration().configure();  
        SchemaExport export = new SchemaExport(config);  
          
        export.setOutputFile(dest)  
            .setDelimiter(";")  
            .setFormat(true)  
            .create(true, false);  
    }  
  
}  