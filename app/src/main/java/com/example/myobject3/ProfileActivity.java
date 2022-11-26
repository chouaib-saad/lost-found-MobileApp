package com.example.myobject3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {

    TextView edName,edPhone,edEmail,edPassw,edAdress;
    EditText champText;


    //Firebase Firestore references
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    public String name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edName = findViewById(R.id.edName);
        edPhone = findViewById(R.id.edPhone);
        edEmail = findViewById(R.id.edEmail);
        edPassw = findViewById(R.id.edPassw);
        edAdress = findViewById(R.id.edAdress);

        champText = findViewById(R.id.champText);





        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                edName.setText(value.getString("Name"));
                edEmail.setText(value.getString("Email"));
                edPhone.setText(value.getString("mobile"));
                edAdress.setText(value.getString("adress"));

                name = value.getString("Name");
                email = value.getString("Email");

            }
        });


    } //onCreate Methode end









    public void shereOption(View view) {
        String texte = champText.getText().toString();
        if(texte.isEmpty()){
            Toast.makeText(getApplicationContext(), "Ecrire quelque chose..", Toast.LENGTH_SHORT).show();
        }else{
            shereText("je suis "+name+" :\n"+texte);
        }
    }












    private void shereText(String s) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,s);
        intent.setType("text/plain");
        if(intent.resolveActivity(getPackageManager()) != null){
            Toast.makeText(getApplicationContext(),"partage avec succes", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"pas des applications de partage de ce telephone", Toast.LENGTH_SHORT).show();
        }
    }













}   //class end