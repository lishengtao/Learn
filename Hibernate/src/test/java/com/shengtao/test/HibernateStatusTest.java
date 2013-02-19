package com.shengtao.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

import com.shengtao.common.HibernateUtil;
import com.shengtao.domain.Student;
  
/** 
 * 使用Hibernate API完成CRUD 更复杂的持久化操作需要使用到Query接口 
 *  
 */  
public class HibernateStatusTest {  
  
    private static SessionFactory factory;  
  
    @BeforeClass  
    public static void init() {  
        factory = HibernateUtil.getSessionFactory();  
    }  
  
    @Test  
    public void testSessionCache() {  
        Session session = factory.openSession();  
        Transaction tx = session.beginTransaction();  
  
        Student stu = (Student) session.get(Student.class, 1);  
        System.out.println(stu);  
  
        Student stu2 = (Student) session.get(Student.class, 1);  
        System.out.println(stu2);  
  
        stu2.setName("mix");  
        System.out.println(stu2);  
        /*flush()将数据库与缓存中的数据同步,不是必须调用的*/  
        session.flush(); // 手动清理缓存  
      
        /* 
         * clear()写在flush后面，执行后才会引起缓存数据变化，session.flush()的调用牵扯到事务， 
         * 首先我们知道在执行事务之前都会将AutoCommit设置为false【手动提交方式，因为默认是true 
         * 自动提交的】 当AutoCommit 为false时我们执行完事务就要调用到session.flush(); 
         * session.clear();一切处理完后我们要close掉当前的这个session 
         */  
        session.clear();  
  
        stu2.setScore(77.00);  
        System.out.println(stu2);  
  
        tx.commit();  
        session.close();  
    }  
  
    @Test  
    public void testObjectStatus() {  
        Student stu = new Student(); // transient瞬时状态  
        stu.setName("ww");  
        stu.setGender(false);  
        stu.setAge(38);  
        stu.setScore(65.5);  
  
        Session session = factory.openSession();  
        Transaction tx = session.beginTransaction();  
  
        session.save(stu); // persistent持久化状态  
        System.out.println(stu);  
  
        tx.commit();  
        session.close();  
  
        System.out.println(stu); // detached 脱管  
  
        Session session2 = factory.openSession();  
        session2.beginTransaction();  
  
        session2.save(stu);  
        System.out.println(stu);// 脱管状态--> 持久化状态 (不建议用save方法来操作脱管对象)  
  
        session2.getTransaction().commit();  
        session2.close();  
    }  
  
    /* 
     * get()方法：先查找session缓存中是否已经存在此标识符指定的对象,如果存在,直接使用. 
     * 否则发出SQL语句从数据库中获取.如果数据库中也不存在,返回null 
     */  
    @Test  
    public void testGet() {  
  
        Session session = factory.openSession();  
        session.beginTransaction();  
  
        Student stu = (Student) session.get(Student.class, 1);  
  
        System.out.println(stu);  
  
        session.getTransaction().commit();  
        session.close();  
    }  
  
    /* 
     * load()方法先查找session缓存中是否已经存在此标识符指定的对象,如果存在,直接使用. 
     * 否则Hibernate会为此标识符对象产生一个代理对象,实现延迟加载(懒加载)的功能。这个代理对象包含有OID。 
     * 当要使用到此对象的非OID属性值，才发出SQL语句去数据库中获取。 
     * 如果数据库中也不存在，返回InvocationTargetException异常。 
     */  
    @Test  
    public void testLoad() {  
        Session session = factory.openSession();  
        session.beginTransaction();  
  
        Student stu = (Student) session.load(Student.class, 5);  
  
        System.out.println(stu.getId());  
  
        System.out.println(stu);  
  
        session.getTransaction().commit();  
        session.close();  
    }  
  
    /* 
     * delete()方法:持久化状态 --> 移除状态 . 
     *  注意:处理移除状态的对象不要再去使用它,因为,在session清理缓存时,数据库表中对应的数据会被删除掉. 
     */  
    @Test  
    public void testDelete() {  
  
        Session session = factory.openSession();  
        session.beginTransaction();  
  
        Student stu = (Student) session.load(Student.class, 7);  
  
        session.delete(stu);  
  
        System.out.println(stu);  
  
        session.getTransaction().commit();  
        session.close();  
  
        System.out.println(stu);  
    }  
  
