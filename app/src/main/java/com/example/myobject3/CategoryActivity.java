package com.example.myobject3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    EditText inputSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        CategoryModel[] myListData =new CategoryModel[]{
                new CategoryModel("Laptops",R.drawable.laptops),
                new CategoryModel("Phones",R.drawable.phones),
                new CategoryModel("Keys",R.drawable.keys),
                new CategoryModel("Cards",R.drawable.cards),
                new CategoryModel("Electronics",R.drawable.electronics),
                new CategoryModel("Money",R.drawable.money),
                new CategoryModel("Document",R.drawable.documents),
                new CategoryModel("Garment",R.drawable.garments),
                new CategoryModel("Glasses",R.drawable.sun),
                new CategoryModel("Bags",R.drawable.bags),
                new CategoryModel("Shoes",R.drawable.shoes),
                new CategoryModel("Cosmetics",R.drawable.cosmetiques),
                new CategoryModel("Instrument",R.drawable.instruments),
                new CategoryModel("Jewellery",R.drawable.jewellery),
                new CategoryModel("Pets",R.drawable.pets),
                new CategoryModel("Watches",R.drawable.watches),
                new CategoryModel("Other",R.drawable.other)};


        inputSearch=findViewById(R.id.inputSearch);



        RecyclerView recyclerView=findViewById(R.id.recyclerViewy);
        CategoryAdapter adapter=new CategoryAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);








        } //onCreate end






    //for searchInput










    public void alertMsg(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CategoryActivity.this);
        //defenir la titre de l'alert :
        builder.setTitle("Avertissement");
        builder.setMessage("Retourne a la page d'accuil ?");

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
