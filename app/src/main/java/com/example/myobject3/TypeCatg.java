package com.example.myobject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class TypeCatg extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private Button btnChoose, btnCapture, btnInsert;
    private EditText  edCategory, edObjectName, edLocation, edDecription;
    TextView edName;
    private RadioGroup RG;
    private RadioButton rb1, rb2;
    private ImageView image;
    public  boolean photoChanged = false;


    //Firebase Firestore references
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    public String name;

    //for image
    private static final int PICK_IMAGE_REQUEST=1;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    public String photoUrl;

    //firebase implimentation
//    FirebaseDatabase rootNode; //el table lkbir
//    DatabaseReference reference;

    //other use the last solution
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

//    FirebaseOptions options = new FirebaseOptions.Builder().setDatabaseUrl("https://fir-uploadexample-ffeee-default-rtdb.firebaseio.com/")
//            .setProjectId("fir-uploadexample-ffeee")
//            .setApplicationId("com.example.myobject3")
////            .setApiKey("")
//            // setStorageBucket(...)
//            .build();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_catg);


        btnChoose = findViewById(R.id.btnChoose);
        btnCapture = findViewById(R.id.btnCapture);
        btnInsert = findViewById(R.id.btnInsert);
        edName = findViewById(R.id.edName);
        edCategory = findViewById(R.id.edCategory);
        edObjectName = findViewById(R.id.edObjectName);
        edLocation = findViewById(R.id.edLocation);
        edDecription = findViewById(R.id.edDescription);
        RG = findViewById(R.id.RG);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        image = findViewById(R.id.imageView);


//        FirebaseOptions options = new FirebaseOptions.Builder().setProjectId("fir-uploadexample-ffeee").setApplicationId(BuildConfig.APPLICATION_ID).build();

        categorySelected();
        setNameAutomattically();


        btnCapture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (!checkCameraPermission()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestCameraPermission();
                    }
                } else {
                    pickImageFromCamera();

                }
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (!checkStoragePermission()) {
                    requestStoragePermission();
                } else {
                    pickImageFromGallery();
                }
            }
        });








//        ========================================
//        get the name from firestore ref

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                name = value.getString("Name");
                edName.setText(name);

            }
        });

