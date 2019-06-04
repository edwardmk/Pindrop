package com.bowling.edward.bowling;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.bowling.edward.bowling.AlleyLocator.LocalAlleys;
import com.bowling.edward.bowling.Friends.FindFriends;
import com.bowling.edward.bowling.LineGraphStats.FinalScoreStatistics;

public class HomePage extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    ImageButton createGame, manageFriends, nearbyAlleys, viewStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        createGame = findViewById(R.id.createGameButton);
        manageFriends = findViewById(R.id.manageFriendsButton);
        nearbyAlleys = findViewById(R.id.nearbyAlleysButton);
        viewStats = findViewById(R.id.viewStatsButton);

        createGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                goToGame();
            }

        });
        nearbyAlleys.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                goToAlleys();
            }

        });
        viewStats.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                goToStats();
            }

        });
        manageFriends.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                goToFriends();
            }

        });

        NavigationView mNavigationView = findViewById(R.id.nav_view);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_game) {
            Intent i = new Intent(this, Game.class);
            startActivity(i);
        }
        else if (id == R.id.nav_home) {
            Intent i = new Intent(this, HomePage.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_statistics){
            Intent i = new Intent(this, FinalScoreStatistics.class);
            startActivity(i);
        }
        else if (id == R.id.nav_view_friends) {
            Intent i = new Intent(this, FindFriends.class);
            startActivity(i);
        }
        else if (id == R.id.nav_find_alley) {
            Intent i = new Intent(this, LocalAlleys.class);
            startActivity(i);
        }
        else if (id == R.id.nav_log_out) {
            Intent i = new Intent(this, LoadingScreen.class);
            startActivity(i);
            finish();
        }
        return false;
    }
    public void goToGame(){
        Intent i = new Intent(HomePage.this, Game.class);
        startActivity(i);
    }
    public void goToStats(){
        Intent i = new Intent(HomePage.this, AllStats.class);
        startActivity(i);
    }
    public void goToAlleys(){
        Intent i = new Intent(HomePage.this, LocalAlleys.class);
        startActivity(i);
    }
    public void goToFriends(){
        Intent i = new Intent(HomePage.this, FindFriends.class);
        startActivity(i);
    }


}
