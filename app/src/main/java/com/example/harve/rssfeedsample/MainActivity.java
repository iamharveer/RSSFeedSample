package com.example.harve.rssfeedsample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", "");
        setContentView(R.layout.activity_main);
        IotdHandler handler = new IotdHandler();
        handler.processFeed();
        resetDisplay(handler.getTitle(), handler.getDate(), handler.getImage(), handler.getDescription());
    }

    @Override
    protected void onStart()
    {
        Log.d("OnStart","..");
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        Log.d("OnResume", "...");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        Log.d("OnPause", "...");
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        Log.d("onStop", "...");
        super.onStop();
    }

    public void resetDisplay(String title, String date, Bitmap image, StringBuffer description)
    {
        TextView titleView = (TextView)findViewById(R.id.ImageHeader);
        TextView dateView = (TextView)findViewById(R.id.DateTime);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        TextView descriptionView = (TextView)findViewById(R.id.ImageDescription);

        titleView.setText(title);
        dateView.setText(date);
        imageView.setImageBitmap(image);
        descriptionView.setText(description);

    }
}
