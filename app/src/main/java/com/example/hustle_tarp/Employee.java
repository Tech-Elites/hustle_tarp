package com.example.hustle_tarp;

import java.util.HashMap;

public class Employee {
    String name,status,dob;

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getDob() {
        return dob;
    }

    public Employee() {
    }

    public HashMap<String,String> getHashMap(){
        HashMap <String,String> hc= new HashMap<>();
        hc.put("name",name);
        hc.put("status",status);
        hc.put("dob",dob);
        return hc;
    }
    public Employee(String name, String status, String dob) {
        this.name = name;
        this.status = status;
        this.dob = dob;
    }
}
