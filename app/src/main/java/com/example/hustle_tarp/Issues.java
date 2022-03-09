package com.example.hustle_tarp;

import java.util.HashMap;

public class Issues {
    private String title,description,credits,link,due_date;

    public Issues() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCredits() {
        return credits;
    }

    public String getLink() {
        return link;
    }

    public String getDue_date() {
        return due_date;
    }

    public Issues(String title, String description, String credits, String link, String due_date) {
        this.title = title;
        this.description = description;
        this.credits = credits;
        this.link = link;
        this.due_date = due_date;
    }
    public HashMap getHashMap(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("title",title);
        hashMap.put("description",description);
        hashMap.put("credits",credits);
        hashMap.put("link",link);
        hashMap.put("due_date",due_date);
        return hashMap;
    }
}
