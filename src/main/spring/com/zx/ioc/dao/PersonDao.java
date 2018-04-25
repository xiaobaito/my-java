package com.zx.ioc.dao;

import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {

    private String flag = "1";

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "PersonDao{" +
                "flag='" + flag + '\'' +
                '}';
    }
}
