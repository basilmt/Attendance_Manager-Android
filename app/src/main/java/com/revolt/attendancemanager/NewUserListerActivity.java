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

public class NewUserListerActivity extends AppCompatActivity implements UserNameAdapter.OnClickListen {

    List<String> userId = new ArrayList<>();
    UserNameAdapter userNameAdapter;
    RecyclerView usersRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_lister);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("New Users");

        usersRV = findViewById(R.id.newUsersRV);
        userNameAdapter = new UserNameAdapter(userId, this);
        usersRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        usersRV.setHasFixedSize(true);
        usersRV.setAdapter(userNameAdapter);

        getNewUserDetails();

    }

    private void getNewUserDetails() {
        FirebaseDatabase.getInstance()
                .getReference().child("new").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userId.clear();
                if (snapshot.exists()){
                    findViewById(R.id.emptyList).setVisibility(View.GONE);
                    for (DataSnapshot i :snapshot.getChildren() ) {
                        userId.add(i.getKey());
                    }
                }
                else {
                    findViewById(R.id.emptyList).setVisibility(View.VISIBLE);
                }
                userNameAdapter.notifyDataSetChanged();
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
        Intent intent = new Intent(getBaseContext(), RegNewUserActivity.class);
        intent.putExtra("id", userId.get(position));
        startActivity(intent);
    }
}