package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity implements View.OnClickListener  {

    BottomNavigationView bottonNavMenu;
    ImageView favoriteButton;
    ImageView backButton;
    ImageView logoutButton;
    Button btt_voir_Temp;
    Button btt_voir_lock;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

            backButton = (ImageView) findViewById(R.id.back_button);
            favoriteButton = (ImageView) findViewById(R.id.favorite_button);
            logoutButton=(ImageView) findViewById(R.id.logout_button);
            bottonNavMenu = findViewById(R.id.bottomNavMenu);

            backButton.setOnClickListener(this);
            favoriteButton.setOnClickListener(this);
            logoutButton.setOnClickListener(this);

            mAuth = FirebaseAuth.getInstance();

            bottonNavMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.Home:
                            Intent Intent1 = new Intent(Profile.this, Liste_contacts.class);
                            startActivity(Intent1);
                            break;
                        case R.id.Settings:
                            Intent settingsIntent = new Intent(Profile.this, settings.class);
                            startActivity(settingsIntent);
                            break;
                        case R.id.Profile:
                            Intent Intent2 = new Intent(Profile.this, Profile.class);
                            startActivity(Intent2);
                            break;

                    }
                    return true;
                }
            });
        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.back_button){
                onBackPressed(); // gérer l'événement de clic sur la flèche arrière
            }else if(view.getId()==R.id.btt_voir_Temp)
            {

            }else if(view.getId()==R.id.btt_voir_lock)
            {

            }
            else if(view.getId()==R.id.logout_button){
                mAuth.signOut();
                Intent intent = new Intent(this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
}