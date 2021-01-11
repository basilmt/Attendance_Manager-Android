package com.revolt.attendancemanager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserListerActivity extends AppCompatActivity implements UserAdapter.OnClickListen {


    List<User> userList = new ArrayList<>();
    UserAdapter userAdapter;
    RecyclerView usersRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("Users");


        usersRV = findViewById(R.id.usersRV);
        userAdapter = new UserAdapter(userList, this);
        usersRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        usersRV.setHasFixedSize(true);
        usersRV.setAdapter(userAdapter);

        getUserDetails();


    }

    private void getUserDetails() {
        FirebaseDatabase.getInstance()
                .getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    userList.clear();
                    for (DataSnapshot i :snapshot.getChildren() ) {
                        User user = i.getValue(User.class);
                        userList.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(usersRV, "Server Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getBaseContext(), ShowUserActivity.class);
        intent.putExtra("name", userList.get(position).getName());
        intent.putExtra("place", userList.get(position).getPlace());
        intent.putExtra("ph", userList.get(position).getPh());
        intent.putExtra("email", userList.get(position).getEmail());

        startActivity(intent);
    }
}