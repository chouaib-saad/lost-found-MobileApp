package com.example.myobject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Log extends AppCompatActivity {

    private Button btnemail;

    private FirebaseAuth mAuth;
    public   static boolean token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        btnphone=findViewById(R.id.btnphone);

        btnemail=findViewById(R.id.btnemail);

//        btnphone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Log.this, Register.class));
//            }
//
//
//
//        });
        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Log.this, LogEmail.class));

            }
        });

        if(!isConnected(Log.this)){
            NoConnectionMsg();
        }else{

            // authenticate the user
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            Intent intent =getIntent();
//            String response = intent.getStringExtra("response");
//            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            if(user != null){
//                //User is signed in
//                Intent intent = new Intent(Log.this,MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
        }


    } // ===========OnCreate End=========
















    private void goToUrl(String s) {
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse(s));
        startActivity(intent);
    }

    public void logoIm(View view) {
        Toast.makeText(getApplicationContext(), "Nous espérons que vous trouverez ce que vous cherchez", Toast.LENGTH_SHORT).show();
    }


    public void twitter(View view) {
        goToUrl("http://www.twitter.com");
    }

    public void facebook(View view) {
        goToUrl("http://www.facebook.com");
    }

    public void Linkedin(View view) {
        goToUrl("https://www.linkedin.com/");
    }

    public void exitApp(View view) {
        alertMsg();
    }

    @Override
    public void onBackPressed() { alertMsg(); }


    public void alertMsg(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Log.this);
        //defenir la titre de l'alert :
        builder.setTitle("Avertissement");
        builder.setMessage("Voulez-vous vraiment fermer l'application ?");

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
        builder.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cacher le dialog
                finishAffinity();
            }
        });
        //afficher le dialog
        builder.show();
    }



    // fct de message si la cnx ne pas etablir :
    private void NoConnectionMsg(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Log.this);
        builder.setTitle("Echec de connexion");
        builder.setMessage("Veuille vous connecter a l'internet pour continuer !");
        builder.setCancelable(false);
        builder.setPositiveButton("Connecter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //ouvrir les parametres wifi :
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                if(!isConnected(Log.this)){
                    NoConnectionMsg();
                }
                //ouvrir le parametres de donnes mobile :
                //startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                //retirer la bar progressive de l'activite mere..
//                ProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //startActivity(new Intent(getApplicationContext(),Login.class));
//                dialogInterface.dismiss();
//                ProgressBar.setVisibility(View.INVISIBLE);
                if(!isConnected(Log.this)){
                    NoConnectionMsg();
                }

            }
        });
        AlertDialog alertDialog = builder.create();
        builder.setCancelable(false);
        alertDialog.show();
    }



    private boolean isConnected(Log Log) {
        ConnectivityManager connectivityManager = (ConnectivityManager) Log.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return wifiConn != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected());
    }
}