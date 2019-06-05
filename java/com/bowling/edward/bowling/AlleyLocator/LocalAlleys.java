package com.bowling.edward.bowling.AlleyLocator;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bowling.edward.bowling.Constructors.Alley;
import com.bowling.edward.bowling.Friends.FindFriends;
import com.bowling.edward.bowling.Game;
import com.bowling.edward.bowling.HomePage;
import com.bowling.edward.bowling.LineGraphStats.FinalScoreStatistics;
import com.bowling.edward.bowling.LoadingScreen;
import com.bowling.edward.bowling.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LocalAlleys extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mListView;
    private static final String apiKey = "AIzaSyCIUdmvacCqSeWbOtLL1Tg2CEmYkMbBQew";

   private static final String api = "https://maps.googleapis.com/maps/api/place";
    private static final String details = "/details";
    private static final String search = "/textsearch";
    private static final String json = "/json?";
    private static final String log = "alley";
    Double longitude, latitude;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_alleys);
        mListView = findViewById(R.id.alleyView);
        getCoordinates();
    }

    public void getCoordinates() {
        if (ActivityCompat.checkSelfPermission(LocalAlleys.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocalAlleys.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocalAlleys.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            int radius = 10;
            List<Alley> list = search(longitude, latitude, radius);
            if (list != null) {
                AlleyAdapter adapter = new AlleyAdapter(list, this);

                RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager(getApplicationContext());
                mListView.setLayoutManager(reLayoutManager);
                mListView.setItemAnimator(new DefaultItemAnimator());
                mListView.setAdapter(adapter);
            } else {
                Toast.makeText(LocalAlleys.this, "No alleys nearby.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public ArrayList<Alley> search(double lat, double lng, int radius) {
        ArrayList<Alley> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(api);
            sb.append(search);
            sb.append(json);
            sb.append("location=" + (lng) + "," + (lat));
            sb.append("&radius=" + (radius));
            sb.append("&query=bowling+alley");
            sb.append("&key=" + apiKey);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(log, "Error processing API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(log, "Error connecting to API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");
            resultList = new ArrayList<>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Alley place = new Alley(null, null, 0, null, null);
                place.name = predsJsonArray.getJSONObject(i).getString("name");
                place.rating = predsJsonArray.getJSONObject(i).getDouble("rating");
                place.location = predsJsonArray.getJSONObject(i).getString("formatted_address");
                place.placeID = predsJsonArray.getJSONObject(i).getString("place_id");
                String latitude = Double.toString(lat);
                String longitude = Double.toString(lng);
                place.currLocation = longitude + "," + latitude;
                if (count < 6) {
                    place.website = websiteMaker(place.placeID);
                }
                if (resultList.size() < 5) {
                    resultList.add(place);
                }
            }
        } catch (JSONException e) {
            Log.e(log, "Error processing JSON results", e);
        }

        return resultList;
    }
    public String websiteMaker(String placeID) {
            HttpURLConnection conn = null;
            String webObj = null;
            StringBuilder jsonDetails = new StringBuilder();
            try {
                StringBuilder sb1 = new StringBuilder(api);
                sb1.append(details);
                sb1.append(json);
                sb1.append("place_id=" + placeID);
                sb1.append("&fields=website");
                sb1.append("&key=" + apiKey);

                URL url = new URL(sb1.toString());
                conn = (HttpURLConnection) url.openConnection();

                InputStreamReader in = new InputStreamReader(conn.getInputStream());

                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonDetails.append(buff, 0, read);
                }

            } catch (MalformedURLException e) {
                Log.e(log, "Error processing API URL", e);
            } catch (IOException e) {
                Log.e(log, "Error connecting to API", e);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            try {
                JSONObject reader = new JSONObject(jsonDetails.toString());
                JSONObject sys  = reader.getJSONObject("result");
                webObj = sys.getString("website");
            } catch (JSONException e) {
                Log.e(log, "Error processing JSON results", e);
            }
            count++;
            return webObj;
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
        else if (id == R.id.nav_log_out) {
            Intent i = new Intent(this, LoadingScreen.class);
            startActivity(i);
            finish();
        }
        return false;
    }
}
