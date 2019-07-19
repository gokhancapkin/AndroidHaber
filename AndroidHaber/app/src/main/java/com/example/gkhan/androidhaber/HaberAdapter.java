package com.example.gkhan.androidhaber;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by tchzafer on 21/03/2018.
 */

public class HaberAdapter extends RecyclerView.Adapter<HaberAdapter.MyViewHolder> {

    ArrayList<HaberList> mHaberList;
    LayoutInflater inflater;
    private String[] tur = {"Gündem","Spor","Eğlence","Ekonomi"};
    private Spinner sp;
    static String resultq;
    public HaberAdapter(Context context, ArrayList<HaberList> habers) {
        inflater = LayoutInflater.from(context);
        this.mHaberList = habers;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.haber_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HaberList selectedHaber = mHaberList.get(position);
        holder.setData(selectedHaber, position);

    }

    @Override
    public int getItemCount() {
        return mHaberList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView HaberHeader, HaberContent , ViewNum, Like;
        ImageView HaberImage, deleteproduct;

        public MyViewHolder(final View itemView) {
            super(itemView);

            HaberHeader = (TextView) itemView.findViewById(R.id.HaberHeader);
            HaberContent = (TextView) itemView.findViewById(R.id.HaberContent);
            HaberImage = (ImageView) itemView.findViewById(R.id.HaberImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), Haber.class);
                    intent.putExtra("pos",i);
                    v.getContext().startActivity(intent);

                }
            });

        }

        public void setData(HaberList selectedHaber, int position) {

            this.HaberHeader.setText(selectedHaber.getHaberHeader());
            this.HaberContent.setText(selectedHaber.getHaberContent());
            this.HaberImage.setImageResource(selectedHaber.getImageID());
        }
        @Override
        public void onClick(View v) {

        }



    }
}