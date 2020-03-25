package com.zhangzemin.main;

import com.zhangzemin.bean.User;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import java.util.Date;

/**
 * @author zhangzemin
 * @date 2020/3/25 9:53
 */
public class MainSetup implements Setup {

    @Override
    public void init(NutConfig nutConfig) {
        Ioc ioc = nutConfig.getIoc();
        Dao dao = ioc.get(Dao.class);
        Daos.createTablesInPackage(dao,"com.zhangzemin",false);


        // 初始化默认根用户
        if(dao.count(User.class ) == 0){
            User user = new User();
            user.setName("admin");
            user.setPassword("123456");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            dao.insert(user);
        }
    }

    @Override
    public void destroy(NutConfig nutConfig) {
        // webapp销毁之前执行的逻辑
        // 这个时候依然可以从nutConfig取出ioc, 然后取出需要的ioc 对象进行操作
    }
}
