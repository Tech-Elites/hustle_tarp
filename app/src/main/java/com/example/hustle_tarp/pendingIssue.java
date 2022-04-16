package com.example.hustle_tarp;

import java.util.HashMap;

public class pendingIssue {
    String name,desc,date_of_submission;

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDate_of_submission(String date_of_submission) {
        this.date_of_submission = date_of_submission;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate_of_submission() {
        return date_of_submission;
    }

    public pendingIssue(String name, String desc, String date_of_submission) {
        this.name = name;
        this.desc = desc;
        this.date_of_submission = date_of_submission;
    }
    public HashMap<String,String> getHashMap()
    {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("desc",desc);
        hashMap.put("date_of_submission",date_of_submission);
        return hashMap;
    }
}
