package com.zx.ioc.controller;

import com.zx.ioc.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;
}
