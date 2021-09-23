package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class HelloController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private Tracer tracer;

    @GetMapping("/test")
    public String index() {

        userDao.getUserDetails();
        return "Ok, null? -> "+(tracer==null);
    }

    @GetMapping("/hello")
    public String hello() {

        Span span = tracer.activeSpan();

        span.log("This is a log, I am going to try to log something important");
        span.setTag("Number of users", userDao.getUserDetails().size());

        return "Hello there";
    }

}
