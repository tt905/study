package com.mo.study.model;

/**
 * Created by motw on 2017/2/6.
 */
public class PUser {

    @TestAnno(name = "小明", value = "看上面")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
