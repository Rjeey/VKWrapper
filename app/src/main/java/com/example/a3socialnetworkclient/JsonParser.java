package com.example.a3socialnetworkclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public ArrayList<Friend> parse(String resultJson) {
        try {
            ArrayList<Friend> allFriend = new ArrayList<>();

            JSONObject jObject = new JSONObject(resultJson).getJSONObject("response");
            JSONArray jArray = (JSONArray) jObject.get("items");

            String fname, lname, photo, city;
            fname = lname = photo = city = null;
            for (int i=0; i < jArray.length(); i++) {
                fname = lname = photo = city = null;
                JSONObject oneObject = jArray.getJSONObject(i);
                fname = oneObject.getString("first_name");
                lname = oneObject.getString("last_name");

                photo = oneObject.getString("photo_100");
                if (oneObject.has("city"))
                    city = oneObject.getJSONObject("city").getString("title");
                else
                    city = "Неизвестно";
                allFriend.add(new Friend(fname, lname, photo, city));
            }
            return allFriend;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
