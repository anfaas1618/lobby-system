package com.anfaas.lobbysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineCheckLobby extends AppCompatActivity {
Button find,add,accept_req;
EditText find_email;
CardView user_found;
TextView name_found,request_name;
Handler mHandler;
    Context context ;
    User user;
FirebaseDatabase database=FirebaseDatabase.getInstance();
DatabaseReference myRef=database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_check_lobby);
        context=this.getApplicationContext();
        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable,5000);

        user_found =findViewById(R.id.user_found);
        find=findViewById(R.id.search);
        find_email=findViewById(R.id.email_search);
        name_found=findViewById(R.id.found_name);
        add=findViewById(R.id.add);
        accept_req=findViewById(R.id.request_accept_btn);
        request_name=findViewById(R.id.request_name);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             myRef.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for (DataSnapshot snap:dataSnapshot.getChildren())
                       {
                           user = snap.getValue(User.class);
                          if (user.getEmail().equals(find_email.getText().toString()))
                          {
                              Toast.makeText(OnlineCheckLobby.this, "great", Toast.LENGTH_SHORT).show();
                              name_found.setText(user.getName());
                              user_found.setVisibility(View.VISIBLE);
                          }
                       }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
            }
        });
     add.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             FirebaseDatabase database1=FirebaseDatabase.getInstance();
             DatabaseReference reference=database1.getReference("request");
             reference.child(LocalDatabase.getLocalUid(context)).setValue(user.getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     user_found.setVisibility(View.GONE);
                 }
             });
         }
     });


    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            Toast.makeText(OnlineCheckLobby.this,"in runnable",Toast.LENGTH_SHORT).show();

            OnlineCheckLobby.this.mHandler.postDelayed(m_Runnable, 5000);
        }


    };
    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(m_Runnable);
        finish();

    }
}
