package com.zhangzemin.main;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.*;

/**
 * @author zhangzemin
 * @date 2020/3/9 11:04
 */
@IocBean
@Modules(scanPackage = true,packages = {"com.zhangzemin"})
@Encoding(output = "UTF-8",input = "UTF-8")
@ChainBy(args = {"conf/config.js"})
@IocBy(args={"*js", "conf/",
        "*anno", "com.zhangzemin",
        "*async",
        "*tx"
})
public class MainModule {

}
