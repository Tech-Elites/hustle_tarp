package com.example.hustle_tarp;

import java.util.HashMap;

public class solSubmitted {
    private String userId;
    private String userName;
    private String link;
    private String comments;
    private String issueId;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    private String date;

    public solSubmitted() {

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getLink() {
        return link;
    }

    public String getComments() {
        return comments;
    }

    public solSubmitted(String userId, String userName, String link, String comments,String issueId,String date) {
        this.userId = userId;
        this.userName = userName;
        this.link = link;
        this.comments = comments;
        this.issueId=issueId;
        this.date=date;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getIssueId() {
        return issueId;
    }

    public HashMap getHashMap()
    {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("userId",userId);
        hashMap.put("userName",userName);
        hashMap.put("link",link);
        hashMap.put("comments",comments);
        hashMap.put("issueId",issueId);
        hashMap.put("date",date);
        return hashMap;

    }
}
