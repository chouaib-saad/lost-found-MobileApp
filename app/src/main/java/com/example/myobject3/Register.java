package com.example.myobject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText edName, edPhone,edEmail,edPassw,edConf,edAdress;
    private Button btnRegistermail,btnlogin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edName = findViewById(R.id.edName);
        edPhone = findViewById(R.id.edPhone);
        edEmail = findViewById(R.id.edEmail);
        edPassw = findViewById(R.id.edPassw);
        edConf = findViewById(R.id.edConf);
        edAdress = findViewById(R.id.edAdress);
        btnRegistermail = findViewById(R.id.btnRegistermail);
        btnlogin = findViewById(R.id.btnlogin);
        db = FirebaseFirestore .getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);






        //login button
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), LogEmail.class));
                finish();
            }
        });







        btnRegistermail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final  String Name = edName.getText().toString();
                final String mobile = edPhone.getText().toString();
                final  String Email = edEmail.getText().toString().trim();
                String Password = edPassw.getText().toString().trim();
                String ConPass = edConf.getText().toString();
                final   String adress = edAdress.getText().toString();
                if(!validateName()|!validatephone()|!validateEmail()|!validatePassword()|!validateConfPassword()|!validateAdress()){
                    Toast.makeText(getApplicationContext(),"verifier votre donnes est ressayer..", Toast.LENGTH_SHORT).show();
                    return;

                    /* point d'entre au activite "inscription verfier*/
                }else {


                    progressBar.setVisibility(View.VISIBLE);
                    // register the user in firebase

                    mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // send verification link
                                FirebaseUser fuser = mAuth.getCurrentUser();

                              userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = db.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Name", Name);
                                user.put("Email", Email);
                                user.put("mobile", mobile);
                                user.put("adress", adress);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        android.util.Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
//                                startActivity(new Intent(getApplicationContext(), LogEmail.class));
                                progressBar.setVisibility(View.INVISIBLE);
                                finish();

                            } else {
                                Toast.makeText(Register.this, "Erreur : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });



    } //OnCreate methode end











    //    **validation functions***

    private Boolean validateName() {
        String val = edName.getText().toString();
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            edName.requestFocus();
            edName.setError("le champ ne peut pas etre vide");
            return false;
        } else {
            edName.setError(null);
            return true;
        }

    }

    private Boolean validateAdress() {
        String val = edAdress.getText().toString();
        /*en peut utiliser "\\A\\w{4,20}\\z" (touts le caract a lexep de spaces)
        au lieu de "(?=\\s+$)"
        pour eliminer les espaces blanc */
        //String noWhiteSpace ="(?=\\s+$)";
        String noWhiteSpace ="\\A\\w{4,20}\\z";
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            edAdress.requestFocus();
            edAdress.setError("le champ ne peut pas etre vide");
            return false;
        } else if(val.length()>=15){
            edAdress.requestFocus();
            edAdress.setError("l'adresse est trop long");
            return false;
        }
        else {
            edAdress.setError(null);
            return true;
        }

    }

    private Boolean validateEmail() {
        String val = edEmail.getText().toString();
        /*regic expression: pour Respecter la forme de @ email*/
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            edEmail.requestFocus();
            edEmail.setError("le champ ne peut pas etre vide");
            return false;
        } else if(!val.matches(emailPattern)){
            edEmail.requestFocus();
            edEmail.setError("veuille saisir un email valide");
            return false;
        }
        else {
            edEmail.setError(null);
            return true;
        }

    }
    private Boolean validatephone() {
        String val = edPhone.getText().toString();
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            edPhone.requestFocus();
            edPhone.setError("le champ ne peut pas etre vide");
            return false;
        } else if(val.length()!=8){
            edPhone.requestFocus();
            edPhone.setError("numero de telephone est de 8 chiffres");
            return false;
        }
        else {
            edPhone.setError(null);
            return true;
        }

    }
    private Boolean validatePassword() {
        String val = edPassw.getText().toString();
        //"^" : starting of the screen "$" : end of the screen
        //il faut que le mot de passe contient le suivant:

        /* a verifier
        String passwordVal ="^"+
                //"(?=.*[0-9])" +             //at least 1 digit
                //"(?=.*[a-z])" +             //at least 1 lower case letter
                //"(?=.*[A-Z])" +             //at least 1 upper case letter
                "(?=.*[a-zA-Z])"+           //any letter
                "(?=.*[@#$%^&+=])"+         //at least 1 special caracter
                //"(?=\\s+$)" +             //no white spaces 1
                "\\A\\w{4,20}\\z" +         //no white spaces 2
                ".{4,}" +                   //at least 4 characters
                "$";
        */
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            edPassw.requestFocus();
            edPassw.setError("Vous devez choisir un mot de passe");
            return false;
        }else if(val.length()<=6){
            edPassw.requestFocus();
            edPassw.setError("le mot de passe est trop court");
            return false;
        }
        else if(val.matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$")){
            edPassw.requestFocus();
            edPassw.setError("le mot de passe est trop faible");
            return false;
        }
        else {
            edPassw.setError(null);
            return true;
        }

    }


    private Boolean validateConfPassword() {
        String val = edConf.getText().toString();
        if (val.isEmpty()) {
            edConf.requestFocus();
            edConf.setError("Vous devez choisir un mot de passe");
            return false;
        }
        else if(!edConf.getText().toString().equals(edPassw.getText().toString())){
            edConf.requestFocus();
            edConf.setError("Les mots de passe ne correspondent pas");
            return false;
        }
        else {
            edConf.setError(null);
            return true;
        }

    }
//    **end validation functions***


    public void alertMsg(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Register.this);
        //defenir la titre de l'alert :
        builder.setTitle("Avertissement");
        builder.setMessage("Voulez-vous vraiment retourne a la page de connexion ?");

        //positionnez la bouton oui
        builder.setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //progress Bar
                /*ProgressBar.setVisibility(View.VISIBLE);*/

                //ditruire l'activiter Main
                dialogInterface.dismiss();

                /*pas important*/
                //quitter l'application
                //System.exit(0);

            }
        });
        //bouton non
        builder.setNegativeButton("Retour", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cacher le dialog
//                finishAffinity();
                finish();
            }
        });
        //afficher le dialog
        builder.show();
    }

    @Override
    public void onBackPressed() {
        alertMsg();
    }






}