//        =================================================


    } //onCreate methode end














    private void pickImageFromGallery() {

        ImagePicker.with(TypeCatg.this)
                .galleryOnly()
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
//        photoChanged = true;
    }

    private void pickImageFromCamera() {

        ImagePicker.with(TypeCatg.this)
                .cameraOnly()
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
//        photoChanged = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {

        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return res1 && res2;
    }


    private void categorySelected() {
        String typeName = CategoryAdapter.intentP.getStringExtra("type");
        edCategory.setText(typeName);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        photoChanged = false;
        if(resultCode==RESULT_OK && data != null && data.getData() != null){
            photoChanged = true;
            mImageUri = data.getData();
            image.setImageURI(mImageUri);
        }else{
            photoChanged = false;
        }
    }

    public void imagePick(View view) {

        if(!photoChanged){
            Toast.makeText(getApplicationContext(), "Choisir une image ..", Toast.LENGTH_SHORT).show();
        }
    }


    public void checkButton(View view) {
        //radio group
        int radioId = RG.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        Toast.makeText(getApplicationContext(), "tu choisir : " + radioButton.getText(), Toast.LENGTH_SHORT).show();
//        if(radioButton.getText()==rb1.getText()){
//            Toast.makeText(getApplicationContext(), rb1.getText(), Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(getApplicationContext(), rb2.getText(), Toast.LENGTH_SHORT).show();
//        }
    }


    private boolean isR1checked() {
        //radio group
        int radioId = RG.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        return radioButton.getText() == rb1.getText();
    }


    private boolean isR2checked() {
        //radio group
        int radioId = RG.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        return radioButton.getText() == rb2.getText();
    }


    private Boolean validateUserName() {
        String val = edName.getText().toString();
        /*en peut utiliser "\\A\\w{4,20}\\z" (touts le caract a lexep de spaces)
        au lieu de "(?=\\s+$)"
        pour eliminer les espaces blanc */
        //String noWhiteSpace ="(?=\\s+$)";
//          String noWhiteSpace ="\\A\\w{4,20}\\z";
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            edName.requestFocus();
            edName.setError("le champ ne peut pas etre vide");
            edName.getBackground().setColorFilter(getResources().getColor(R.color.themeColor), PorterDuff.Mode.SRC_ATOP);
            return false;
        } else if (val.length() >= 15) {
            edName.requestFocus();
            edName.setError("le nom est trop long");
            return false;
        }else if(val.length() < 5){
            edName.requestFocus();
            edName.setError("le nom est trop courte");
            return false;
        }
        else {
            edName.setError(null);
//            edName.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateCategorie() {
        String[] categories = {"Laptops", "Phones", "Keys", "Cards","Electronics","Money","Document","Garment","Glasses","Bags",
                "Shoes","Cosmetics","Instrument","Jewellery","Pets","Watches","Other"};
        String val = edCategory.getText().toString();
        if (val.isEmpty()) {
            edCategory.requestFocus();
            edCategory.setError("champ ne peut pas etre vide");
            edCategory.getBackground().setColorFilter(getResources().getColor(R.color.themeColor), PorterDuff.Mode.SRC_ATOP);
            return false;
        } else if (!categExist(categories,edCategory)) {
            edCategory.requestFocus();
            edCategory.setError("cette categorie ne pas existe !");
            return false;
        } else {
            edCategory.setError(null);
//            edName.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateNomObj() {
        String val = edObjectName.getText().toString();
        /*en peut utiliser "\\A\\w{4,20}\\z" (touts le caract a lexep de spaces)
        au lieu de "(?=\\s+$)"
        pour eliminer les espaces blanc */
        //String noWhiteSpace ="(?=\\s+$)";
//          String noWhiteSpace ="\\A\\w{4,20}\\z";
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            edObjectName.requestFocus();
            edObjectName.setError("le champ ne peut pas etre vide");
            edObjectName.getBackground().setColorFilter(getResources().getColor(R.color.themeColor), PorterDuff.Mode.SRC_ATOP);
            return false;
        } else if (val.length() >= 15) {
            edObjectName.requestFocus();
            edObjectName.setError("le nom de l'objet est trop long");
            return false;
        }else if(val.length() < 5){
            edObjectName.requestFocus();
            edObjectName.setError("le nom de l'objet est  trop courte");
            return false;
        }
        else {
            edObjectName.setError(null);
//            edObjectName.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validateLocation() {
        String val = edLocation.getText().toString();
        /*en peut utiliser "\\A\\w{4,20}\\z" (touts le caract a lexep de spaces)
        au lieu de "(?=\\s+$)"
        pour eliminer les espaces blanc */
        //String noWhiteSpace ="(?=\\s+$)";
//          String noWhiteSpace ="\\A\\w{4,20}\\z";
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            edLocation.requestFocus();
            edLocation.setError("le champ ne peut pas etre vide");
            edLocation.getBackground().setColorFilter(getResources().getColor(R.color.themeColor), PorterDuff.Mode.SRC_ATOP);
            return false;
        } else if (val.length() >= 15) {
            edLocation.requestFocus();
            edLocation.setError("le nom de la location est trop long");
            return false;
        } else if (val.length() < 5) {
            edLocation.requestFocus();
            edLocation.setError("le nom de la location est  trop courte");
            return false;
        } else {
            edLocation.setError(null);
//            edObjectName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDescription() {
        String val = edDecription.getText().toString();
        /*en peut utiliser "\\A\\w{4,20}\\z" (touts le caract a lexep de spaces)
        au lieu de "(?=\\s+$)"
        pour eliminer les espaces blanc */
        //String noWhiteSpace ="(?=\\s+$)";
//          String noWhiteSpace ="\\A\\w{4,20}\\z";
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            edDecription.requestFocus();
            edDecription.setError("le champ ne peut pas etre vide");
            edDecription.getBackground().setColorFilter(getResources().getColor(R.color.themeColor), PorterDuff.Mode.SRC_ATOP);
            return false;
        } else if (val.length() >= 200) {
            edDecription.requestFocus();
            edDecription.setError("la description est trop long");
            return false;
        } else if (val.length() < 5) {
            edDecription.requestFocus();
            edDecription.setError("la description est  trop courte");
            return false;
        } else {
            edDecription.setError(null);
//            edObjectName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhotoSelected() {
        if(!isphotoChanged()){

            Toast.makeText(getApplicationContext(), "Veuillez sélectionner une image..", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }




    //envoyer les donnes avec le bouton insert objet
    public void sendData(View view) {
        if (!validateUserName()|!validateCategorie()|!validateNomObj()|!validateLocation()|!validateDescription()) {
//            ProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"verifier vos champs pour continuer", Toast.LENGTH_SHORT).show();
            return;
        } else if(!validatePhotoSelected()){
            return ;
        }
        else{

//            photoChanged = false;




            //recevoir de touts les valeurs de l'inscription de user en "String"
//            String nom = edName.getText().toString();
//            String categ = edCategory.getText().toString();
//            String nomObj = edObjectName.getText().toString();
//            String emplac = edLocation.getText().toString();
//            String description = edDecription.getText().toString();
//
            if(isR2checked()) {
//                //FirebaseDatabase rootNode; //el table lkbir
//                //DatabaseReference reference;
//                //rootNode = FirebaseDatabase.getInstance();
//                //reference = rootNode.getReference("lost");
//
//                //LostClass LostClass = new LostClass(nom, categ, nomObj, emplac, description);
//                //reference.child(nom).setValue(LostClass);
//
                //other way methode 2
                mStorageRef = FirebaseStorage.getInstance().getReference("lost");
                mDatabaseRef =FirebaseDatabase.getInstance().getReference("lost");
//
//
//
//
            }else if(isR1checked()){
////                rootNode = FirebaseDatabase.getInstance();
////                reference = rootNode.getReference("found");
//
////                FoundClass FoundClass = new FoundClass(nom, categ, nomObj, emplac, description);
////                reference.child(nom).setValue(FoundClass);
//
                //other way methode 2
                mStorageRef = FirebaseStorage.getInstance().getReference("found");
                mDatabaseRef =FirebaseDatabase.getInstance().getReference("found");
//
//
            }

//            uploadFile();
//            Toast.makeText(getApplicationContext(), "Votre statut a été enregistré avec succès", Toast.LENGTH_LONG).show();
            SavingPostAlert();
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
        }
    }


    public void alertMsg(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TypeCatg.this);
        //defenir la titre de l'alert :
        builder.setTitle("Avertissement");
        builder.setMessage("Selectionne une autre categorie ?");

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
//                photoChanged = false;
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



    private boolean categExist(String[] tab,EditText category){
        int exist = 0;
        for (int i=0; i < tab.length; i++)
        {
            if(tab[i].equals(category.getText().toString()))
            {
                exist++;
            }
        }
        return exist > 0;
    }


    private boolean isphotoChanged(){
        return photoChanged;

//        image.getId()!=R.id.imageView
    }

    private void uploadFile(){
        if(mImageUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+
                    "."+getFileExtension(getApplicationContext(),mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String nom = edName.getText().toString();
                            String categ = edCategory.getText().toString();
                            String nomObj = edObjectName.getText().toString();
                            String emplac = edLocation.getText().toString();
                            String description = edDecription.getText().toString();

//                            //wait before set the progress bar to 0
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mProgressBar.setProgress(0);
//                                }
//                            },5000);
//                            Toast.makeText(TypeCatg.this, "Image Inserte avec succes", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Votre statut a été enregistré avec succès", Toast.LENGTH_LONG).show();



                            //methode to get image url
//                            ===========================================================
                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUrl = uri.toString();
                                            //createNewPost(imageUrl);

                                            if(isR2checked()){
                                                LostClass LostClass = new LostClass(nom, categ, nomObj, emplac,description, imageUrl);
                                                String uploadId =   mDatabaseRef.push().getKey();
                                                mDatabaseRef.child(uploadId).setValue(LostClass);


                                            }else if(isR1checked()){

                                                FoundClass FoundClass = new FoundClass(nom, categ, nomObj, emplac, description, imageUrl);
                                                String uploadId =   mDatabaseRef.push().getKey();
                                                mDatabaseRef.child(uploadId).setValue(FoundClass);

                                            }
                                        }
                                    });
                                }
                            }
//                            ==============================================================




                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TypeCatg.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0* snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                    mProgressBar.setProgress((int) progress);
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "pas d'image ete selectionne", Toast.LENGTH_SHORT).show();
        }
    }

    //get the extention of the image
//    private String getFileExtension(Uri uri){
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }


    //sucsesfull solution
    public static String getFileExtension(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }



    public void SavingPostAlert(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TypeCatg.this);
        //defenir la titre de l'alert :
        builder.setTitle("VALIDITE DE VOTRE STATUT");
        builder.setMessage("Cette statut restera un mois puis supprimee par les administrateurs ");

        //positionnez la bouton oui
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //progress Bar
                /*ProgressBar.setVisibility(View.VISIBLE);*/

                uploadFile();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

                //ditruire l'activiter Main
//                dialogInterface.dismiss();

                /*pas important*/
                //quitter l'application
                //System.exit(0);

            }
        });

        //cant dismiss the alert dialog
        builder.setCancelable(false);

        //afficher le dialog
        builder.show();
    }



    private void setNameAutomattically(){
        edName.setText(name);
    }









}  //class end

