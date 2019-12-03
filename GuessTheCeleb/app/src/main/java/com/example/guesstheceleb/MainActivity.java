package com.example.guesstheceleb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> celebURLs = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    String[] anwers = new String[4];
    int chosenCeleb = 0;
    int locationOfCorrectAnswer = 0;
    ImageView imageView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    public class ImageDownloader extends  AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return  myBitmap;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.celebImage);

        button0 = findViewById(R.id.button1);
        button1 = findViewById(R.id.button2);
        button2 = findViewById(R.id.button3);
        button3 = findViewById(R.id.button4);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("http://www.posh24.se/kandisar").get();

            String[] splitResult = result.split("<div class=\"listedArticles\">");

            Pattern p = Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebURLs.add(m.group(1));
            }

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebNames.add(m.group(1));
            }

            newQuestion();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chooseCeleb(View view) {
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Toast.makeText(getApplicationContext(), "Dobrze!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Źle! Prawidłowa odpowiedź to: " + celebNames.get(chosenCeleb), Toast.LENGTH_SHORT).show();
        }
        newQuestion();

    }

    public void newQuestion() {
        try {
            Random rand = new Random();
            chosenCeleb = rand.nextInt(celebURLs.size());

            ImageDownloader imageTask = new ImageDownloader();
            Bitmap celebImage = imageTask.execute(celebURLs.get(chosenCeleb)).get();

            imageView.setImageBitmap(celebImage);

            locationOfCorrectAnswer = rand.nextInt(4);

            int incorrectAnswerLocation;

            for (int i = 0; i < 4; i++) {
                if (i == locationOfCorrectAnswer) {
                    anwers[i] = celebNames.get(chosenCeleb);
                } else {
                    incorrectAnswerLocation = rand.nextInt(celebURLs.size());

                    while (incorrectAnswerLocation == locationOfCorrectAnswer) {
                        incorrectAnswerLocation = rand.nextInt(celebURLs.size());
                    }

                    anwers[i] = celebNames.get(incorrectAnswerLocation);
                }
            }

            button0.setText(anwers[0]);
            button1.setText(anwers[1]);
            button2.setText(anwers[2]);
            button3.setText(anwers[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
