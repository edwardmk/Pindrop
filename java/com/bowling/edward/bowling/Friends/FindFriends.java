package com.bowling.edward.bowling.Friends;

import android.content.Intent;
import android.service.voice.VoiceInteractionSessionService;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bowling.edward.bowling.Constructors.Friend;
import com.bowling.edward.bowling.Constructors.User;
import com.bowling.edward.bowling.Game;
import com.bowling.edward.bowling.HomePage;
import com.bowling.edward.bowling.LineGraphStats.FinalScoreStatistics;
import com.bowling.edward.bowling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FindFriends extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public ImageButton iButton;
    Button viewFriendStats;
    public EditText email;
    public TextView showEmail, showUserName;
    public DatabaseReference findAllUsers;
    DatabaseReference reference;
    String userID;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private static final String TAG = "AddToDatabase";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        findAllUsers = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        email = findViewById(R.id.emailToSearch);
        iButton = findViewById(R.id.searchButton);

        viewFriendStats = findViewById(R.id.showStats);

        viewFriendStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFriendStats();
            }
        });
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        showUserName = findViewById(R.id.userNameToShow);
        showEmail = findViewById(R.id.emailToShow);

//        addFriendButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public  void onClick(View v){
//                CheckFriend();
//            }
//
//        });
        iButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                String emailToSearch = email.getText().toString();
                SearchEmail(emailToSearch);
            }

        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        }
        public void SearchEmail(final String emailToSearch) {
            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
            mDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    User user = dataSnapshot.getValue(User.class);
                    String userEmail = user.getEmail();
                    String username = user.getUsername();
                    friendId = dataSnapshot.getKey();
                    if (userEmail.equals(emailToSearch)) {
                        showUserName.setText(username);
                        showEmail.setText(userEmail);
                        viewFriendStats.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                public void onChildRemoved(DataSnapshot ds) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        public void goToFriendStats(){
            Intent i = new Intent(FindFriends.this, ViewFriendStats.class);
            i.putExtra("friendId", friendId);
            startActivity(i);
        }


//        public void CheckFriend(){
//            final DatabaseReference emailRef = FirebaseDatabase.getInstance().getReference("users").child(userID).child("friends");
//            processDone = false;
//            emailRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    if (dataSnapshot.getValue() == null && processDone == false){
//                        Toast.makeText(FindFriends.this, "creating friends", Toast.LENGTH_SHORT).show();
//                     //   AddFriend();
//                        processDone = true;
//                    }
//                    else if (processDone == false){
//                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                        String email = data.child("email").getValue().toString();
//                        String emailOfFriend = showEmail.getText().toString();
//                        if (email.equals(emailOfFriend) && processDone == false) {
//                            checkIfExists = true;
//                            processDone = true;
//                        }
//                        else if (processDone == false && checkIfExists == false) {
//                            checkIfExists = false;
//                            processDone = true;
//                        }
//                    }
//                    if (checkIfExists == true && processDone == true) {
//                        Toast.makeText(FindFriends.this, "exists", Toast.LENGTH_SHORT).show();
//                        processDone = true;
//                    }
//                    else if (checkIfExists == false && processDone == true) {
//                        Toast.makeText(FindFriends.this, "not exists", Toast.LENGTH_SHORT).show();
//                        checkIfExists = true;
//                     //   AddFriend();
//                    }
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//    public void AddFriend() {
////        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
////        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userID);
////        ref.orderByChild("email").equalTo(showEmail.getText().toString()).addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                if (dataSnapshot.exists()) {
////                    Toast.makeText(FindFriends.this, "Stop!", Toast.LENGTH_LONG).show();
////                }
////                else {
//                    DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference().child("users");
//                    Query query = usersDb.orderByChild("email").startAt(showEmail.getText().toString()).endAt(showEmail.getText().toString() + "\uf8ff");
//                    query.addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            String email = "";
//                            String uid = dataSnapshot.getRef().getKey();
//                            String usernameToAdd = "";
//                            if (dataSnapshot.child("email").getValue() != null) {
//                                email = dataSnapshot.child("email").getValue().toString();
//                                usernameToAdd = dataSnapshot.child("username").getValue().toString();
//
//                                if (!email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
//                                    Friend obj = new Friend(email, usernameToAdd, uid);
//                                    final String user_id = mAuth.getCurrentUser().getUid();
//                                    myRef.child("users").child(user_id).child("friends").push().setValue(obj);
//                                } else {
//                                    Toast.makeText(FindFriends.this, "You can't be friends with yourself", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        public void onChildRemoved(DataSnapshot ds) {
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }


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
        else if (id == R.id.nav_statistics) {
            Intent i = new Intent(this, FinalScoreStatistics.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_view_friends) {
            Intent i = new Intent(this, FindFriends.class);
            startActivity(i);
            finish();
        }

        return false;

    }
}
