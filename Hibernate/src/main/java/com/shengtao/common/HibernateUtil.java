package com.shengtao.common;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.cfg.Configuration;  
  
/** 
 * 
 * Hibernate工具类 
 *  
 */  
public class HibernateUtil {  
    private static final SessionFactory factory;  
      
    private HibernateUtil(){}  
      
    static{  
        //加载Hibernate全局配置文件,根据配置信息创建SessionFactory工厂实例  
        factory = new Configuration().configure().buildSessionFactory();  
    }  
      
    public static SessionFactory getSessionFactory(){  
        return factory;  
    }  
      
    public static Session getSession(){  
        return factory.openSession();  
    }  
}  
