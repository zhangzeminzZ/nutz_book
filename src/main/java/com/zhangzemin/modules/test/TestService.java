package com.zhangzemin.modules.test;

import com.zhangzemin.bean.Person;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * @author zhangzemin
 * @date 2020/3/9 11:03
 */
@IocBean
public class TestService {

    @Inject
    private Dao dao;

    public Object test(){
        List<Person> list = dao.query(Person.class, null);
        for(Person p : list){
            System.out.println(p);
        }
        return list;
    }
}
