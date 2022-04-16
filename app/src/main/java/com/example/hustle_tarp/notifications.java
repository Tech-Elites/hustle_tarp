package com.example.hustle_tarp;

import java.util.HashMap;

public class notifications {
    String user_id;
    String message;

    public notifications() {
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getMessage() {
        return message;
    }

    public notifications(String user_id, String message) {
        this.user_id = user_id;
        this.message = message;
    }
    public HashMap<String,String> returnHashMap()
    {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("user_id",user_id);
        hashMap.put("message",message);
        return  hashMap;
    }
}
