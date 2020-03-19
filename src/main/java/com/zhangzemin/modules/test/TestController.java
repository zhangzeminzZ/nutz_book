package com.zhangzemin.modules.test;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * @author zhangzemin
 * @date 2020/3/9 11:18
 */
@IocBean
@At("/test")
public class TestController {
    @Inject
    private TestService testService;

    @At("/test1")
    @Ok("json")
    public Object test(){
        return testService.test();
    }
}
