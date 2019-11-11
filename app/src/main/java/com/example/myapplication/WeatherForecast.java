package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.Boolean.FALSE;
import static java.lang.System.in;

public class WeatherForecast extends AppCompatActivity {
    ImageView imageView;
    TextView current_temp;
    TextView min_temp;
    TextView max_temp;
    TextView wind_speed;

    private final String ACTIVITY_NAME = "WeatherForecastActivity";
    ProgressBar progressBar;
    String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        current_temp = findViewById(R.id.currentTemp);
        min_temp = findViewById(R.id.minTemp);
        max_temp = findViewById(R.id.maxTemp);
        imageView = findViewById(R.id.currWeather);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery f = new ForecastQuery();
        f.execute();

    }






    private class ForecastQuery extends AsyncTask<String, Integer, String>{
        private String windSpeed;
        private String currentTemp;
        String minTemp;
        String maxTemp;
        Bitmap currWeather;

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                InputStream in = conn.getInputStream();
                try{
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);

                    int type;
                    //While you're not at the end of the document:
                    while((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
                    {
                        //Are you currently at a Start Tag?
                        if(parser.getEventType() == XmlPullParser.START_TAG)
                        {
                            if(parser.getName().equals("temperature") )
                            {
                                currentTemp = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemp = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            }
                            else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";

                                Log.i(ACTIVITY_NAME,"Looking for file: " + fileName);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);

                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME,"Found the file locally");
                                    currWeather = BitmapFactory.decodeStream(fis);
                                }
                                else {
                                    String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                    currWeather = getImage(new URL(iconUrl));

                                    FileOutputStream outputStream = openFileOutput( fileName, Context.MODE_PRIVATE);
                                    currWeather.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i(ACTIVITY_NAME,"Downloaded the file from the Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }
                            else if (parser.getName().equals("wind")) {
                                parser.nextTag();
                                if(parser.getName().equals("speed") )
                                {
                                    windSpeed = parser.getAttributeValue(null, "value");
                                }
                            }
                        }
                        // Go to the next XML event
                        parser.next();
                    }
                } finally {
                    in.close();
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return "";
        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String a) {
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(currWeather);
            current_temp.setText(currentTemp + "C\u00b0");
            min_temp.setText(minTemp + "C\u00b0");
            max_temp.setText(maxTemp + "C\u00b0");
            //wind_speed.setText(windSpeed + "km/h");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}
