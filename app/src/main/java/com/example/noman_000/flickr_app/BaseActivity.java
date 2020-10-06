package com.example.noman_000.flickr_app;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

class BaseActivity extends AppCompatActivity{
    static final String FLICK_QUERY = "flickr_query";
    static final String PHOTO_TRANSFER = "transfer_photo";
    void activateToolBar(boolean enableHoMe){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null){
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if(toolbar != null){
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(enableHoMe);
            }
        }
    }
}
