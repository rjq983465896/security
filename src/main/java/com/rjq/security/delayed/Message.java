package com.rjq.security.delayed;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @Description: 消息体
 * @author: renjingqian
 * @date: 2022/2/19 12:33
 */
@Data
public class Message implements Delayed {

    /**
     * 到期时间 单位：ms
     */
    private long activeTime;

    private String key;

    public Message(long activeTime,String key) {
        // 将传入的时间转换为超时的时刻
        this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime, TimeUnit.MILLISECONDS)
                + System.nanoTime();
        this.key=key;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        // 剩余时间= 到期时间-当前系统时间，系统一般是纳秒级的，所以这里做一次转换
        long d = unit.convert(activeTime-System.nanoTime(), TimeUnit.NANOSECONDS);
        return d;
    }

    @Override
    public int compareTo(Delayed o) {
        // 剩余时间-当前传入的时间= 实际剩余时间（单位纳秒）
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        // 根据剩余时间判断等于0 返回1 不等于0
        // 有可能大于0 有可能小于0  大于0返回1  小于返回-1
        return (d == 0) ? 0 : ((d > 0) ? 1 : -1);
    }
}
