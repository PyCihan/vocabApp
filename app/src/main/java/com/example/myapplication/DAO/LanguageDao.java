package com.example.myapplication.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import androidx.lifecycle.LiveData;

import com.example.myapplication.models.Language;

@Dao
public interface LanguageDao {
    @Insert
    void insert(Language language);

    @Query("SELECT * FROM language")
    LiveData<List<Language>> getAllLanguages();
}
