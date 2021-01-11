package com.revolt.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegNewUserActivity extends AppCompatActivity {

    String id;
    EditText name, place, phone, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_new_user);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        Button submit;

        name = findViewById(R.id.name);
        place = findViewById(R.id.place);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(view -> saveDetails());

    }

    private void saveDetails() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name.getText().toString());
        result.put("place", place.getText().toString());
        result.put("ph", phone.getText().toString());
        result.put("email", email.getText().toString());

        FirebaseDatabase.getInstance().getReference()
                .child("users").child(id).setValue(result)
                .addOnSuccessListener(aVoid -> {
                    FirebaseDatabase.getInstance().getReference()
                            .child("new").child(id).removeValue();
                    finish();
                });

    }
}