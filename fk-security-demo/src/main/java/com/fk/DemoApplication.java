package com.fk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fankun
 * @date 2018/3/2 12:22
 */
@SpringBootApplication
//@RestController
//@ComponentScan("fk.security")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }



//    @PostMapping("/hello")
//    public String hello() {
//        return "hello security";
//    }
}
