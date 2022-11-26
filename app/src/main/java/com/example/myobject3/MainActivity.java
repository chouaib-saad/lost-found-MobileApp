package com.example.myobject3;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myobject3.ui.help.HelpFragment;
import com.example.myobject3.ui.home.HomeFragment;
import com.example.myobject3.ui.profil.ProfilFragment;
import com.example.myobject3.ui.profil.ProfilViewModel;
import com.example.myobject3.ui.slideshow.SlideshowFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myobject3.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private FirebaseAuth mAuth;
    private final String deconnexion="no";
    public static Intent intentDec;
    DrawerLayout drawer;
    NavigationView navigationView;

    //user reset password in local
//    FirebaseAuth fAuth;
//    FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //user reset password
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contactez nous..", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"imenothm2022@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "J'ai un problème");
                intent.putExtra(Intent.EXTRA_TEXT, "description de votre probleme ici..");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(),"Gmail App is not installed", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        navController = Navigation.findNavController(this,R.id.nav_profil_frag);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new HomeFragment()).commit();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(),"vous êtes déjà sur cette page",Toast.LENGTH_LONG).show();
                        break;
                }
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        break;
                }
                switch (item.getItemId()) {
                    case R.id.nav_help:
                        Intent intent = new Intent (Intent.ACTION_SEND);
                        intent.setType("message/rfc822");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"imenothm2022@gmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "J'ai un problème");
                        intent.putExtra(Intent.EXTRA_TEXT, "description de votre probleme ici..");
                        intent.setPackage("com.google.android.gm");
                        if (intent.resolveActivity(getPackageManager())!=null)
                            startActivity(intent);
                        else
                            Toast.makeText(getApplicationContext(),"Gmail App is not installed", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        break;

                }
                switch (item.getItemId()) {
                    case R.id.nav_paschange:
                        resetPasswordFunction();
                        break;
                }
                switch (item.getItemId()) {
                    case R.id.nav_cond:
                        startActivity(new Intent(getApplicationContext(),ConditionsUtisations.class));
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.dcnx:
                alertMsg();
                break;
            case R.id.acc_obj:
                startActivity(new Intent(getApplicationContext(),SeePosts.class));
                break;
            default:
                //nothing to do..
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//
//            case R.id.nav_gallery:
//                startActivity(new Intent(getApplicationContext(),TypeCatg.class));
//                break;
//            case R.id.nav_setting:
//                startActivity(new Intent(getApplicationContext(),TypeCatg.class));
//                break;
//            case R.id.nav_profil:
//                startActivity(new Intent(getApplicationContext(),TypeCatg.class));
//                break;
//            case R.id.nav_slideshow:
//                startActivity(new Intent(getApplicationContext(),TypeCatg.class));
//                break;
//            default:
//                break;
//
//        }
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    public void alertMsg(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        //defenir la titre de l'alert :
        builder.setTitle("Deconnexion");
        builder.setMessage("Deconnecte ?");

        //positionnez la bouton oui
        builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
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
        builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cacher le dialog
//                finishAffinity();
                LogEmail.ed1.setText("");
                LogEmail.ed2.setText("");

//                Intent intent = new Intent(MainActivity.this,LogEmail.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK); //user cant go back
//                startActivity(intent);
                logout();
            }
        });
        //afficher le dialog
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            alertMsg();
        }
    }


    private void logout(){

//        FirebaseAuth fAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(getApplicationContext(),Log.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        fAuth.signOut();
        startActivity(intent);
        finish();



//        intentDec = new Intent(getApplicationContext(),Log.class);
//        intentDec.putExtra("response",deconnexion);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        mAuth.signOut();
//        startActivity(intentDec);




    }


    private void resetPasswordFunction(){
        final EditText resetPassword = new EditText(getApplicationContext());
        final  androidx.appcompat.app.AlertDialog.Builder passwordResetDialog = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        passwordResetDialog.setTitle("Modifier le mot de passe ?");
        passwordResetDialog.setMessage("Entree un nouveau mot de passe : ");
        passwordResetDialog.setView(resetPassword);


        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
          String newPassword = resetPassword.getText().toString();
          user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void unused) {

                  Toast.makeText(getApplicationContext(), "mot de passe modifier avec succes", Toast.LENGTH_SHORT).show();
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {

                  Toast.makeText(getApplicationContext(), "echec veuillez réessayer plus tard", Toast.LENGTH_SHORT).show();
              }
          });


            }
        });

        //bouton non
        passwordResetDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                dialogInterface.dismiss();

            }
        });
        //afficher le dialog
        passwordResetDialog.create().show();



    }



}  //end of class