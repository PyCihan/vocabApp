package com.example.myapplication.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.models.Vocabulary;

import java.util.List;

@Dao
public interface VocabularyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Vocabulary vocabulary);

    @Update
    void update(Vocabulary vocabulary);

    @Query("SELECT * FROM vocabulary WHERE id = :id")
    Vocabulary getVocabularyById(int id);

    @Query("SELECT * FROM vocabulary")
    LiveData<List<Vocabulary>> getAllVocabularies();


}

