package com.liu.getOffBusReminderFinal.utils;

/**
 * @author liushuaibiao
 * @date 2023/4/28 10:36
 */
public class SnowflakeWork {
    private static final long MAX_WORK_ID = 31L;
    private Snowflake snowFlake;

    public SnowflakeWork() {
    }

    public synchronized long snowflakeId() {
        if (this.snowFlake == null) {
            this.snowFlake = IdUtil.getSnowflake();
        }

        return this.snowFlake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long datacenterId) {
        if (workerId <= 31L && workerId >= 0L) {
            if (datacenterId <= 31L && datacenterId >= 0L) {
                Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);
                return snowflake.nextId();
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 31L));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
    }
}

