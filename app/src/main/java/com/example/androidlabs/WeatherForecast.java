package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WeatherForecast extends AppCompatActivity
{
    private ProgressBar weatherBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        weatherBar = findViewById(R.id.indeterminateBar);
        weatherBar.setVisibility(View.VISIBLE);
        String iconName;
        ForecastQuery forecast = new ForecastQuery();
        forecast.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric", "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389", "http://openweathermap.org/img/w/");  //Type 1
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String>
    {
        String currentTemperature;
        String min;
        String max;
        double UV;
        String iconName;
        Bitmap image = null;

        //Type 1
        @Override
        protected String doInBackground(String... debates)
        {
            try
            {
                //create a URL object of what server to contact:
                URL url = new URL(debates[0]);
                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //wait for data:
                InputStream response = urlConnection.getInputStream();
                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");
                //From part 3, slide 20
                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT
                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("temperature"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                            currentTemperature = xpp.getAttributeValue(null,    "value");
                            publishProgress(20);
                            min = xpp.getAttributeValue(null, "min");
                            publishProgress(40);
                            max = xpp.getAttributeValue(null, "max");
                            publishProgress(60);
                        }
                        else if(xpp.getName().equals("weather"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                            iconName = xpp.getAttributeValue(null,    "icon");
                            publishProgress(70);
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }
            }
            catch (Exception ignored)
            {

            }

            try
            {
                URL url = new URL(debates[1]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, StandardCharsets.UTF_8), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line).append("\n");
                }
                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);
                UV = jObject.getDouble("value");
                publishProgress(80);
            }
            catch (Exception ignored)
            {

            }

            try
            {
                URL url = new URL(debates[2] + iconName + ".png");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200)
                {
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                }

                FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
                FileInputStream fis = null;
                try {    fis = openFileInput(String.valueOf(image));   }
                catch (FileNotFoundException e) {    e.printStackTrace();  }
                Bitmap bm = BitmapFactory.decodeStream(fis);
            } catch (Exception ignored)
            {

            }

            return "Done";
        }

        //Type 2
        public void onProgressUpdate(Integer...value)
        {
            weatherBar.setProgress(value[0]);
            Log.i("Progress", "Progress is :" + value[0] + "%");
        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            TextView currentTemperatureET = findViewById(R.id.current_temperature);
            TextView minET = findViewById(R.id.min_temperature);
            TextView maxET = findViewById(R.id.max_temperature);
            TextView UVET = findViewById(R.id.UV_Rating);
            ProgressBar weatherBar = findViewById(R.id.indeterminateBar);

            currentTemperatureET.setText(currentTemperature);
            minET.setText(min);
            maxET.setText(max);
            maxET.setText(max);
            UVET.setText(String.valueOf(UV));
            weatherBar.setVisibility(View.INVISIBLE);
            Log.i("HTTP", fromDoInBackground);
        }
    }
}
