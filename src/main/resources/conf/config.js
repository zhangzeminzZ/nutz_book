var ioc = {
    dao:{
        type:"org.nutz.dao.impl.NutDao",
        args:[{refer:"dataSource"}],
        singleton:false
    },
    dataSource:{
        type:"com.alibaba.druid.pool.DruidDataSource",
        // type:"org.apache.tomcat.jdbc.pool.DataSource",
        fields : {
            driverClassName : "com.mysql.cj.jdbc.Driver",
            url : "jdbc:mysql://localhost:3306/nutz?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8&rewriteBatchedStatements=true",
            username : "root",
            password : "root",
            validationQuery : "select now() from dual",//数据库验证查询语句
            testOnReturn : true,
            testOnBorrow : true,
            testWhileIdle : true
        },
        events:{
            depose:"close"//监听对象被销毁时，关闭容器
        }
    }
};