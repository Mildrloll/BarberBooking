package com.example.barberbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    //Check if user is exists
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //AccountKit.getCurrentAccount(new AccountKitCallback<Account>(){//
    // @Override
    // public void onSuccess(final Account account){
    //      if(account != null){
    //      //TODO everything in here needs to stay and make account to user and remove .toString()
    //      }}
    //@Override
    // public void OnError(AccountKitError accountError){
    //      }})
}