    @Test  
    /* update()方法: 重附被修改的脱管对象,成为持久化对象 */  
    public void testUpdate() {  
        Session session = factory.openSession();  
        session.beginTransaction();  
  
        Student stu = (Student) session.load(Student.class, 8);  
  
        stu.setName("更新持久化状态的对象");  
  
        session.getTransaction().commit();  
        session.close();  
  
        System.out.println(stu);  
        stu.setName("修改脱管对象");  
  
        Session session2 = factory.openSession();  
        session2.beginTransaction();  
        session2.update(stu);  
        session2.getTransaction().commit();  
        session2.close();  
    }  
  
    @Test  
    /* 
     * saveOrUpdate()方法：  
     * 1) 瞬时对象，执行类似save()的功能  
     * 2) 脱管对象，如果在当前session缓存中不存在同OID的对象，就执行类似update()的功能。否则，抛出异常。 
     */  
    public void testSaveOrUpdate() {  
  
        Session session = factory.openSession();  
        session.beginTransaction();  
  
        Student stu = (Student) session.get(Student.class, 9);  
  
        System.out.println(stu);  
  
        session.getTransaction().commit();  
        session.close();  
  
        // 处理脱管状态  
        stu.setName("9哥");  
  
        Session session2 = factory.openSession();  
        session2.beginTransaction();  
  
        session2.saveOrUpdate(stu);  
        System.out.println(stu);  
  
        session2.getTransaction().commit();  
        session2.close();  
  
        System.out.println(stu);  
    }  
  
    @Test  
    public void testSaveOrUpdate2() {  
  
        Student stu = new Student();  
        stu.setId(19); // 对象没有与session关联，并且OID有值，就被认为是脱管对象  
  
        Session session = factory.openSession();  
        session.beginTransaction();  
  
        session.saveOrUpdate(stu);  
  
        session.getTransaction().commit();  
        session.close();  
    }  
  
    @Test  
    public void testSaveOrUpdate3() {  
        Student stu = new Student();  
        stu.setId(9); // 对象没有与session关联，并且OID有值，就被认为是脱管对象  
        stu.setName("su");  
  
        Session session = factory.openSession();  
        session.beginTransaction();  
  
        session.get(Student.class, 9);  
  
        session.saveOrUpdate(stu);  
  
        session.getTransaction().commit();  
        session.close();  
    }  
  
    @Test  
    /* 
     * merge()方法： 
     * 1) 瞬时对象，执行类似save()的功能 
     * 2)脱管对象：如果在当前session缓存中不存在同OID的对象，就执行类似update()的功能。 
     * 否则，把传入的对象数据合并到缓存中的对象，返回缓存中的对象。  
     * 3) 经常用来替代update()和saveOrUpdate()方法。 
     */  
    public void testmerge() {  
        Student stu = new Student();  
        stu.setId(9); //对象没有与session关联，并且OID有值，就被认为是脱管对象  
        stu.setName("su");  
  
        Session session = factory.openSession();  
        session.beginTransaction();  
  
        session.get(Student.class, 9);  
  
        stu = (Student) session.merge(stu);  
  
        session.getTransaction().commit();  
        session.close();  
    } 
    
    //openSession()方法的测试，必须在事务提交后关闭session  
    @Test  
	public void openSessionDelete(){  
	    Session session = factory.openSession();  
	    Transaction tx = session.beginTransaction();  
	    Student stu = (Student) session.get(Student.class, 1);  
	    session.delete(stu);  
	    tx.commit();  
	    session.close();  
	}  
    
    //getCurrentSession()方法，不需要关闭session  
    @Test  
    public void getCurrentSessionDelete(){  
        Session session = factory.getCurrentSession();  
        Transaction tx = session.beginTransaction();  
        Student stu = (Student)session.get(Student.class,2);  
        session.delete(stu);  
        tx.commit();  
    }
  
}
