package com.example.myapplication.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.myapplication.DAO.TranslationDao;
import com.example.myapplication.models.Translation;
import com.example.myapplication.repository.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TranslationRepository {
    private TranslationDao translationDao;
    private ExecutorService executorService;
    private LiveData<List<Translation>> allTranslations;

    public TranslationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        translationDao = db.translationDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Translation translation) {
        executorService.execute(() -> translationDao.insert(translation));
    }

    public void update(Translation translation){
        executorService.execute(() -> translationDao.update(translation));
    }

    public LiveData<List<Translation>> getTranslationsByLanguageIdsAndRating(int sourceLanguageId, int targetLanguageId, int ratingBase) {
        Log.d("TranslationRepository", "Fetching translations for sourceLanguageId: " + sourceLanguageId + ", targetLanguageId: " + targetLanguageId + ", BaseRating: " + ratingBase);
        return translationDao.getTranslationsByLanguageIdsAndRating(sourceLanguageId, targetLanguageId, ratingBase);
    }

    public LiveData<List<Translation>> getTranslationsByLanguageIdsAndRatingExt(int sourceLanguageId, int targetLanguageId, int ratingExtend) {
        Log.d("TranslationRepository", "Fetching translations for sourceLanguageId: " + sourceLanguageId + ", targetLanguageId: " + targetLanguageId + ", ExtendRating" + ratingExtend);
        return translationDao.getTranslationsByLanguageIdsAndRating(sourceLanguageId, targetLanguageId, ratingExtend);
    }

    public boolean hasMoreTranslationsWithRating(int rating) {
        return translationDao.countTranslationsWithRating(rating) > 0;
    }

    public int countTranslationsByLanguageIdsAndRating(int sourceLanguageId, int targetLanguageId, int rating) {
        return translationDao.countTranslationsByLanguageIdsAndRating(sourceLanguageId, targetLanguageId, rating);
    }
    public void resetTranslationRatings(int sourceLanguageId, int targetLanguageId) {
        executorService.execute(() -> translationDao.resetTranslationRatings(sourceLanguageId, targetLanguageId));;
    }
    }