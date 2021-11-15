package com.app.legitngclone.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.app.legitngclone.R;
import com.app.legitngclone.ui.bookmarks.BookmarksViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    String title,urlToImage,description,content,author,publishedAt,url;
    TextView titleTextView,descriptionTextView,contentTextView,content2;
    ImageView imageView;
    boolean bookmarkStatus;
    Button bookmark,bookmarked,returnButton,comment;
    BookmarksViewModel bookmarksViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        bookmarksViewModel =
                ViewModelProviders.of( DetailsActivity.this).get(BookmarksViewModel.class);
        bookmark = findViewById(R.id.bookmark);
        bookmarked = findViewById(R.id.bookmarked);
        returnButton = findViewById(R.id.returnButton);
        imageView = findViewById(R.id.toolbar_imageView);
        titleTextView = findViewById(R.id.title);
        descriptionTextView = findViewById(R.id.description);
        contentTextView = findViewById(R.id.content);
        content2 = findViewById(R.id.content2);
        comment = findViewById(R.id.commentButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            title = bundle.getString("title");
            urlToImage = bundle.getString("urlToImage");
            description = bundle.getString("description");
            content = bundle.getString("content");
            author = bundle.getString("author");
            url = bundle.getString("url");
            publishedAt = bundle.getString("publishedAt");
            if(bundle.getString("bookmarkStatus").equals("bookmarked")){
                bookmarkStatus = true;
            }
            else{
                bookmarkStatus = false;
            }
        }
        if(!bookmarkStatus){
            bookmark.setVisibility(View.VISIBLE);
            bookmarked.setVisibility(View.INVISIBLE);
        }else{
            bookmark.setVisibility(View.INVISIBLE);
            bookmarked.setVisibility(View.VISIBLE);
        }
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this, "Bookmarked", Toast.LENGTH_SHORT).show();
                bookmarksViewModel.insert(title, description, content, urlToImage, author, publishedAt, url);
                bookmark.setVisibility(View.INVISIBLE);
                bookmarked.setVisibility(View.VISIBLE);
            }
        });
        bookmarked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this, "Bookmark removed", Toast.LENGTH_SHORT).show();
                bookmarksViewModel.delete(title);
                bookmark.setVisibility(View.VISIBLE);
                bookmarked.setVisibility(View.INVISIBLE);
            }
        });
        if(urlToImage!=null) {
            Glide.with(DetailsActivity.this)
                    .load(urlToImage)
                    .fitCenter()
                    .placeholder(R.color.lightGrayColor)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontTransform().into(imageView);
        }
        final SharedPreferences sharedPreferences = getSharedPreferences("PREFS",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getBoolean("smallButton",false)){
            descriptionTextView.setTextSize(13);
            contentTextView.setTextSize(13);
            content2.setTextSize(13);
        }else if(sharedPreferences.getBoolean("normalButton",true)){
            descriptionTextView.setTextSize(18);
            contentTextView.setTextSize(18);
            content2.setTextSize(18);
        }else if(sharedPreferences.getBoolean("largeButton",false)){
            descriptionTextView.setTextSize(22);
            contentTextView.setTextSize(22);
            content2.setTextSize(22);
        }
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        contentTextView.setText(content);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = title+"/n"+url;
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Check this out");
                sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(sharingIntent,"Share via"));

            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this,commentActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
    }
}