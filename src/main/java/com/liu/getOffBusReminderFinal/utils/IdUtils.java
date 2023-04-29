package com.liu.getOffBusReminderFinal.utils;

import java.util.UUID;

/**
 * @author liushuaibiao
 * @date 2023/4/28 13:48
 */
public class IdUtils {
    private static IdWorker ID = new IdWorker(1L);
    private static final SnowflakeWork snowflakeWork = new SnowflakeWork();

    public IdUtils() {
    }

    public static long getNextId() {
        return ID.nextId();
    }

    public static long getNextId(int wordId) {
        ID = new IdWorker((long)wordId);
        return ID.nextId();
    }

    public static String snowflakeId() {
        return Long.toString(snowflakeWork.snowflakeId());
    }

    public static synchronized String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
