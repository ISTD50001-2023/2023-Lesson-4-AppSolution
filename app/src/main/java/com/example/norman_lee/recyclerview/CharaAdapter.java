package com.example.norman_lee.recyclerview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CharaAdapter extends RecyclerView.Adapter<CharaAdapter.CharaViewHolder>{

    Context context;
    LayoutInflater mInflater;
    DataSource dataSource;

    //TODO 11.3 Complete the constructor to initialize the DataSource instance variable
    /** Not so good design, because Adapter is tightly coupled to a specific concrete class */
    CharaAdapter(Context context, DataSource dataSource){
        this.dataSource = dataSource;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    //TODO 11.5 the layout of each Card is inflated and used to instantiate CharaViewHolder (no coding)
    @NonNull
    @Override
    public CharaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.pokemon, viewGroup, false);
        return new CharaViewHolder(itemView);
    }

    //TODO 11.6 the data at position i is extracted and placed on the i-th card
    @Override
    public void onBindViewHolder(@NonNull CharaViewHolder charaViewHolder, int i) {
        String name = dataSource.getName(i);
        Bitmap bitmap = dataSource.getImage(i);
        charaViewHolder.textViewName.setText(name);
        charaViewHolder.imageViewChara.setImageBitmap(bitmap);
    }

    //TODO 11.7 the total number of data points must be returned here
    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }

    //TODO 11.4 complete the constructor to initialize the instance variables
    static class CharaViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewChara;
        TextView textViewName;

        CharaViewHolder(View view){
            super(view);
            imageViewChara = view.findViewById(R.id.cardViewImage);
            textViewName = view.findViewById(R.id.cardViewTextName);
        }

    }


}
