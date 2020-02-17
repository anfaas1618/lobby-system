package com.anfaas.lobbysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
CardView user_found,friend_request;
TextView name_found,request_name;
Handler mHandler;
    Context context ;
    String friendUid;
    String friend_uid;

FirebaseDatabase database=FirebaseDatabase.getInstance();
DatabaseReference myRef=database.getReference("Users");
    FirebaseDatabase database1=FirebaseDatabase.getInstance();
    DatabaseReference reference=database1.getReference("request");
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
        friend_request =findViewById(R.id.request_friend);

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
                          {    friendUid=user.getUid();
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
        accept_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database2=FirebaseDatabase.getInstance();
                DatabaseReference friends = database2.getReference("Friends");
                Friends friend=new Friends(friend_uid);
                friends.child(LocalDatabase.getLocalUid(context)).setValue(friend);
                Friends me=new Friends(LocalDatabase.getLocalUid(context));
                friends.child(friend_uid).setValue(me);
            }
        });
     add.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             AddFriend friend_request=new AddFriend(LocalDatabase.getLocalUid(context),friendUid,LocalDatabase.name);
             reference.child(LocalDatabase.getLocalUid(context)).setValue(friend_request)
                     .addOnCompleteListener(new OnCompleteListener<Void>() {
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

        {  reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren())
                {

                    Toast.makeText(OnlineCheckLobby.this,"in runnable",Toast.LENGTH_SHORT).show();
                    AddFriend friend=snap.getValue(AddFriend.class);
                    Log.i("check",LocalDatabase.getLocalUid(context).trim());
                    Log.i("check2",friend.recievers_Uid.trim());

                    if (friend.recievers_Uid.trim().equals(LocalDatabase.getLocalUid(context).trim()))
                    {Log.i("checkdd",LocalDatabase.getLocalUid(context));
                        request_name.setText(friend.senders_name);
                        friend_request.setVisibility(View.VISIBLE);
                        friend_uid=friend.senders_Uid;
                        Toast.makeText(context, "wohoo", Toast.LENGTH_SHORT).show();
                        reference.removeValue();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
