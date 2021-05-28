package com.example.a3socialnetworkclient;

public class Friend {
    private String first_name;
    private String last_name;
    private String photo100;
    private String city_title;

    public Friend (String fname, String lname, String photo, String city){
        first_name = fname;
        last_name = lname;
        photo100 = photo;
        city_title = city;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhoto100() {
        return photo100;
    }

    public String getCity_title() {
        return city_title;
    }
}
