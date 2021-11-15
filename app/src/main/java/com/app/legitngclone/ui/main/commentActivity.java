package com.app.legitngclone.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.legitngclone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class commentActivity extends AppCompatActivity {

    EditText editText;
    FloatingActionButton send;
    Button back;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String title;
    private DatabaseReference root;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Images = new ArrayList<>();
    ArrayList<String> Dates = new ArrayList<>();
    ArrayList<String> Comments = new ArrayList<>();

    commentsAdapter commentsAdapter;
    String saveCurrentDate;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        root = FirebaseDatabase.getInstance().getReference();
        editText = findViewById(R.id.editTextTextPersonName);
        send = findViewById(R.id.floatingActionButton);
        back = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.commentsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
        }

        if (user != null) {
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!editText.getText().toString().equals(""))
                        sendComment();
                }
            });
        }
        root.child("Comments").child(title).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Log.i("snapshot1", snapshot.child("name").getValue().toString());
                    Log.i("snapshot2", snapshot.child("date").getValue().toString());
                    Log.i("snapshot3", snapshot.child("imageUrl").getValue().toString());
                    Log.i("snapshot4", snapshot.child("commentText").getValue().toString());

                    Names.add(snapshot.child("name").getValue().toString());
                    Dates.add(snapshot.child("date").getValue().toString());
                    Images.add(snapshot.child("imageUrl").getValue().toString());
                    Comments.add(snapshot.child("commentText").getValue().toString());
                    commentsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String
                    previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String
                    previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        commentsAdapter = new commentsAdapter(Names, Images, Dates, Comments, commentActivity.this);
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

    }

    private void sendComment() {
        String commentSender_ref = "Comments/" + title;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentDate = currentDate.format(calForDate.getTime()) + ", " + currentTime.format(calForTime.getTime());
        DatabaseReference databaseReference = root.child("Comments").child(title).push();
        String pushKey = databaseReference.getKey();
        Map hashMap = new HashMap<>();
        hashMap.put("name", user.getDisplayName());
        hashMap.put("commentText", editText.getText().toString());
        hashMap.put("date", saveCurrentDate);
        hashMap.put("imageUrl", user.getPhotoUrl().toString());
        Map hashMap2 = new HashMap<>();
        hashMap2.put(commentSender_ref + "/" + pushKey, hashMap);
        root.updateChildren(hashMap2).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                editText.setText("");
            }
        });

    }

    private void FetchComments() {


    }
}