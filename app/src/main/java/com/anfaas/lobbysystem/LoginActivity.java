package com.anfaas.lobbysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
     FirebaseAuth myAuth;
     EditText email,password;
     Button login;
     Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context =this.getApplicationContext();
        myAuth=FirebaseAuth.getInstance();
        //refrencing
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.btnlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {   FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                                    assert user != null;
                                    LocalDatabase localDatabase= new LocalDatabase(user.getDisplayName()
                                            , user.getEmail()
                                            ,user.getUid()
                                            ,context);
                                    int check=1;
                                    LocalDatabase.checkLogin(check,context);
                                    startActivity(new Intent(LoginActivity.this,OnlineCheckLobby.class));
                                }
                            }
                        });
            }
        });
    }
}
