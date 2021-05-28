package com.example.a3socialnetworkclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

// ТЗ
// Реализовать приложение, которое подключается к публичному API соц. сети (предпочтительно VK/или другой)
// или к другому сервису, который может отдать данные для их последующего отображения.
// Выводить список друзей, с фотографиями и минимальным описанием.

public class MainActivity extends AppCompatActivity {
    TextView info;
    LinearLayout main;
    ArrayList<Friend> allFriend = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = (TextView) findViewById(R.id.info);
        main = (LinearLayout) findViewById(R.id.frgmCont);

        if (isOnline(this)){ // проверка подключения интернета
            Thread thread = new Thread(null, doBackgroundThreadProcessing,
                    "Background"); // Здесь трудоемкие задачи переносятся в дочерний поток.
            thread.start();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "Нет соединения с интернетом!", Toast.LENGTH_LONG).show();
        }
    }

    // Объект Runnable, который запускает метод для выполнения задач в фоновом режиме.
    private Runnable doBackgroundThreadProcessing = new Runnable() {
        public void run() { // Метод, который выполняет какие-то действия в фоновом режиме.
            HttpClient newrequest = new HttpClient();
            JsonParser  newjson = new JsonParser();
            allFriend = newjson.parse(newrequest.HTTPRequest());

            main.post(new Runnable() {
                public void run() {
                    for (int i = 0; i < allFriend.size(); i++) {
                        newRecord(i);
                    }
                }
            });
        }
    };

    public void newRecord (Integer i){
        LinearLayout layoutVER = new LinearLayout(this);
        layoutVER.setOrientation(LinearLayout.HORIZONTAL);
            ImageView image = new ImageView(this);
            new DownloadImageTask(image).execute(allFriend.get(i).getPhoto100());// Показать картинку
            image.setMinimumHeight(500);
            image.setMinimumWidth(500);
            layoutVER.addView(image);

            LinearLayout layoutHOR = new LinearLayout(this);
            layoutHOR.setOrientation(LinearLayout.VERTICAL);
                TextView first_last_name = new TextView(this);
                first_last_name.setText(allFriend.get(i).getFirst_name() + " " + allFriend.get(i).getLast_name());
                first_last_name.setTextSize(23);
                layoutHOR.addView(first_last_name);

                TextView city = new TextView((this));
                city.setText(allFriend.get(i).getCity_title());
                city.setTextSize(23);
                layoutHOR.addView(city);

            layoutVER.addView(layoutHOR);
        main.addView(layoutVER);
    }

    // Метод, который проверяет есть ли доступ в интернет
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                //Log.e('Ошибка передачи изображения', e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

