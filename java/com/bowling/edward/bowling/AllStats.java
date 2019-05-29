package com.bowling.edward.bowling;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bowling.edward.bowling.AlleyLocator.LocalAlleys;
import com.bowling.edward.bowling.Friends.FindFriends;
import com.bowling.edward.bowling.LineGraphStats.AverageFrameStatistics;
import com.bowling.edward.bowling.LineGraphStats.AverageStrikesStatistics;
import com.bowling.edward.bowling.LineGraphStats.FinalScoreStatistics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class AllStats extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private DatabaseReference myRef;
    TextView avgFrame, avgClearFrames, avgScore, avgStrikes, gmsPlayed;
    Button reset, goToGraphs;
    String user_id;
    int gameCount;
    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth mAuth;
    public List<Integer> averageFrameList = new ArrayList<>();
    public List<Integer> averageClearCountList = new ArrayList<>();
    public List<Integer> averageScoreList = new ArrayList<>();
    public List<Integer> averageStrikesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stats);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        myRef = mFirebaseDatabase.getReference();
        avgFrame = findViewById(R.id.averageFrame);
        avgClearFrames = findViewById(R.id.averageFrameClear);
        avgScore = findViewById(R.id.averageGameScore);
        avgStrikes = findViewById(R.id.averageStrikes);
        gmsPlayed = findViewById(R.id.gamesPlayed);
        reset = findViewById(R.id.resetStats);
        goToGraphs = findViewById(R.id.takeToChart);

        NavigationView mNavigationView = findViewById(R.id.nav_view);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("gamecount");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String gameCountString = dataSnapshot.getValue().toString();
                    gameCount = Integer.parseInt(gameCountString);
                    GamesPlayed(gameCountString);
                    AverageFrames(gameCount);
                    AverageClearedFrames(gameCount);
                    AverageScore(gameCount);
                    AverageStrikes(gameCount);
                }
                else{
                    setStatsToZero();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStatistics();
            }
        });
        goToGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGraphs(gameCount);
            }
        });
    }
    private void GamesPlayed(String gameCount) {
        gmsPlayed.setText(gameCount);

    }

    private void AverageFrames(final int gameCount){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("games");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    int average1 = ds.child("averageFrame").getValue(int.class);
                    averageFrameList.add(average1);
                    int sum = 0;
                    for(int i = 0; i < averageFrameList.size(); i++){
                        sum = sum + averageFrameList.get(i);
                    }
                    int averageFrameFinal;
                    if(gameCount > 0) {
                        averageFrameFinal = sum / gameCount;
                    }
                    else{
                        averageFrameFinal = sum;
                    }
                    String averageFrameFinalString = valueOf(averageFrameFinal);

                    avgFrame.setText(averageFrameFinalString);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }


    private void AverageClearedFrames(final int gameCount) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("games");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    int clearCount = ds.child("clearCount").getValue(int.class);
                    averageClearCountList.add(clearCount);
                    int sum = 0;
                    for(int i = 0; i < averageClearCountList.size(); i++){
                        sum = sum + averageClearCountList.get(i);
                    }
                    int averageclearCount = sum/gameCount;

                    String averageclearCountFinalString = valueOf(averageclearCount);

                    avgClearFrames.setText(averageclearCountFinalString);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }


    private void AverageScore(final int gameCount) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("games");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    int finalScore = ds.child("finalScore").getValue(int.class);
                    averageScoreList.add(finalScore);
                    int sum = 0;
                    for(int i = 0; i < averageScoreList.size(); i++){
                        sum = sum + averageScoreList.get(i);
                    }
                    int averageScoreFinal = sum/gameCount;

                    String averageScoreFinalString = valueOf(averageScoreFinal);

                    avgScore.setText(averageScoreFinalString);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    private void AverageStrikes(final int gameCount) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("games");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    int strikes = ds.child("strikeCount").getValue(int.class);
                    averageStrikesList.add(strikes);
                    int sum = 0;
                    for(int i = 0; i < averageStrikesList.size(); i++){
                        sum = sum + averageStrikesList.get(i);
                    }
                    int averageStrikesFinal = sum/gameCount;

                    String averageStrikesFinalString = valueOf(averageStrikesFinal);

                    avgStrikes.setText(averageStrikesFinalString);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    public void resetStatistics(){
        setStatsToZero();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("games");
        mDatabase.removeValue();
        myRef.child("users").child(user_id).child("gamecount").setValue(0);
    }

    public void setStatsToZero(){
        avgFrame.setText("0");
        avgClearFrames.setText("0");
        avgScore.setText("0");
        avgStrikes.setText("0");
        gmsPlayed.setText("0");
    }

    public void showGraphs(int gameCount){
        final String[] stats = {"Frames Cleared", "Average Frame", "Average Strikes", "Average Score"};
        if (gameCount < 5){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Too Few Games Played")
                    .setMessage("You need to play at least 5 games before you can see you stats here")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pick a stat to show");
            builder.setItems(stats, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if ("Frames Cleared".equals(stats[which])) {

                        goToFramesGraph();
                    } else if ("Average Frame".equals(stats[which])) {
                        goToAverageFrame();
                    } else if ("Average Strikes".equals(stats[which])) {
                        goToAverageStrikes();
                    } else if ("Average Score".equals(stats[which])) {
                        goToAverageScore();
                    }
                }
            });
            builder.show();
        }
    }

    public void goToFramesGraph(){
        Intent i = new Intent(this, FinalScoreStatistics.class);
        startActivity(i);
    }
    public void goToAverageFrame(){
        Intent i = new Intent(this, AverageFrameStatistics.class);
        startActivity(i);
    }
    public void goToAverageStrikes(){
        Intent i = new Intent(this, AverageStrikesStatistics.class);
        startActivity(i);
    }
    public void goToAverageScore(){
        Intent i = new Intent(this, FinalScoreStatistics.class);
        startActivity(i);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_game) {
            Intent i = new Intent(this, Game.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_home) {
            Intent i = new Intent(this, HomePage.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_statistics){
            Intent i = new Intent(this, FinalScoreStatistics.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_view_friends) {
            Intent i = new Intent(this, FindFriends.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_find_alley) {
            Intent i = new Intent(this, LocalAlleys.class);
            startActivity(i);
            finish();
        }
        return false;
    }
}
