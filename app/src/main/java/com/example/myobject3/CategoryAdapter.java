package com.example.myobject3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private final CategoryModel[] listdata;

    public static Intent intentP;

    public CategoryAdapter(CategoryModel[] listdata){

        this.listdata=listdata;


    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listItem=layoutInflater.inflate(R.layout.listview_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        final CategoryModel myListData=listdata[position];
        holder.textView.setText(listdata[position].getName());
        holder.imageView.setImageResource(listdata[position].getImage());
         holder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Tu as choisi le categorie de : "+ myListData.getName(),Toast.LENGTH_SHORT).show();

                intentP = new Intent(view.getContext(),TypeCatg.class);
                intentP.putExtra("type",myListData.getName());
                Intent intent = new Intent(view.getContext(),TypeCatg.class);

                view.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return listdata.length;


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public CardView cardView;
        public ViewHolder(View itemView){
            super(itemView);
            this.imageView=itemView.findViewById(R.id.imageView);
            this.textView=itemView.findViewById(R.id.textView);
            cardView=itemView.findViewById(R.id.LineaireLayout);


    }




    }
}
