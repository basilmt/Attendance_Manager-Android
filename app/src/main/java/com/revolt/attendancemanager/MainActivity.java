package com.revolt.attendancemanager;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST_TAG";

    Button users, newUsers, attendance, demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = findViewById(R.id.users);
        attendance = findViewById(R.id.attendance);
        newUsers = findViewById(R.id.newUsers);
        demo = findViewById(R.id.demo);

        users.setOnClickListener( view -> startActivity(new Intent(this,UserListerActivity.class)));
        attendance.setOnClickListener( view -> startActivity(new Intent(this,AttendanceUserListerActivity.class)));
        newUsers.setOnClickListener(view -> startActivity(new Intent(this,NewUserListerActivity.class)));
        demo.setOnClickListener(view -> startActivity(new Intent(this,DemoActivity.class)));

    }

}