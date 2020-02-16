package com.anfaas.lobbysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineCheckLobby extends AppCompatActivity {
Button find;
EditText find_email;
FirebaseDatabase database=FirebaseDatabase.getInstance();
DatabaseReference myRef=database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_check_lobby);
        find=findViewById(R.id.search);
        find_email=findViewById(R.id.email_search);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             myRef.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for (DataSnapshot snap:dataSnapshot.getChildren())
                       {
                          User user = snap.getValue(User.class);
                          if (user.getEmail().equals(find_email.getText().toString()))
                          {
                              Toast.makeText(OnlineCheckLobby.this, "great", Toast.LENGTH_SHORT).show();
                          }
                       }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
            }
        });

    }
}
