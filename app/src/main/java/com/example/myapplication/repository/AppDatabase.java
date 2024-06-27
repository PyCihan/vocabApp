package com.example.myapplication.repository;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.myapplication.DAO.LanguageDao;
import com.example.myapplication.DAO.TranslationDao;
import com.example.myapplication.DAO.VocabularyDao;
import com.example.myapplication.models.Language;
import com.example.myapplication.models.Translation;
import com.example.myapplication.models.Vocabulary;

@Database(entities = {Language.class, Vocabulary.class, Translation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract LanguageDao languageDao();
    public abstract VocabularyDao vocabularyDao();
    public abstract TranslationDao translationDao();

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "vocabulary_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

