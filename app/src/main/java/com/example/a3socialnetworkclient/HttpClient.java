package com.example.a3socialnetworkclient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

// РАБОТА С API
// апи вк https://vk.com/dev/users.get
// ID приложения	********** создать приложение во вкладке разработчикам
//access_token=94cd31ecb0316b7c111ef3f2123c79d1b9a539c910044011dcad24cb65554032c770ada97972fa32a0a61&expires_in=0&user_id=121182601&email=Artema_m@tut.by
//access_token=*******************************************************твой токен как получить читать в тырнете

public class HttpClient {
    String access_token = "94cd31ecb0316b7c111ef3f2123c79d1b9a539c910044011dcad24cb65554032c770ada97972fa32a0a61";

    public String HTTPRequest() {
        String requestUrl2 = "https://api.vk.com/method/friends.get?v=5.52&fields=nickname,%20city,%20photo_100&access_token=" + access_token; // поля какие выводить - читать в вк инсрукции
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(requestUrl2);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
