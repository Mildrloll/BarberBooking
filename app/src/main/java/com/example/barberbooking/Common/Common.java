package com.example.barberbooking.Common;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Common {
    public static String IS_LOGIN = "IsLogin";

    public static void updateToken(Context context, final String s) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if (/*accessToken*/user != null) {
            /*AccountKit.getCurrentAccount(new AccountKitCallback<Account>()
                @Override
                public void onSuccess(Account account){}*/
            //TODO Stuff in here need to stay and account and .toString() needs to be removed
            /*@Override
             * public void onError(AccountKitError accountKitError){});*/
        } else {
            //TODO user in the else needs to renamed to localUser
        }
    }
}
