package com.revolt.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowAttendanceActivity extends AppCompatActivity implements TimeAdapter.OnClickListen {

    String id;
    List<String> time = new ArrayList<>();
    TimeAdapter timeAdapter;
    RecyclerView attendanceRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        attendanceRV = findViewById(R.id.attendanceRV);
        timeAdapter = new TimeAdapter(time, this);
        attendanceRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        attendanceRV.setHasFixedSize(true);
        attendanceRV.setAdapter(timeAdapter);

        getAttendance();

    }

    private void getAttendance() {
        FirebaseDatabase.getInstance()
                .getReference().child("attendance")
                .child(id)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    time.clear();
                    for (DataSnapshot i : snapshot.getChildren()) {
                        time.add(i.getKey());
                    }
                    timeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(attendanceRV, "Server Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Snackbar.make(attendanceRV, "verthe", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}