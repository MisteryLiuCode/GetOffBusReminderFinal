package com.liu.getOffBusReminderFinal.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liushuaibiao
 * @date 2023/4/14 18:47
 */
public enum LocationEnum {
    TYPE_1("work", "上班位置信息"),
    TYPE_2("home", "家位置信息"),;

    private String code;
    private String mark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    LocationEnum(String code, String mark) {
        this.code = code;
        this.mark = mark;
    }

    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = new ArrayList();
        LocationEnum[] var2 = values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            LocationEnum be = var2[var4];
            Map<String, Object> map = new HashMap();
            map.put("code", be.getCode());
            map.put("mark", be.getMark());
            list.add(map);
        }

        return list;
    }
}
