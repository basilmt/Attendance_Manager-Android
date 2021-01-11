package com.revolt.attendancemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DemoActivity extends AppCompatActivity {


    TextView index;
    EditText id;
    Button register, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        index = findViewById(R.id.index);
        id = findViewById(R.id.id);
        register = findViewById(R.id.register);
        submit = findViewById(R.id.submit);

        setIndex();
        register.setOnClickListener(view -> registerUser());
        submit.setOnClickListener(view -> submitAttendance());

    }

    private void setIndex() {
        FirebaseDatabase.getInstance()
                .getReference().child("utils").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot i :snapshot.getChildren() ) {
                        if (i.getKey().equals("index")) {
                            index.setText(i.getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(index, "Server Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void submitAttendance() {
        String tId = id.getText().toString();
        if (Integer.parseInt(tId) >= Integer.parseInt(index.getText().toString())){
            Snackbar.make(index, "Id not found", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put(String.valueOf(System.currentTimeMillis()), true);

        FirebaseDatabase.getInstance()
                .getReference().child("attendance").child(tId)
                .updateChildren(result);


    }

    private void registerUser() {
        FirebaseDatabase.getInstance()
                .getReference().child("utils").child("index")
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        Object inx = currentData.getValue();
                        if (inx == null){
                            return Transaction.success(currentData);
                        }
                        String currentIndex = String.valueOf((Integer.parseInt(inx.toString())));

                        HashMap<String, Object> result = new HashMap<>();
                        result.put(currentIndex, true);
                        FirebaseDatabase.getInstance()
                                .getReference().child("new").updateChildren(result);

                        currentData.setValue(Integer.parseInt(currentIndex)+1);
                        return Transaction.success(currentData);

                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        if (!committed) {
                            Snackbar.make(index, "Server Error", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });
    }
}