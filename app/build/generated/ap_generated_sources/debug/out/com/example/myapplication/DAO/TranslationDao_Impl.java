package com.example.myapplication.DAO;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.myapplication.models.Translation;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TranslationDao_Impl implements TranslationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Translation> __insertionAdapterOfTranslation;

  private final EntityDeletionOrUpdateAdapter<Translation> __deletionAdapterOfTranslation;

  private final EntityDeletionOrUpdateAdapter<Translation> __updateAdapterOfTranslation;

  private final SharedSQLiteStatement __preparedStmtOfResetTranslationRatings;

  public TranslationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTranslation = new EntityInsertionAdapter<Translation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `translations` (`id`,`sourceVocabularyId`,`targetVocabularyId`,`sourceLanguageId`,`targetLanguageId`,`translationRating`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Translation entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSourceVocabularyId());
        statement.bindLong(3, entity.getTargetVocabularyId());
        statement.bindLong(4, entity.getSourceLanguageId());
        statement.bindLong(5, entity.getTargetLanguageId());
        statement.bindLong(6, entity.getTranslationRating());
      }
    };
    this.__deletionAdapterOfTranslation = new EntityDeletionOrUpdateAdapter<Translation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `translations` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Translation entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTranslation = new EntityDeletionOrUpdateAdapter<Translation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `translations` SET `id` = ?,`sourceVocabularyId` = ?,`targetVocabularyId` = ?,`sourceLanguageId` = ?,`targetLanguageId` = ?,`translationRating` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Translation entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSourceVocabularyId());
        statement.bindLong(3, entity.getTargetVocabularyId());
        statement.bindLong(4, entity.getSourceLanguageId());
        statement.bindLong(5, entity.getTargetLanguageId());
        statement.bindLong(6, entity.getTranslationRating());
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfResetTranslationRatings = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE translations SET translationRating = 0 WHERE sourceLanguageId = ? AND targetLanguageId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Translation translation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTranslation.insert(translation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Translation translation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTranslation.handle(translation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Translation translation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTranslation.handle(translation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void resetTranslationRatings(final int sourceLanguageId, final int targetLanguageId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfResetTranslationRatings.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, sourceLanguageId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, targetLanguageId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfResetTranslationRatings.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Translation>> getTranslationsByLanguageIdsAndRating(
      final int sourceLanguageId, final int targetLanguageId, final int ratingBase) {
    final String _sql = "SELECT * FROM translations WHERE sourceLanguageId = ? AND targetLanguageId = ? AND translationRating = ? LIMIT 3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sourceLanguageId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, targetLanguageId);
    _argIndex = 3;
    _statement.bindLong(_argIndex, ratingBase);
    return __db.getInvalidationTracker().createLiveData(new String[] {"translations"}, false, new Callable<List<Translation>>() {
      @Override
      @Nullable
      public List<Translation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSourceVocabularyId = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceVocabularyId");
          final int _cursorIndexOfTargetVocabularyId = CursorUtil.getColumnIndexOrThrow(_cursor, "targetVocabularyId");
          final int _cursorIndexOfSourceLanguageId = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceLanguageId");
          final int _cursorIndexOfTargetLanguageId = CursorUtil.getColumnIndexOrThrow(_cursor, "targetLanguageId");
          final int _cursorIndexOfTranslationRating = CursorUtil.getColumnIndexOrThrow(_cursor, "translationRating");
          final List<Translation> _result = new ArrayList<Translation>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Translation _item;
            final int _tmpSourceVocabularyId;
            _tmpSourceVocabularyId = _cursor.getInt(_cursorIndexOfSourceVocabularyId);
            final int _tmpTargetVocabularyId;
            _tmpTargetVocabularyId = _cursor.getInt(_cursorIndexOfTargetVocabularyId);
            final int _tmpSourceLanguageId;
            _tmpSourceLanguageId = _cursor.getInt(_cursorIndexOfSourceLanguageId);
            final int _tmpTargetLanguageId;
            _tmpTargetLanguageId = _cursor.getInt(_cursorIndexOfTargetLanguageId);
            _item = new Translation(_tmpSourceVocabularyId,_tmpTargetVocabularyId,_tmpSourceLanguageId,_tmpTargetLanguageId);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpTranslationRating;
            _tmpTranslationRating = _cursor.getInt(_cursorIndexOfTranslationRating);
            _item.setTranslationRating(_tmpTranslationRating);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int countTranslationsWithRating(final int rating) {
    final String _sql = "SELECT COUNT(*) FROM translations WHERE translationRating = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, rating);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int countTranslationsByLanguageIdsAndRating(final int sourceLanguageId,
      final int targetLanguageId, final int rating) {
    final String _sql = "SELECT COUNT(*) FROM translations WHERE sourceLanguageId = ? AND targetLanguageId = ? AND translationRating = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sourceLanguageId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, targetLanguageId);
    _argIndex = 3;
    _statement.bindLong(_argIndex, rating);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
