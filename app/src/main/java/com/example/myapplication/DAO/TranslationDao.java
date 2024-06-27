package com.example.myapplication.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.models.Translation;

import java.util.List;

@Dao
public interface TranslationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Translation translation);

    @Update
    void update(Translation translation);

    @Delete
    void delete(Translation translation);

    @Query("SELECT * FROM translations WHERE sourceLanguageId = :sourceLanguageId AND targetLanguageId = :targetLanguageId AND translationRating = :ratingBase LIMIT 3")
    LiveData<List<Translation>> getTranslationsByLanguageIdsAndRating(int sourceLanguageId, int targetLanguageId, int ratingBase);

    @Query("SELECT COUNT(*) FROM translations WHERE translationRating = :rating")
    int countTranslationsWithRating(int rating);


    @Query("SELECT COUNT(*) FROM translations WHERE sourceLanguageId = :sourceLanguageId AND targetLanguageId = :targetLanguageId AND translationRating = :rating")
    int countTranslationsByLanguageIdsAndRating(int sourceLanguageId, int targetLanguageId, int rating);

    @Query("UPDATE translations SET translationRating = 0 WHERE sourceLanguageId = :sourceLanguageId AND targetLanguageId = :targetLanguageId")
    void resetTranslationRatings(int sourceLanguageId, int targetLanguageId);


}

