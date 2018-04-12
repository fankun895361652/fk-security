package com.fk.asyc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author fankun
 * @date 2018/3/6 14:53
 */
@Component
public class MockQueue {
    private String beginOrder;
    private String endOrder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getBeginOrder() {
        return beginOrder;
    }

    public void setBeginOrder(String beginOrder) {
        new Thread(()->{
            logger.info("收到请求:"+beginOrder);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.endOrder = beginOrder;
            logger.info("处理完请求:"+beginOrder);
        }).start();
    }

    public String getEndOrder() {
        return endOrder;
    }

    public void setEndOrder(String endOrder) {
        this.endOrder = endOrder;
    }
}
