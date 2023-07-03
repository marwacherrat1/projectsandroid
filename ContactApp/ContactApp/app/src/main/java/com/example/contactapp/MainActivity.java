package com.example.contactapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout_auth_success;
    LinearLayout layout_auth;
    LinearLayout layout_acc;
    EditText username_auth;
    EditText password_auth;
    EditText firstname_acc;
    EditText lastname_acc;
    EditText username_acc;
    EditText password_acc;
    Button logIn_auth;
    Button signup_auth;
    Button signup_acc;
    Button btt_cancel_acc;
    ImageView backButton;

    private FirebaseAuth mAuth;
//    private DatabaseReference userDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisation des views
        layout_auth = (LinearLayout) findViewById(R.id.layout_auth);
        layout_acc = (LinearLayout) findViewById(R.id.layout_acc);
//        layout_auth_success = (LinearLayout) findViewById(R.id.layout_auth_success);
        username_auth = (EditText) findViewById(R.id.username_auth);
        password_auth = (EditText) findViewById(R.id.password_auth);
        firstname_acc = (EditText) findViewById(R.id.firstname_acc);
        lastname_acc = (EditText) findViewById(R.id.lastname_acc);
        username_acc = (EditText) findViewById(R.id.username_acc);
        password_acc = (EditText) findViewById(R.id.password_acc);
        logIn_auth = (Button) findViewById(R.id.logIn_auth);
        signup_auth = (Button) findViewById(R.id.btt_singup_auth);
        signup_acc = (Button) findViewById(R.id.btt_singup_acc);
        btt_cancel_acc=(Button) findViewById(R.id.btt_cancel_acc);
//        ok = (Button) findViewById(R.id.btt_ok);
        backButton = (ImageView) findViewById(R.id.back_button);

        //mettre les boutons en ecoute
        logIn_auth.setOnClickListener(this);
        signup_auth.setOnClickListener(this);
        signup_acc.setOnClickListener(this);
        btt_cancel_acc.setOnClickListener(this);
//        ok.setOnClickListener(this);
        backButton.setOnClickListener(this);

        //initialisation de Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser) {

        if (currentUser!=null){
//            layout_auth.setVisibility(View.GONE);
//            layout_acc.setVisibility(View.GONE);
//            layout_auth_success.setVisibility(View.VISIBLE);
            Intent myintent= new Intent(this, Liste_contacts.class);
            startActivity(myintent);
        }
        else{
            layout_auth.setVisibility(View.VISIBLE);
            layout_acc.setVisibility(View.GONE);
//            layout_auth_success.setVisibility(View.GONE);
        }
    }
    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.logIn_auth){
            String email = username_auth.getText().toString();
            String password = password_auth.getText().toString();
            signIn( email,  password);}
        else if (view.getId()==R.id.btt_singup_auth) {
            layout_acc.setVisibility(View.VISIBLE);
            layout_auth.setVisibility(View.GONE);
        }
        else if (view.getId()==R.id.btt_cancel_acc) {
            layout_acc.setVisibility(View.GONE);
            layout_auth.setVisibility(View.VISIBLE);
        }
        else if (view.getId()==R.id.btt_singup_acc) {
            String email = username_acc.getText().toString();
            String password = password_acc.getText().toString();
            createAccount(email,  password) ;
        }
//        else if (view.getId()==R.id.btt_ok) {
//            Intent myintent= new Intent(this, Liste_contacts.class);
//            startActivity(myintent);
//        }
        else if(view.getId()==R.id.back_button){
            onBackPressed(); // gérer l'événement de clic sur la flèche arrière
        }

    }

//    private void insertUserData(String firstname, String lastname, String email, String password) {
//
//        Users user = new Users(firstname,lastname , email, password );
////L'utilisation de " Push()" nous permet de generer un ID unique pour chaque insertion
//        userDbRef.push().setValue(user);
//        Toast.makeText(MainActivity.this, " User Inserer", Toast.LENGTH_SHORT).show();
//    }

}

