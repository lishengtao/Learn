package com.shengtao.test;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.junit.Test;

import com.shengtao.common.HibernateUtil;
import com.shengtao.domain.Student;
  
/** 
 * 使用Hibernate API完成CRUD 
 * 更复杂的持久化操作需要使用到Query接口 
 *  
 */  
public class HibernateTest {  
      
    @Test  
    public void testAdd(){  
        Student stu = new Student();  
        stu.setName("胜涛");  
        stu.setBirthday(new Date());  
        stu.setAge(1);  
        stu.setGender(true);  
        stu.setScore(66.8);  
          
        //利用工厂打开一个Session实例  
        Session session = HibernateUtil.getSession();  
          
        //开启一个操作事务  
        Transaction tx = session.beginTransaction();  
          
        //利用session进行持久化操作  
        session.save(stu);  
          
        //提交事务  
        tx.commit();  
          
        //关闭Session  
        session.close();  
    }  
      
    @Test  
    public void getStu(){  
          
        //持久化管理器  
        Session session = null;  
        Transaction tx = null;  
        try{  
            session = HibernateUtil.getSession();  
          
            tx = session.beginTransaction();  
          
            //根据ID查询实体对象  
            Student stu = (Student)session.get(Student.class, 1);  
              
            Assert.assertNotNull(stu);  
            System.out.println(stu);  
          
            tx.commit();  
        }catch(HibernateException he){  
            he.printStackTrace();  
            tx.rollback();  
        }finally{  
            if(null != session && session.isOpen()){  
                try{  
                    session.close();  
                }catch(HibernateException e){  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
      
    @Test  
    public void testUpdate(){  
        Session session = null;  
          
        try{  
            session = HibernateUtil.getSession();  
              
            session.beginTransaction();  
              
            Student stu = (Student)session.get(Student.class, 1);  
            stu.setName("zs");  
            stu.setScore(52.1);  
          
            session.update(stu);  
              
            session.getTransaction().commit();  
        }catch(HibernateException e){  
            Assert.fail();  
            e.printStackTrace();  
            session.getTransaction().rollback();  
        }finally{  
            if(session != null && session.isOpen()){  
                session.close();  
            }  
        }  
    }  
      
    @Test  
    public void testDelete(){  
        Session session = null;  
          
        try{  
            session = HibernateUtil.getSession();  
            //session.beginTransaction();  
              
            Student stu = (Student)session.get(Student.class, 2);  
            System.out.println(stu);  
            session.delete(stu);  
              
            //session.getTransaction().commit();  
        }catch(HibernateException e){  
            Assert.fail();  
            e.printStackTrace();  
            //session.getTransaction().rollback();  
        }finally{  
            if(session != null && session.isOpen()){  
                session.close();  
            }  
        }  
    }  
      
    @Test  
    public void testGet(){  
        Session session = null;  
          
        try{  
            session = HibernateUtil.getSession();  
            session.beginTransaction();  
              
            Student stu = (Student)session.get(Student.class, 2);  
  
            System.out.println(stu);  
              
            Student stu2 = (Student) session.get(Student.class, 2);  
              
            System.out.println(stu2);  
              
            session.getTransaction().commit();  
        }catch(HibernateException e){  
            Assert.fail();  
            e.printStackTrace();  
            session.getTransaction().rollback();  
        }finally{  
            if(session != null && session.isOpen()){  
                session.close();  
            }  
        }  
    }  
      
    @Test  
    public void testGet2(){  
            Session session = null;  
          
            session = HibernateUtil.getSession();  
            session.beginTransaction();  
            Student stu = (Student)session.get(Student.class, 2);  
            System.out.println(stu);  
            session.getTransaction().commit();  
            session.close();  
              
            session = HibernateUtil.getSession();  
            session.beginTransaction();  
            Student stu2 = (Student) session.get(Student.class, 2);  
            System.out.println(stu2);  
            session.getTransaction().commit();  
            session.close();  
    }  
      
    @SuppressWarnings("unchecked")  
    @Test  
    public void testQuery(){  
        Session session = null;  
        try{  
            session = HibernateUtil.getSession();  
            session.beginTransaction();  
              
            Query query = session.createQuery("from Student");  
            List<Student> stus = query.list();  
              
            for(Student stu : stus){  
                System.out.println(stu);  
            }  
              
            session.getTransaction().commit();  
          
        }catch(HibernateException e){  
            e.printStackTrace();  
            session.getTransaction().rollback();  
        }finally{  
            if(session != null && session.isOpen()){  
                session.close();  
            }  
        }  
    }  
      
    @SuppressWarnings("unchecked")  
    @Test  
    public void testCriteria(){  
        Session session = null;  
        try{  
            session = HibernateUtil.getSession();  
            session.beginTransaction();  
              
            Criteria criteria = session.createCriteria(Student.class);  
              
            List<Student> stus = criteria.addOrder(Order.desc("id")).list();  
              
            for(Student stu : stus){  
                System.out.println(stu);  
            }  
              
            session.getTransaction().commit();  
          
        }catch(HibernateException e){  
            e.printStackTrace();  
            session.getTransaction().rollback();  
        }finally{  
            if(session != null && session.isOpen()){  
                session.close();  
            }  
        }  
    }  
      
}
