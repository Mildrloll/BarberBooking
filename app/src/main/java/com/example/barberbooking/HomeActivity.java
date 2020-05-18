package com.example.barberbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.barberbooking.Common.Common;
import com.example.barberbooking.Fragments.HomeFragment;
import com.example.barberbooking.Fragments.ShoppingFragment;
import com.example.barberbooking.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    BottomSheetDialog bottomSheetDialog;
    CollectionReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        //Init
        userRef = FirebaseFirestore.getInstance().collection("User");
        //Check intent, if is login = true, enable full access
        //If is login = false just let user around shopping to view
        if (getIntent() != null) {
            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN, false);
            if (isLogin) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    DocumentReference currentUser = userRef.document(user.getPhoneNumber());
                    currentUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot userSnapShot = task.getResult();
                                if (userSnapShot.exists()) {
                                    showUpdateDialog(user.getPhoneNumber());
                                }
                            }
                        }
                    });
                }
            }
        }
        //View
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_home) {
                    fragment = new HomeFragment();
                } else if (menuItem.getItemId() == R.id.action_shopping) {
                    fragment = new ShoppingFragment();
                }
                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }

    private void showUpdateDialog(String phoneNumber) {
        //Init dialog
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(false);
        View sheetView = getLayoutInflater().inflate(R.layout.layout_update_information, null);
        Button btn_update = (Button) sheetView.findViewById(R.id.btn_update);
        TextInputEditText edt_name = (TextInputEditText) sheetView.findViewById(R.id.edt_name);
        TextInputEditText edt_address = (TextInputEditText) sheetView.findViewById(R.id.edt_address);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(edt_name.getText().toString(), edt_address.getText().toString(), phoneNumber);
                userRef.document(phoneNumber).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        bottomSheetDialog.dismiss();
                        Toast.makeText(HomeActivity.this, "Thank you", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        bottomSheetDialog.dismiss();
                        Toast.makeText(HomeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
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
