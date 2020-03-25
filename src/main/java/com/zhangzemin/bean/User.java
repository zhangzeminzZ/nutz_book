package com.zhangzemin.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
 * @author zhangzemin
 * @date 2020/3/25 9:43
 */
@Table("t_user")
public class User {
    @Name //注意,字符串主键用@Name,与属性名称无关!!!
    @Prev(els=@EL("uuid(32)")) // 可以是 uuid() uuid(32)
    private String id;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String salt;
    @Column
    private Date createTime;
    @Column
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
