package com.example.shaoo.blooddonationapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewHolder>{

    List <CardData> data;
    Context context;
    RVAdapter(List<CardData> data)
    {
        this.data = data;
    }
    RVAdapter(List<CardData> data, Context context){this.data = data; this.context = context;}

    public void update_data(List<CardData> Data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_screen_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.title.setText(data.get(position).title);
        holder.description.setText(data.get(position).description);
        //  Toast.makeText(context,data.get(position).photoID, Toast.LENGTH_SHORT).show();
        Glide.with(context).load(data.get(position).photoID).into(holder.picture);
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else
            return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView description;
        ImageView picture;

        CardViewHolder(View itemView) {
            super(itemView);
            //cv = (CardView)itemView.findViewById(R.id.home_screen_card);
            title = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_content);
            picture = (ImageView) itemView.findViewById(R.id.card_image);
        }
    }

}
