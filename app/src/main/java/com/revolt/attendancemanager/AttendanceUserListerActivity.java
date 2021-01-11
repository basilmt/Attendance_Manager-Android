package com.revolt.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttendanceUserListerActivity extends AppCompatActivity implements UserNameIdAdapter.OnClickListen {

    List<UserId> userName = new ArrayList<>();
    UserNameIdAdapter userNameAdapter;
    RecyclerView UsersListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_user_lister);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("Attendance");

        UsersListRV = findViewById(R.id.UsersListRV);
        userNameAdapter = new UserNameIdAdapter(userName, this);
        UsersListRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        UsersListRV.setHasFixedSize(true);
        UsersListRV.setAdapter(userNameAdapter);

        getUserDetails();


    }

    private void getUserDetails() {
        FirebaseDatabase.getInstance()
                .getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName.clear();
                if (snapshot.exists()){
                    findViewById(R.id.emptyList).setVisibility(View.GONE);
                    for (DataSnapshot i :snapshot.getChildren() ) {
                        String id = i.getKey();
                        String name = i.child("name").getValue().toString();
                        userName.add(new UserId(name,id));
                    }
                }
                else {
                    findViewById(R.id.emptyList).setVisibility(View.VISIBLE);
                }
                userNameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(UsersListRV, "Server Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getBaseContext(), ShowAttendanceActivity.class);
        intent.putExtra("id", userName.get(position).getId());
        startActivity(intent);
    }
}