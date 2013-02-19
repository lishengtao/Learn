package com.shengtao.test;

import org.hibernate.SessionFactory;
import org.junit.BeforeClass;

import com.shengtao.common.HibernateUtil;

public class HibernateQueryTest {
	private static SessionFactory factory;  
	  
    @BeforeClass  
    public static void init() {  
        factory = HibernateUtil.getSessionFactory();  
    }  
}
