package com.example.myobject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SeePosts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_posts);
    } //end onCreate Methode



    //open lost object activity
    public void foundObj(View view) {
        startActivity(new Intent(getApplicationContext(),FoundObjects.class));
    }

    //open lost object activity
    public void lostObj(View view) {
        startActivity(new Intent(getApplicationContext(),LostObjects.class));
    }
} //end class