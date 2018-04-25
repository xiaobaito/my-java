package com.zx.aop.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by weizx on 2018/4/22.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Transactional
    public void add() {
        userDao.add();
        System.out.println("insert into user is success");
//        int o= 1/0;
    }
}
