package com.zx.ioc.service;

import com.zx.ioc.dao.PersonDao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PersonService {

    /**
     * 系统中有多个相同类型的组件时，@Autowired 会根据属性名称去获取组件，
     * 如果配置了@Qualifier就会直接使用@Qualifier去获取相应的组件
     */
//    @Qualifier(value = "personDao")
//    @Autowired
//    @Resource(name = "personDao")
       @Inject
    private PersonDao personDao;

    public void print(){
        System.out.println("personService ... print .... " + personDao);
    }

    @Override
    public String toString() {
        return "PersonService{" +
                "personDao=" + personDao +
                '}';
    }
}
