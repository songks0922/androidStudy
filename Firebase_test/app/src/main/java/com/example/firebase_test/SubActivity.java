package com.example.firebase_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SubActivity extends AppCompatActivity {
    public static final int re = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Button button = (Button)findViewById(R.id.submit);
        Button recycler = (Button)findViewById(R.id.recycler);

        EditText firstName = (EditText)findViewById(R.id.fisrt);
        EditText lastName = (EditText)findViewById(R.id.last);
        EditText phonNumber = (EditText)findViewById(R.id.phonNumber);

        TextView textView = (TextView)findViewById(R.id.textView);

        //firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                user.put("first", firstName.getText().toString());
                user.put("last", lastName.getText().toString());
                user.put("phonNumber", phonNumber.getText().toString());
                // Add a new document with a generated ID
                db.collection("users").document("test")
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added with ID: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
                textView.setText(firstName.getText().toString() + " " + lastName.getText().toString() + "\nphonNumber: " + phonNumber.getText().toString());
            }
        });

        final DocumentReference docRef = db.collection("users").document("test");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    //Log.d(TAG, "Current data: " + snapshot.getData());
                    textView.setText(snapshot.getData().get("first") + " " + snapshot.getData().get("last") + "\nphonNumber: " + snapshot.getData().get("phonNumber"));
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), recycelerActivity.class);
                startActivityForResult(intent, re);
            }
        });
    }
}
