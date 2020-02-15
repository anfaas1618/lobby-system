package com.anfaas.lobbysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
     //views
     EditText userName,email,password;
     Button login;
     //firebase database
      FirebaseDatabase database=FirebaseDatabase.getInstance();
      DatabaseReference myRef=database.getReference("Users");
      //firebase auth
      FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //refrencing ids
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
                firebaseLogin();

            }
        });
    }

    private void firebaseLogin() {
             auth=FirebaseAuth.getInstance();//getting instance
             auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful())
                             {   auth=FirebaseAuth.getInstance();
                                 FirebaseUser  firebaseUser=auth.getCurrentUser();
                                 UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().
                                             setDisplayName(userName.getText().toString()).build();
                                 assert firebaseUser != null;
                                 firebaseUser.updateProfile(profileUpdates);
                                 User user=new User(firebaseUser.getDisplayName(),firebaseUser.getEmail(),firebaseUser.getUid());
                                 Toast.makeText(RegisterActivity.this, "registered", Toast.LENGTH_SHORT).show();
                                 myRef.child(firebaseUser.getUid().trim()).setValue(user);
                             }
                         }
                     });
    }
}
