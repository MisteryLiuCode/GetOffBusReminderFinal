package com.liu.getOffBusReminderFinal.utils;

/**
 * @author liushuaibiao
 * @date 2023/4/28 13:49
 */
public class IdWorker {
    private static final long TWEPOCH = 1482831049587L;
    private static final long WORKERIDBITS = 5L;
    private static final long DATACENTERIDBITS = 5L;
    private static final long MAXWORKERID = 31L;
    private static final long SEQUENCEBITS = 12L;
    private static final long WORKERIDSHIFT = 12L;
    private static final long TIMESTAMPLEFTSHIFT = 22L;
    private static final long SEQUENCEMASK = 4095L;
    private static long lastTimestamp = -1L;
    private long sequence = 0L;
    private final long workerId;

    public IdWorker(final long workerId) {
        if (workerId <= 31L && workerId >= 0L) {
            this.workerId = workerId;
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1L & 4095L;
            if (this.sequence == 0L) {
                timestamp = this.tilNextMillis(lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }

        if (timestamp < lastTimestamp) {
            try {
                throw new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            } catch (Exception var5) {
            }
        }

        lastTimestamp = timestamp;
        long nextId = timestamp - 1482831049587L << 22 | this.workerId << 12 | this.sequence;
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
