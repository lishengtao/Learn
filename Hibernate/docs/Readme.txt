1. 写得不错的关于持久化的Blog

   http://javacrazyer.iteye.com/category/114998

2. Oracle JDBC Driver 的位置

   E:\app\Administrator\product\11.2.0\dbhome_1\jdbc\lib
   
3. Oracle的thin驱动和oci驱动  

   	这是Oracle提供的两套Java访问Oracle数据库的方法  
	thin就是纯粹用Java完成访问数据库的所有方法，优点是不用安装客户端  
	
	oci就是使用Java来调用本机的Oracle客户端然后再访问数据库，优点是速度快但是需要安装和配置数据库
	OCI：要安装ORACLE客户端，移植性略差，理论上性能好些
	THIN：属于TYPE4，纯JAVA实现，移植性好，理论上性能略差些
	
	推荐：最好还是使用THIN   DRIVER吧，移植性好些，使用起来也相对规范些，问题也少。至于性能嘛，说实话，在8i上没感觉THIN   DRIVER比OCI慢，感觉还快些。不过没有实际准确测试过，也不敢下定论。为什么呢，调用本地方法也是有开销的，JDK性能越来越好，本地方法的性能 与调用的开销相抵一下就打了一点折扣。另外ORACLE   8i本身就是JAVA实现，对JAVA支持很好，用THIN   DRIVER不显得慢反而快也许有这方面的原因。
	
4. Oracle 没有Boolean数据类型的解决方案

        一直被Oracle中没有boolean类型困扰，网上有两种解决方案，一是用Number(1)，二是用Char(1)，各有所长，个人比较喜欢用Number方式解决，原因很简单，因为是从C语言开始的，这符合C语言的习惯。前几天有幸咨询到Oracle方面的顾问，他们提供的解决方案是用Char(1)实现boolean，但也有需要注意的地方，原话如下：  如果是特定boolean类型情况下，Char(1）是比Number(1)更好的选择，因为前者所用的存储空间会比后者少，但这二者在查询时存储空间的节省会提供查效率，但是要注意的是用Char(1)的时候不能让这个字段可以为空，必须有缺省，否则查询效率会降低
        
5. Hibernate中域的Boolean型字段到Oracle中varchar2(1)的映射

6. Hibernate提供了中间型的数据类型

   Java数据类型  <-> 中间数据类型 <-> 数据库中的数据类型
   
   Dialect中应该有线索
   
7. oracle 查看连接数

   select username,count(username) from v$session where username is not null group by username 

