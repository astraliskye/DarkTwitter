package com.codepath.apps.restclienttemplate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity
{
    ImageView _ivProfileImage;
    TextView _tvName;
    TextView _tvHandle;
    TextView _tvContent;
    TextView _tvTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        _ivProfileImage = findViewById(R.id.ivProfileImage);
        _tvName = findViewById(R.id.tvName);
        _tvHandle = findViewById(R.id.tvHandle);
        _tvContent = findViewById(R.id.tvContent);
        _tvTimestamp = findViewById(R.id.tvTimestamp);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        _tvName.setText(tweet.user.name);
        _tvHandle.setText("@" + tweet.user.screenName);
        _tvContent.setText(tweet.body);
        _tvTimestamp.setText(tweet.getFormattedTimestamp());

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .into(_ivProfileImage);
    }
}
