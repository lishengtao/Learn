package com.shengtao.domain;

import java.util.Date;  

/** 
 * 学生实体类 --> 按JavaBean的形式定义 
 *  
 */  
public class Student {  
    private int id;  //OID 对象标识符  
    private String name;  
    private int age;  
    private boolean gender;  
    private Date birthday;  
    private double score;  
      
    public int getId() {  
        return id;  
    }  
    public void setId(int id) {  
        this.id = id;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public int getAge() {  
        return age;  
    }  
    public void setAge(int age) {  
        this.age = age;  
    }  
    public boolean isGender() {  
        return gender;  
    }  
    public void setGender(boolean gender) {  
        this.gender = gender;  
    }  
    public Date getBirthday() {  
        return birthday;  
    }  
    public void setBirthday(Date birthday) {  
        this.birthday = birthday;  
    }  
    public double getScore() {  
        return score;  
    }  
    public void setScore(double score) {  
        this.score = score;  
    }  
      
    @Override  
    public String toString(){  
        return "id=" + id + ",name=" + name + ",gender=" + gender + ",age=" + age  
        + ",birthday=" + this.birthday + ",score=" + score;  
    }  
      
}
