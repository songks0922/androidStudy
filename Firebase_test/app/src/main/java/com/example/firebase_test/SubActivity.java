package com.example.firebase_test;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Button button = (Button)findViewById(R.id.submit);

        EditText firstName = (EditText)findViewById(R.id.fisrt);
        EditText lastName = (EditText)findViewById(R.id.last);
        EditText born = (EditText)findViewById(R.id.born);

        TextView textView = (TextView)findViewById(R.id.textView);

        //firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                user.put("first", firstName.getText().toString());
                user.put("last", lastName.getText().toString());
                user.put("born", born.getText().toString());
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
                textView.setText(firstName.getText().toString() + " " + lastName.getText().toString() + "\nborn: " + born.getText().toString());
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
//                    firstName.setText(snapshot.getData().get("first").toString());
//                    lastName.setText(snapshot.getData().get("last").toString());
//                    born.setText(snapshot.getData().get("born").toString());
                    textView.setText(snapshot.getData().get("first") + " " + snapshot.getData().get("last") + "\nborn: " + snapshot.getData().get("born"));
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        //delete
//        db.collection("users").document("")
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error deleting document", e);
//                    }
//                });
    }
}
