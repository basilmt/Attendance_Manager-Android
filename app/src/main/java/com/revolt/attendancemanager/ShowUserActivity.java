package com.revolt.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowUserActivity extends AppCompatActivity {

    TextView name, place, phone, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        Bundle bundle = getIntent().getExtras();
        String nameText = bundle.getString("name");
        String placeText = bundle.getString("place");
        String emailText = bundle.getString("email");
        String phoneText = bundle.getString("ph");


        name = findViewById(R.id.name);
        place = findViewById(R.id.place);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        name.setText(nameText);
        place.setText(placeText);
        email.setText(emailText);
        phone.setText(phoneText);


    }
}