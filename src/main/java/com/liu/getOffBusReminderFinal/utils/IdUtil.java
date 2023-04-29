package com.liu.getOffBusReminderFinal.utils;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.net.NetUtil;




/**
 * @author liushuaibiao
 * @date 2023/4/28 13:50
 */
public class IdUtil {
    public IdUtil() {
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }

    public static String objectId() {
        return ObjectId.next();
    }

    /** @deprecated */
    @Deprecated
    public static Snowflake createSnowflake(long workerId, long datacenterId) {
        return new Snowflake(workerId, datacenterId);
    }

    public static Snowflake getSnowflake(long workerId, long datacenterId) {
        return (Snowflake) Singleton.get(Snowflake.class, new Object[]{workerId, datacenterId});
    }

    public static Snowflake getSnowflake(long workerId) {
        return (Snowflake)Singleton.get(Snowflake.class, new Object[]{workerId});
    }

    public static Snowflake getSnowflake() {
        return (Snowflake)Singleton.get(Snowflake.class, new Object[0]);
    }

    public static long getDataCenterId(long maxDatacenterId) {
        long id = 1L;
        byte[] mac = NetUtil.getLocalHardwareAddress();
        if (null != mac) {
            id = (255L & (long)mac[mac.length - 2] | 65280L & (long)mac[mac.length - 1] << 8) >> 6;
            id %= maxDatacenterId + 1L;
        }

        return id;
    }

    public static long getWorkerId(long datacenterId, long maxWorkerId) {
        StringBuilder mpid = new StringBuilder();
        mpid.append(datacenterId);

        try {
            mpid.append(RuntimeUtil.getPid());
        } catch (UtilException var6) {
        }

        return (long)(mpid.toString().hashCode() & '\uffff') % (maxWorkerId + 1L);
    }

    public static String nanoId() {
        return NanoId.randomNanoId();
    }

    public static String nanoId(int size) {
        return NanoId.randomNanoId(size);
    }
}
