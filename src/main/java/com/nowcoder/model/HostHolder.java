package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * Created by wang on 2017/5/29.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();
    public User getUser(){
        return users.get();
    }
    public void setUser(User user){
        users.set(user);
    }
    public void clear(){
        users.remove();
    }
}
