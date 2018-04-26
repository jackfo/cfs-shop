package com.whpu.web.context;

import com.whpu.web.pojo.SessionUser;

public class UserContext {
    private static ThreadLocal<SessionUser> userHolder = new ThreadLocal<SessionUser>();

    public static void setUser(SessionUser user) {
        userHolder.set(user);
    }

    public static SessionUser getUser() {
        return userHolder.get();
    }
}
