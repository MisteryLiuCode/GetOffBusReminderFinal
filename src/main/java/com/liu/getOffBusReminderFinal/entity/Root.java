package com.liu.getOffBusReminderFinal.entity;
import java.util.List;
public class Root
{
    private List<Tips> tips;

    private String status;

    private String info;

    private String infocode;

    private String count;

    public void setTips(List<Tips> tips){
        this.tips = tips;
    }
    public List<Tips> getTips(){
        return this.tips;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setInfo(String info){
        this.info = info;
    }
    public String getInfo(){
        return this.info;
    }
    public void setInfocode(String infocode){
        this.infocode = infocode;
    }
    public String getInfocode(){
        return this.infocode;
    }
    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }
}

