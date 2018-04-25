package com.zx.aop.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by weizx on 2018/4/22.
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add() {

        String sql = "INSERT INTO USER (NAME,age) VALUES (?,?)";
        String name = UUID.randomUUID().toString().substring(0,5);

        jdbcTemplate.update(sql,name,18);

    }

}
