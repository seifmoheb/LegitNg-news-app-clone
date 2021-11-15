package com.app.legitngclone.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.legitngclone.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.commentsViewHolder>{

    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Images = new ArrayList<>();
    ArrayList<String> Dates = new ArrayList<>();
    ArrayList<String> Comments = new ArrayList<>();
    Context context;

    public commentsAdapter(ArrayList<String> names, ArrayList<String> images, ArrayList<String> dates, ArrayList<String> comments, Context context) {
        this.Names = names;
        this.Images = images;
        this.Dates = dates;
        this.Comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public commentsAdapter.commentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_item,parent,false);
        commentsAdapter.commentsViewHolder holder = new commentsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull commentsAdapter.commentsViewHolder holder, int position) {
        holder.name.setText(Names.get(position));
        holder.date.setText(Dates.get(position));
        holder.comment.setText(Comments.get(position));
        if (Images.get(position)== null) {
            holder.userImage.setImageResource(R.drawable.mainlogo);
        } else {
            Glide.with(context)
                    .load(Images.get(position))
                    .fitCenter()
                    .placeholder(R.color.lightGrayColor)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.userImage);
        }
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }

    public class commentsViewHolder extends RecyclerView.ViewHolder{
        TextView name,date,comment;
        ImageView userImage;
        public commentsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView);
            date = itemView.findViewById(R.id.dateTextView);
            comment = itemView.findViewById(R.id.commentTextView);
            userImage = itemView.findViewById(R.id.userImage);
        }
    }
}
