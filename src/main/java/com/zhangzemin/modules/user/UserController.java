package com.zhangzemin.modules.user;

import com.zhangzemin.bean.User;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author zhangzemin
 * @date 2020/3/25 9:58
 */
@IocBean
@At("/user")
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")
@Filters(@By(type= CheckSession.class, args={"me", "/"}))
public class UserController {
    @Inject
    protected Dao dao; // 就这么注入了,有@IocBean它才会生效

    @At
    public int count() {
        return dao.count(User.class);
    }

    /**
     * 登录方法
     * @param name 用户名
     * @param password 密码
     * @param session
     * @return
     */
    @At
    @Filters()
    public Object login(@Param("username") String name, @Param("password") String password, HttpSession session) {
        User user = dao.fetch(User.class, Cnd.where("name", "=", name).and("password", "=", password));
        if (user == null) {
            return false;
        } else {
            session.setAttribute("me", user.getId());
            return true;
        }
    }

    @At
    @Ok(">>:/")
    public void logout(HttpSession session){
        session.invalidate();
    }

    /**
     * 校验方法
     * @param user 用户对象
     * @param create 增加还是更新
     * @return
     */
    protected String checkUser(User user,boolean create){

        if (user == null) {
            return "空对象";
        }
        if (create) {
            if (Strings.isBlank(user.getName()) || Strings.isBlank(user.getPassword()))
                return "用户名/密码不能为空";
        } else {
            if (Strings.isBlank(user.getPassword()))
                return "密码不能为空";
        }
        String passwd = user.getPassword().trim();
        if (6 > passwd.length() || passwd.length() > 12) {
            return "密码长度错误";
        }
        user.setPassword(passwd);
        if (create) {
            int count = dao.count(User.class, Cnd.where("name", "=", user.getName()));
            if (count != 0) {
                return "用户名已经存在";
            }
        }

        if (user.getName() != null)
            user.setName(user.getName().trim());
        return null;
    }


    /**
     * 添加用户
     * @param user 用户对象
     * @return
     */
    @At
    public Object add(@Param("..")User user){
        NutMap map = new NutMap();
        String msg = this.checkUser(user,true);
        if(null != msg){
            return map.setv("ok",false).setv("msg",msg);
        }
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user = dao.insert(user);
        return map.setv("ok",true).setv("data",user);
    }

    /**
     * 更新用户
     * @param user 用户对象
     * @return
     */
    @At
    public Object update(@Param("..")User user){
        NutMap re = new NutMap();
        String msg = checkUser(user, false);
        if (msg != null){
            return re.setv("ok", false).setv("msg", msg);
        }
        user.setName(null);// 不允许更新用户名
        user.setCreateTime(null);//也不允许更新创建时间
        user.setUpdateTime(new Date());// 设置正确的更新时间
        dao.updateIgnoreNull(user);// 真正更新的其实只有password和salts
        return re.setv("ok", true);
    }


    /**
     * 删除用户
     * @param id 用户id
     * @param me session中的用户id
     * @return
     */
    @At
    public Object delete(@Param("id")String id, @Attr("me")String me) {
        if (me.equals(id)) {
            return new NutMap().setv("ok", false).setv("msg", "不能删除当前用户!!");
        }
        dao.delete(User.class, id); // 再严谨一些的话,需要判断是否为>0
        return new NutMap().setv("ok", true);
    }

    /**
     * 查询用户
     * @param name 用户名
     * @param pager 分页
     * @return
     */
    @At
    public Object query(@Param("name")String name, @Param("..")Pager pager){
        Cnd cnd = Strings.isBlank(name)? null : Cnd.where("name","like","%"+name+"%");
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(User.class,cnd,pager));
        pager.setRecordCount(dao.count(User.class,cnd));
        qr.setPager(pager);
        return qr;
    }

    @At("/")
    @Ok("jsp:jsp.user.list")// 真实路径是 /WEB-INF/jsp/user/list.jsp
    public void index(){

    }
}
