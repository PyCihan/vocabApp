package com.example.myapplication.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.myapplication.DAO.LanguageDao;
import com.example.myapplication.models.Language;
import com.example.myapplication.repository.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LanguageRepository {
    private LanguageDao languageDao;
    private LiveData<List<Language>> allLanguages;
    private final ExecutorService executorService;

    public LanguageRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        languageDao = db.languageDao();
        allLanguages = languageDao.getAllLanguages();
        executorService = Executors.newSingleThreadExecutor(); // Definieren des benutzerdefinierten ExecutorService
    }

    public LiveData<List<Language>> getAllLanguages() {
        return allLanguages;
    }

    public void insert(Language language) {
        executorService.execute(() -> languageDao.insert(language));
    }
}
