package com.hsbc.unattendedtest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RequestbinApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RequestbinApplication.class).run(args);
    }

}
