package com.rjq.security.consumer;

import com.rjq.security.delayed.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.DelayQueue;

/**
 * @version V1.0
 * @Description: 消费者
 * @author: renjingqian
 * @date: 2022/2/19 12:37
 */
@Slf4j
public class Consumer implements Runnable {

    public static DelayQueue<Message> queue = new DelayQueue<>();


    @Override
    public void run() {
        //循环调用
        while (true) {
            try {
                Iterator<Message> iterator = queue.iterator();
                //队列有元素就取出来
                while (iterator.hasNext()) {
                    Message message = queue.take();
                    log.info("key={},开始执行time={}", message.getKey(), message.getActiveTime());
                    //TODO 业务处理
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
