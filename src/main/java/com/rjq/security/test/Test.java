package com.rjq.security.test;

import com.rjq.security.consumer.Consumer;
import com.rjq.security.delayed.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version V1.0
 * @Description: 测试
 * @author: renjingqian
 * @date: 2022/2/19 12:40
 */
@Slf4j
public class Test {

    public static ConcurrentHashMap<String, Message> queueMap=new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Message> queue=Consumer.queue;
        //创建1size的线程
        ExecutorService exec= Executors.newFixedThreadPool(1);
        exec.execute(new Consumer());
        //有消息就往队列里面加
        Message message1=new Message(5000,"5s");
        Message message2=new Message(1000*15,"15s");
        Message message3=new Message(1000*20,"20s");
        //全局map维护key和队列的关系
        queueMap.put("5s",message1);
        queueMap.put("15s",message2);
        queueMap.put("20s",message3);
        queue.offer(message1);
        queue.offer(message2);
        queue.offer(message3);
        Thread.sleep(1000*16);
        log.info("移除队列元素");
        //移除队列的元素
        Message message = queueMap.get("20s");
        queue.remove(message);

    }
}
