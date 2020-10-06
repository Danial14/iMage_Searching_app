package com.example.noman_000.flickr_app;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {
    private ImageView iMage;
    private TextView author;
    private TextView photoTitle;
    private TextView photoTags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        iMage = (ImageView) findViewById(R.id.photo_iMage);
        author = (TextView) findViewById(R.id.photo_author);
        photoTitle = (TextView) findViewById(R.id.photo_title);
        photoTags = (TextView) findViewById(R.id.photo_tags);
        activateToolBar(true);
        Intent intent = getIntent();
        if(intent != null){
            Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
            Toast.makeText(this, "intent called is not null", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "" + intent.getStringExtra("NUMBER"), Toast.LENGTH_LONG)
            .show();
            if(photo != null){
                author.setText(photo.getAuthor());
                Resources resources = getResources();
                photoTitle.setText(resources.getString(R.string.photo_title, photo.getTitle()));
                photoTags.setText(resources.getString(R.string.photo_tags, photo.getTags()));
                Picasso.with(this).load(photo.getLink()).
                        placeholder(R.drawable.placeholder).error(R.drawable.error).
                        into(iMage);
            }
        }
    }

}
