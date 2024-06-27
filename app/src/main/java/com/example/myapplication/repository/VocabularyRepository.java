package com.example.myapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.DAO.VocabularyDao;
import com.example.myapplication.models.Vocabulary;
import com.example.myapplication.repository.AppDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VocabularyRepository {
    private VocabularyDao vocabularyDao;
    private final ExecutorService executorService;

    public VocabularyRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        vocabularyDao = db.vocabularyDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Vocabulary vocabulary, InsertCallback callback) {
        executorService.execute(() -> {
            long id = vocabularyDao.insert(vocabulary);
            vocabulary.setId(id);
            callback.onInsert(id);
        });
    }
    public interface InsertCallback {
        void onInsert(long id);
    }

    public Future<Vocabulary> getVocabularyById(int id) {
        return executorService.submit(new Callable<Vocabulary>() {
            @Override
            public Vocabulary call() throws Exception {
                return vocabularyDao.getVocabularyById(id);
            }
        });

}
    public LiveData<List<Vocabulary>> getAllVocabularies() {
        return vocabularyDao.getAllVocabularies();
    }
}
