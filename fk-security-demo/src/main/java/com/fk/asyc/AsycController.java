package com.fk.asyc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @author fankun
 * @date 2018/3/6 14:36
 */

@RestController
public class AsycController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHandler  handler;
//
//    @RequestMapping("/order")
//    public DeferredResult<String> order(){
//        logger.info("主线程开始");
//        DeferredResult<String> def = new DeferredResult<>();
//        String orderNumber = RandomStringUtils.randomNumeric(8);
//        mockQueue.setBeginOrder(orderNumber);
//        handler.getMap().put(orderNumber,def);
//        logger.info("主线程jieshu");
//        return  def;
//    }





    @PostMapping(value = "/order")
    public Callable<String> order(){
        logger.info("主线程开始");
        Callable<String> call = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("call able begin");
                Thread.sleep(1000);
                logger.info("call able end");
                return "success";
            }
        };
        return call;
    }

}
