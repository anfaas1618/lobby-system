package com.anfaas.lobbysystem;

public class AddFriend {
      boolean is_friends=false;
 public      String senders_Uid;
  public     String recievers_Uid;
  public     String senders_name;
      public  AddFriend()
      {

      }

    public AddFriend( String senders_Uid, String recievers_Uid,String senders_name) {
        this.senders_Uid = senders_Uid;
        this.recievers_Uid = recievers_Uid;
        this.senders_name=senders_name;
    }

}
