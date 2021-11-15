package com.app.legitngclone.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.app.legitngclone.R;
import com.app.legitngclone.data.PostInterface;
import com.app.legitngclone.pojo.PostModel;
import com.app.legitngclone.ui.bookmarks.BookmarksViewModel;
import com.app.legitngclone.ui.home.onef.OneFragmentViewModel;
import com.app.legitngclone.ui.settings.SettingsFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    public void setPostsList(List<PostModel> postsList) {
        for (int i = 0; i < postsList.size(); i++)
            this.postsList.add(postsList.get(i));
        notifyDataSetChanged();
    }

    private List<PostModel> postsList = new ArrayList<>();
    private LiveData<List<SavedPost>> savedPostsList = null;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    Context mContext;

    @NonNull
    @Override
    public PostsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsAdapter.PostViewHolder holder, final int position) {
        savedPostsList = holder.bookmarksViewModel.getAllPosts();
        savedPostsList.observe((LifecycleOwner) getmContext(), new Observer<List<SavedPost>>() {
            @Override
            public void onChanged(List<SavedPost> savedPosts) {
                for (int i = 0; i < savedPosts.size(); i++) {
                    if (savedPosts.get(i).getTitle().equals(postsList.get(position).getTitle())) {
                        holder.bookmarkButton.setVisibility(View.INVISIBLE);
                        holder.bookmarkedButton.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });
        holder.titleTextView.setText(postsList.get(position).getTitle());
        String[] dateAndTime = postsList.get(position).getPublishedAt().split("T");
        String[] date = dateAndTime[0].split("-");
        String[] time = dateAndTime[1].split(":");
        if (date[1].equals("01")) {
            holder.dateTextView.setText("Jan " + date[2] + ", " + time[0] + ":" + time[1]);
        } else if (date[1].equals("02")) {
            holder.dateTextView.setText("Feb " + date[2] + ", " + time[0] + ":" + time[1]);

        } else if (date[1].equals("03")) {
            holder.dateTextView.setText("Apr " + date[2] + ", " + time[0] + ":" + time[1]);

        } else if (date[1].equals("04")) {
            holder.dateTextView.setText("Mar " + date[2] + ", " + time[0] + ":" + time[1]);
        } else if (date[1].equals("05")) {
            holder.dateTextView.setText("May " + date[2] + ", " + time[0] + ":" + time[1]);
        } else if (date[1].equals("06")) {
            holder.dateTextView.setText("Jun " + date[2] + ", " + time[0] + ":" + time[1]);
        } else if (date[1].equals("07")) {
            holder.dateTextView.setText("Jul " + date[2] + ", " + time[0] + ":" + time[1]);
        } else if (date[1].equals("08")) {
            holder.dateTextView.setText("Aug " + date[2] + ", " + time[0] + ":" + time[1]);
        } else if (date[1].equals("09")) {
            holder.dateTextView.setText("Sep " + date[2] + ", " + time[0] + ":" + time[1]);
        } else if (date[1].equals("10")) {
            holder.dateTextView.setText("Oct " + date[2] + ", " + time[0] + ":" + time[1]);
        } else if (date[1].equals("11")) {
            holder.dateTextView.setText("Nov " + date[2] + ", " + time[0] + ":" + time[1]);
        } else if (date[1].equals("12")) {
            holder.dateTextView.setText("Dec " + date[2] + ", " + time[0] + ":" + time[1]);
        }

        final SharedPreferences sharedPreferences = getmContext().getSharedPreferences("PREFS",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getBoolean("images_mode",false)){
            holder.postImageView.setVisibility(View.GONE);
        }else{
            holder.postImageView.setVisibility(View.VISIBLE);
        }
        if (postsList.get(position).getUrlToImage() == null) {
            holder.postImageView.setImageResource(R.drawable.mainlogo);
        } else {
            Glide.with(getmContext())
                    .load(postsList.get(position).getUrlToImage())
                    .fitCenter()
                    .placeholder(R.color.lightGrayColor)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontTransform()
                    .into(holder.postImageView);
        }
        holder.bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Bookmarked", Toast.LENGTH_SHORT).show();
                holder.bookmarksViewModel.insert(postsList.get(position).getTitle(), postsList.get(position).getDescription(), postsList.get(position).getContent(), postsList.get(position).getUrlToImage(), postsList.get(position).getAuthor(), postsList.get(position).getPublishedAt(), postsList.get(position).getUrl());
                holder.bookmarkButton.setVisibility(View.INVISIBLE);
                holder.bookmarkedButton.setVisibility(View.VISIBLE);
            }
        });
        holder.bookmarkedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Bookmark removed", Toast.LENGTH_SHORT).show();
                holder.bookmarksViewModel.delete(postsList.get(position).getTitle());
                holder.bookmarkButton.setVisibility(View.VISIBLE);
                holder.bookmarkedButton.setVisibility(View.INVISIBLE);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DetailsActivity.class);
                intent.putExtra("title",postsList.get(position).getTitle());
                intent.putExtra("description",postsList.get(position).getDescription());
                intent.putExtra("content",postsList.get(position).getContent());
                intent.putExtra("urlToImage",postsList.get(position).getUrlToImage());
                intent.putExtra("url",postsList.get(position).getUrl());
                intent.putExtra("publishedAt",postsList.get(position).getPublishedAt());
                intent.putExtra("author",postsList.get(position).getAuthor());
                if(holder.bookmarkButton.getVisibility() == View.VISIBLE){
                    intent.putExtra("bookmarkStatus","not bookmarked");
                }else{
                    intent.putExtra("bookmarkStatus","bookmarked");
                }
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, dateTextView, seenTextView;
        ImageView postImageView;
        Button bookmarkButton, bookmarkedButton;
        BookmarksViewModel bookmarksViewModel;
        LinearLayout linearLayout;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            seenTextView = itemView.findViewById(R.id.seenTextView);
            postImageView = itemView.findViewById(R.id.postImageView);
            bookmarkButton = itemView.findViewById(R.id.InActive);
            bookmarkedButton = itemView.findViewById(R.id.Active);
            bookmarksViewModel =
                    ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(BookmarksViewModel.class);
            linearLayout = itemView.findViewById(R.id.list_item);

        }
    }
}
