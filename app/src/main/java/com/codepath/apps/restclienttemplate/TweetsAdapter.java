package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.util.Pair;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.activities.TweetDetailActivity;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>
{
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets)
    {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        // Get the data at position
        Tweet tweet = tweets.get(position);

        // Bind the tweet with the view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount()
    {
        return tweets.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivProfileImage;
        TextView tvHandle;
        TextView tvBody;
        TextView tvTimestamp;
        TextView tvName;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvName = itemView.findViewById(R.id.tvName);
        }

        public void bind(Tweet tweet)
        {
            tvBody.setText(tweet.body);
            tvHandle.setText("@" + tweet.user.screenName);
            tvTimestamp.setText(tweet.getFormattedTimestamp());
            tvName.setText(tweet.user.name);

            final Tweet t = tweet;

            tvBody.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(tvBody.getContext(), TweetDetailActivity.class);
                    intent.putExtra("tweet", Parcels.wrap(t));

                    // Use pairs for multiple elements (though only one is used here)
                    Pair<View, String> p1 = Pair.create((View)ivProfileImage, "profileImage");

                    // MUST use androidx.core.util.Pair;
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity)ivProfileImage.getContext(), p1);
                    tvBody.getContext().startActivity(intent, options.toBundle());
                }
            });

            Glide.with(context).load(tweet.user.profileImageUrl)
                    .into(ivProfileImage);
        }
    }

    public void clear()
    {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweets)
    {
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }
}
