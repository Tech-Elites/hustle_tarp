package com.example.hustle_tarp;

import java.util.HashMap;

public class InfoEmployeeClass  implements Comparable<InfoEmployeeClass> {
    int points;
    String name;

    HashMap<String, Integer> tags, dates;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, Integer> tags) {
        this.tags = tags;
    }

    public HashMap<String, Integer> getDates() {
        return dates;
    }

    public void setDates(HashMap<String, Integer> dates) {
        this.dates = dates;
    }

    public InfoEmployeeClass(int points, String name, HashMap<String, Integer> tags, HashMap<String, Integer> dates) {
        this.points = points;
        this.name = name;
        this.tags = tags;
        this.dates = dates;
    }

    public InfoEmployeeClass() {
    }

    public HashMap<String,Object> getHashMap(){
        HashMap <String,Object> hc= new HashMap<>();
        hc.put("name",name);
        hc.put("points",points);
        hc.put("tags",tags);
        hc.put("dates", dates);
        return hc;
    }

    @Override
    public int compareTo(InfoEmployeeClass comparestu)
    {
        int compareage
                = ((InfoEmployeeClass)comparestu).getPoints();

        //  For Ascending order
        return this.points - compareage;

        // For Descending order do like this
        // return compareage-this.studentage;
    }
}
