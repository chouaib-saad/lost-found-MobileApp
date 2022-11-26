package com.example.myobject3;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.List;

public class LostAdapter extends RecyclerView.Adapter <LostAdapter.ImageViewHolder> {
    private final Context mContext;
    private final List<LostClass> mUploads;
    private OnitemClickListener mListener;


    public LostAdapter(Context context,List<LostClass> uploads){
        this.mContext = context;
        this.mUploads = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.lost_found_posts,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        LostClass uploadCurrent = mUploads.get(position);
        holder.name.setText(uploadCurrent.getNom());
        holder.categorie.setText(uploadCurrent.getCateg());
        holder.nom_obj.setText(uploadCurrent.getNomObj());
        holder.location.setText(uploadCurrent.getEmplac());
        holder.description.setText(uploadCurrent.getDescription());
        Picasso.get()
                .load(uploadCurrent.getmImageUrl())
                .placeholder(R.drawable.selectim3)
                .fit()
                .centerCrop()
                .into(holder.obj_im);
//        we can use centre inside
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
    View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView name,categorie,nom_obj,location,description;
        public ImageView obj_im;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            categorie = itemView.findViewById(R.id.categorie);
            nom_obj = itemView.findViewById(R.id.nom_obj);
            location = itemView.findViewById(R.id.location);
            description = itemView.findViewById(R.id.description);
            obj_im = itemView.findViewById(R.id.obj_im);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                int position = getAbsoluteAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }


            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Modifier Votre Statut :");
            MenuItem modifier =   contextMenu.add(Menu.NONE,1,1,"Modifier");
            MenuItem supprime =   contextMenu.add(Menu.NONE,2,2,"Supprimer");

            modifier.setOnMenuItemClickListener(this);
            supprime.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener != null){
                int position = getAbsoluteAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {

                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
                }
            return false;
        }
    }

    public interface OnitemClickListener {
        void onItemClick(int position);
        void onWhatEverClick(int position);
        void onDeleteClick(int position);

    }

    public  void  setOnItemClickListener(OnitemClickListener listener){

        mListener = listener;
    }
}
