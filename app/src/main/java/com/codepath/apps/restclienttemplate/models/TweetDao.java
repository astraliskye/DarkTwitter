package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

// Data access object, for local storage on the device
@Dao
public interface TweetDao
{
    @Query("select Tweet.body as tweet_body, Tweet.createdAt as tweet_createdAt, Tweet.id from Tweet inner join User on Tweet.userId = User.id order by createdAt desc limit 5")
    List<TweetWithUser> recentItems();

    @Insert(onConflict= OnConflictStrategy.REPLACE)
     void insertModel(Tweet... tweets);

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    void insertModel(User... users);
}
