package com.anfaas.lobbysystem;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalDatabase {
    String name;
    String email;
    String uid;
    int checkLogin;
    Context context;

    public LocalDatabase(String name, String email, String uid,Context context) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.context=context;
        SharedPreferences l_name = context.getSharedPreferences("NAME",Context.MODE_PRIVATE );
         SharedPreferences.Editor name_edit = l_name.edit();
         name_edit.putString("NAME",name);
         name_edit.commit();
        SharedPreferences l_email = context.getSharedPreferences("EMAIL",Context.MODE_PRIVATE );
        SharedPreferences.Editor email_edit = l_email.edit();
        email_edit.putString("EMAIL",email);
        email_edit.commit();
        SharedPreferences l_uid = context.getSharedPreferences("UID",Context.MODE_PRIVATE );
        SharedPreferences.Editor uid_edit = l_uid.edit();
        uid_edit.putString("UID",uid);
        uid_edit.commit();

    }
    public static void checkLogin(int check,Context context)
    {
        SharedPreferences l_checklogin=context.getSharedPreferences("LOGINCHECK",Context.MODE_PRIVATE);
        SharedPreferences.Editor logincheck_edit=l_checklogin.edit();
        logincheck_edit.putInt("LOGINCHECK",check);

    }

}
