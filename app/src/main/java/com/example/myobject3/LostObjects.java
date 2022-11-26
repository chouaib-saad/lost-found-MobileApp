package com.example.myobject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class LostObjects extends AppCompatActivity implements LostAdapter.OnitemClickListener {
    private RecyclerView mRecyclerView;
    private LostAdapter mAdapter;

    //progress bar
    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseReference;
    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;

    private List<LostClass> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_objects);


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //progress bar hook
        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new LostAdapter(LostObjects.this,mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(LostObjects.this);

        mStorage = FirebaseStorage.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("lost");

        mDBListener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mUploads.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    LostClass upload = postSnapshot.getValue(LostClass.class);
                    upload.setmKey(postSnapshot.getKey());
                    mUploads.add(upload);

                    mAdapter.notifyDataSetChanged();

                }



                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);


            }
        });



    } //onCreate end



    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"normal click at position :"+position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWhatEverClick(int position) {

        Toast.makeText(this,"whatever click at position :"+position,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDeleteClick(int position) {
        LostClass selectedItem = mUploads.get(position);
        String selectedKey = selectedItem.getmKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                mDatabaseReference.child(selectedKey).removeValue();
                Toast.makeText(getApplicationContext(), "Statut supprimee", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mDBListener);
    }



} //class end