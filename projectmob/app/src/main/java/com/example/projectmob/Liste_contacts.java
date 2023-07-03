package com.example.projectmob;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmob.Model.Contact;
import com.example.projectmob.adapters.MyAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.Objects;

public class Liste_contacts extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab_add;
    RecyclerView contactsRecycler;
    EditText barreRecherche;
    FirebaseFirestore db;
    LinkedList<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_contacts);

        contactsRecycler=(RecyclerView) findViewById(R.id.list_contacts);
        barreRecherche=(EditText) findViewById(R.id.search_text);
        fab_add=(FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);


        db = FirebaseFirestore.getInstance();


        //
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContacts();
        Log.i("sss",contacts.toString());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.fab_add){
            Intent myintent= new Intent(this, Ajouter_contact.class);
            startActivity(myintent);
        }
    }


    void getContacts(){
        contacts= new LinkedList<Contact>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("user").document(currentUser.getEmail());
        docRef.collection("contact").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Contact c= new Contact(document.get("nom").toString(),document.get("prenom").toString(),document.get("service").toString(),document.get("email").toString(),document.get("url").toString());
                                contacts.add(c);


                            }


                            // use this setting to improve performance if you know that changes
// in content do not change the layout size of the RecyclerView
                            contactsRecycler.setHasFixedSize(true);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(Liste_contacts.this);
                            contactsRecycler.setLayoutManager(layoutManager);

                            MyAdapter myAdapter = new MyAdapter(contacts, Liste_contacts.this);

                            contactsRecycler.setAdapter(myAdapter);

                        } else {
                            Log.d("not ok", "Error getting documents: ", task.getException());
                        }
                    }
                });



    }

/*
    void Synchronous(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("user").document(currentUser.getEmail());
        docRef.collection("contact")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("Erreur", "Listen failed.", e);
                            return;
                        }

                        contacts = new LinkedList<Contact>();
                        for (QueryDocumentSnapshot doc : value) {
                            Contact c= new Contact(doc.get("nom").toString(),doc.get("prenom").toString(),doc.get("service").toString(),doc.get("email").toString(),doc.get("url").toString());
                            contacts.add(c);
                        }
                        MyAdapter myAdapter = new MyAdapter(contacts, Liste_contacts.this);

                        contactsRecycler.setAdapter(myAdapter);
                        Log.d("Success", "ok " );
                    }
                });


    }

 */
}


/*
    void Synchronous(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("user").document(currentUser.getEmail());
        docRef.collection("contact")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("Erreur", "Listen failed.", e);
                            return;
                        }

                        contacts = new LinkedList<Contact>();
                        for (QueryDocumentSnapshot doc : value) {
                            Contact c= new Contact(doc.get("nom").toString(),doc.get("prenom").toString(),doc.get("service").toString(),doc.get("email").toString(),doc.get("url").toString());
                            contacts.add(c);
                        }
                        MyAdapter myAdapter = new MyAdapter(contacts, Liste_contacts.this);

                        contactsRecycler.setAdapter(myAdapter);
                        Log.d("Success", "ok " );
                    }
                });


    }

 */

