package com.liu.getOffBusReminderFinal.entity;
import java.util.List;


public class Tips {
    private List<String> id;

    private String name;

    private List<String> district;

    private List<String> adcode;

    private List<String> location;

    private List<String> address;

    private List<String> typecode;

    private List<String> city;

    public void setId(List<String> id){
        this.id = id;
    }
    public List<String> getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setDistrict(List<String> district){
        this.district = district;
    }
    public List<String> getDistrict(){
        return this.district;
    }
    public void setAdcode(List<String> adcode){
        this.adcode = adcode;
    }
    public List<String> getAdcode(){
        return this.adcode;
    }
    public void setLocation(List<String> location){
        this.location = location;
    }
    public List<String> getLocation(){
        return this.location;
    }
    public void setAddress(List<String> address){
        this.address = address;
    }
    public List<String> getAddress(){
        return this.address;
    }
    public void setTypecode(List<String> typecode){
        this.typecode = typecode;
    }
    public List<String> getTypecode(){
        return this.typecode;
    }
    public void setCity(List<String> city){
        this.city = city;
    }
    public List<String> getCity(){
        return this.city;
    }
}
