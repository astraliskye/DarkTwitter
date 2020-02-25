package com.codepath.apps.restclienttemplate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity
{
    public static final int MAX_TWEET_LENGTH = 280;

    Button btnTweet;
    EditText etCompose;
    TextView tvCharCount;

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        btnTweet = findViewById(R.id.btnTweet);
        etCompose = findViewById(R.id.etCompose);
        tvCharCount = findViewById(R.id.tvCharCount);

        client = TwitterApplication.getRestClient(this);

        etCompose.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                tvCharCount.setText((i + i2) + "/280");
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String tweetContent = etCompose.getText().toString();

                if (tweetContent.isEmpty())
                {
                    Toast.makeText(ComposeActivity.this, "Enter something!", Toast.LENGTH_SHORT).show();
                }
                else if (tweetContent.length() > MAX_TWEET_LENGTH)
                {
                    Toast.makeText(ComposeActivity.this, "Tweet is too long!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Post message
                    client.publishTweet(new JsonHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json)
                        {
                            try
                            {
                                Tweet tweet = Tweet.fromJson(json.jsonObject);
                                Intent intent = new Intent();
                                intent.putExtra("tweet", Parcels.wrap(tweet));
                                // set result code and bundle data for response
                                setResult(RESULT_OK, intent);
                                // close activity and pass data to parent
                                finish();

                            }
                            catch(JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable)
                        {

                        }
                    }, etCompose.getText().toString());
                }
            }
        });
    }
}
