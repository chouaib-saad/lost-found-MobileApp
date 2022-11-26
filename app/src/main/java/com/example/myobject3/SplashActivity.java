package com.example.myobject3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SplashActivity extends AppCompatActivity {

    //l'ecran suivant apres l'ecran d'attente DE 5S
    private static final int SPLASH_SCREEN=5000;
    //declaration de variables d'animation
    Animation topAnim,bottomAnim;
    //declaration des images utilise
    ImageView bgIm;
    TextView logo,slogan;

    //progress bar
    RelativeLayout ProgressBar;

    //closer Activity
    public static MainActivity ma;

    //

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //closer Activity
        //supprimer la bar de top
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //rappel Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //progress bar animation hook..
        ProgressBar = findViewById(R.id.ProgressBar);

        //hooks
        bgIm = findViewById(R.id.bgim);
        logo = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);

        //affecter lanimation aux composantes
        bgIm.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        //fonction d'attente avant l'ouvrir de l'activite suivant
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //une bar progresive d'attente
                ProgressBar.setVisibility(View.VISIBLE);

                //creation de nouveau activite a l'intereur de fonction d'attente
                //et debut de l'activite suivant (Login screen)
                //creation de l'activite
                Intent intent =new Intent(SplashActivity.this,Log.class);
                //demarrer la deuxieme activite normallement ::
                //*startActivity(intent);
                //finish() fonction pour supprimer l'activite de splash screen
                // pour que ne puisse ouvrir apres la fermeture de ScPrincipal
                //*finish();
                //demarrage animee :

//                =====
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(bgIm,"logo_image");
                pairs[1] = new Pair<View,String>(logo,"logo_text");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        },SPLASH_SCREEN);

    }

    }
