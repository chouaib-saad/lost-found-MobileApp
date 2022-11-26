package com.example.myobject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogEmail extends AppCompatActivity {
    public static EditText ed1,ed2;
    private Button btnReset,btnReg,btnLogin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_email);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth=FirebaseAuth.getInstance();
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        progressBar = findViewById(R.id.progressBar);
        btnReset=findViewById(R.id.btnReset);
        btnReg=findViewById(R.id.btnReg);
        btnLogin=findViewById(R.id.btnLogin);


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPwd.class));
            }


        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }

        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = ed1.getText().toString();
                String Password = ed2.getText().toString();

                if (Email.isEmpty()) {
                    Toast.makeText(LogEmail.this, "Veuillez saisir un email", Toast.LENGTH_LONG).show();                    return;
                }
                if (Password.isEmpty()) {
                    Toast.makeText(LogEmail.this, "Veuillez entrer le mot de passe", Toast.LENGTH_LONG).show();
                    return;
                }   if (Email.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(LogEmail.this, "remplir tous les champs", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                // authenticate the user
                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogEmail.this, "connecté avec succes", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            progressBar.setVisibility(View.GONE);

                        }else {
                            Toast.makeText(LogEmail.this, "Erreur de connexion : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogEmail.this, ResetPwd.class));
            }
        });


    } //onCreate Methode end




    public void alertMsg(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LogEmail.this);
        //defenir la titre de l'alert :
        builder.setTitle("Avertissement");
        builder.setMessage("Voulez-vous vraiment retourne a la page d'accuil ?");

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
        alertMsg(); }
}