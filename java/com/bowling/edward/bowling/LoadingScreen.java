package com.bowling.edward.bowling;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class LoadingScreen extends AppCompatActivity {

    private ImageView loadingImage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(progressBar.VISIBLE);


//        //Set GIFImageView resource
//        try{
//            //InputStream inputStream = getAssets().open("loadingscreen.gif");
//            InputStream inputStream = getAssets().open("loadingscreen.gif");
//            byte[] bytes = IOUtils.toByteArray(inputStream);
//            loadingImage.setBytes(bytes);
//            loadingImage.startAnimation();
//        }
//        catch (IOException ex)
//        {
//
//        }

        //Wait for 3 seconds and start Activity Main
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadingScreen.this.startActivity(new Intent(LoadingScreen.this,Login.class));
                LoadingScreen.this.finish();
            }
        },2000);
    }
}