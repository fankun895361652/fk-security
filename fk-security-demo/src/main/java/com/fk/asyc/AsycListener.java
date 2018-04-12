package com.fk.asyc;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author fankun
 * @date 2018/3/6 15:10
 */
//@Component
public class AsycListener implements ApplicationListener<ContextRefreshedEvent>{
    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHandler  handler;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

                new Thread(() ->{
                    while(true){
                        if(StringUtils.isNotBlank(mockQueue.getEndOrder())){
                            String orderNumber = mockQueue.getEndOrder();
                            handler.getMap().get(orderNumber).setResult("order is ok:"+orderNumber);
                            mockQueue.setEndOrder(null);
                            logger.info("返回订单处理结果:"+orderNumber);
                        }else{
                            try {
                                Thread.sleep(1000);
//                                logger.info("listener sleep:"+1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
    }
}
