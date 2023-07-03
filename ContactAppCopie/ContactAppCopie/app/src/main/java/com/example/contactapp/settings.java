package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class settings extends AppCompatActivity implements View.OnClickListener  {
    LinearLayout Layout_Settings;
    LinearLayout Temp_Controle;
    LinearLayout Lock_Controle;
    BottomNavigationView bottonNavMenu;
    ImageView favoriteButton;
    ImageView backButton;
    ImageView logoutButton;

    Button btt_voir_Temp;
    Button btt_voir_lock;
    TextView temperatureVal;
    TextView humidityVal;
    Button lockUnlockBtt;
    FirebaseDatabase database;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backButton = (ImageView) findViewById(R.id.back_button);
        favoriteButton = (ImageView) findViewById(R.id.favorite_button);
        logoutButton=(ImageView) findViewById(R.id.logout_button);
        bottonNavMenu = findViewById(R.id.bottomNavMenu);
        Layout_Settings = (LinearLayout) findViewById(R.id.Layout_Settings);
        Temp_Controle = (LinearLayout) findViewById(R.id.Temp_Controle);
        Lock_Controle = (LinearLayout) findViewById(R.id.Lock_Controle);
        btt_voir_Temp = (Button) findViewById(R.id.btt_voir_Temp);
        btt_voir_lock = (Button) findViewById(R.id.btt_voir_lock);
        humidityVal= (TextView) findViewById(R.id.humidityVal);
        temperatureVal= (TextView) findViewById(R.id.temperatureVal);
        lockUnlockBtt = (Button) findViewById(R.id.lockUnlockBtt);

        backButton.setOnClickListener(this);
        favoriteButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        btt_voir_Temp.setOnClickListener(this);
        btt_voir_lock.setOnClickListener(this);
        lockUnlockBtt.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        updateUI(0);
        bottonNavMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent Intent1 = new Intent(settings.this, Liste_contacts.class);
                        startActivity(Intent1);
                        break;
                    case R.id.Settings:
                        updateUI(0);
//                        Intent settingsIntent = new Intent(settings.this, settings.class);
//                        startActivity(settingsIntent);
                       break;
                    case R.id.Profile:
                        Intent Intent2 = new Intent(settings.this, Profile.class);
                        startActivity(Intent2);
                        break;
                }
                return true;
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Vérifiez si les nœuds humidity et temperature existent dans la base de données
                if (dataSnapshot.hasChild("humidity")) {
                    String humidity = dataSnapshot.child("humidity").getValue().toString();
                    humidityVal.setText(humidity + " %");
                }
                if (dataSnapshot.hasChild("temperature")) {
                    String temperature = dataSnapshot.child("temperature").getValue().toString();
                    temperatureVal.setText(temperature+" °C");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Gestion des erreurs de lecture de la base de données
                Log.e("Firebase", "Erreur de lecture de la base de données: " + databaseError.getMessage());
            }
        });
    }
    private void updateUI(int param) {

        if (param==0){
            Layout_Settings.setVisibility(View.VISIBLE);
            Temp_Controle.setVisibility(View.GONE);
            Lock_Controle.setVisibility(View.GONE);
        }
         else if(param==1){
            Layout_Settings.setVisibility(View.GONE);
            Temp_Controle.setVisibility(View.VISIBLE);
            Lock_Controle.setVisibility(View.GONE);
        }else if(param==2){
            Layout_Settings.setVisibility(View.GONE);
            Temp_Controle.setVisibility(View.GONE);
            Lock_Controle.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View view) {
         if(view.getId()==R.id.back_button){
            onBackPressed(); // gérer l'événement de clic sur la flèche arrière
         }else if(view.getId()==R.id.btt_voir_Temp)
         {
             updateUI(1);

         }else if(view.getId()==R.id.btt_voir_lock)
         {
             updateUI(2);
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