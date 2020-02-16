package com.anfaas.lobbysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;

public class RegisterActivity extends AppCompatActivity {
     //views
     EditText userName,email,password;
     Button login;
     //check if user login
     int x;
     //firebase database
      FirebaseDatabase database=FirebaseDatabase.getInstance();
      DatabaseReference myRef=database.getReference("Users");
      //firebase auth
      FirebaseAuth auth;
        //getting context
      View view;
      Context context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context=this.getApplicationContext();
        //referencing ids
        userName=findViewById(R.id.txtName);
        email=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPwd);
        login=findViewById(R.id.btnLogin);
          //on click on login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(userName.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "great", Toast.LENGTH_SHORT).show();
                    
                }
                firebaseRegister();
            }
        });
    }
     //creating account using firebase authentication
    private void firebaseRegister() {
             auth=FirebaseAuth.getInstance();//getting instance
             auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful())//if the task is successful
                             {   auth=FirebaseAuth.getInstance();
                                 FirebaseUser  firebaseUser=auth.getCurrentUser();
                                 //set name in firebase user
                                 UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().
                                             setDisplayName(userName.getText().toString()).build();
                                 assert firebaseUser != null;
                                 firebaseUser.updateProfile(profileUpdates);
                                 //saving information in user class
                                 User user=new User(firebaseUser.getDisplayName(),firebaseUser.getEmail(),firebaseUser.getUid());
                                 Toast.makeText(RegisterActivity.this, "registered", Toast.LENGTH_SHORT).show();
                                 myRef.child(firebaseUser.getUid().trim()).setValue(user);
                                 localSave(firebaseUser.getUid().toString());
                                 //now login
                              firebaseLogin();
                             }
                         }
                     });
    }

    public void firebaseLogin() {

        auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            x=1;
                         Intent intent=   new Intent(RegisterActivity.this,MainActivity.class);
                         intent.putExtra("login",x);
                         startActivity(intent);
                        }
                        else
                            x=0;
                        //storing data in local database
                        LocalDatabase.checkLogin(x,context.getApplicationContext());
                    }
                }
        );
    }

    public void localSave(String uid) {
        new LocalDatabase(
                userName.getText().toString(),
                email.getText().toString(),uid,
                context
        );

    }
}
