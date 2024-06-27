package com.example.myapplication.DAO;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.myapplication.models.Vocabulary;
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
public final class VocabularyDao_Impl implements VocabularyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Vocabulary> __insertionAdapterOfVocabulary;

  private final EntityDeletionOrUpdateAdapter<Vocabulary> __updateAdapterOfVocabulary;

  public VocabularyDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVocabulary = new EntityInsertionAdapter<Vocabulary>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `vocabulary` (`id`,`language_id`,`word`,`description`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Vocabulary entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLanguageId());
        if (entity.getWord() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getWord());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
      }
    };
    this.__updateAdapterOfVocabulary = new EntityDeletionOrUpdateAdapter<Vocabulary>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `vocabulary` SET `id` = ?,`language_id` = ?,`word` = ?,`description` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Vocabulary entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLanguageId());
        if (entity.getWord() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getWord());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        statement.bindLong(5, entity.getId());
      }
    };
  }

  @Override
  public long insert(final Vocabulary vocabulary) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfVocabulary.insertAndReturnId(vocabulary);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Vocabulary vocabulary) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfVocabulary.handle(vocabulary);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Vocabulary getVocabularyById(final int id) {
    final String _sql = "SELECT * FROM vocabulary WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfLanguageId = CursorUtil.getColumnIndexOrThrow(_cursor, "language_id");
      final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final Vocabulary _result;
      if (_cursor.moveToFirst()) {
        _result = new Vocabulary();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpLanguageId;
        _tmpLanguageId = _cursor.getInt(_cursorIndexOfLanguageId);
        _result.setLanguageId(_tmpLanguageId);
        final String _tmpWord;
        if (_cursor.isNull(_cursorIndexOfWord)) {
          _tmpWord = null;
        } else {
          _tmpWord = _cursor.getString(_cursorIndexOfWord);
        }
        _result.setWord(_tmpWord);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _result.setDescription(_tmpDescription);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Vocabulary>> getAllVocabularies() {
    final String _sql = "SELECT * FROM vocabulary";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"vocabulary"}, false, new Callable<List<Vocabulary>>() {
      @Override
      @Nullable
      public List<Vocabulary> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLanguageId = CursorUtil.getColumnIndexOrThrow(_cursor, "language_id");
          final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final List<Vocabulary> _result = new ArrayList<Vocabulary>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Vocabulary _item;
            _item = new Vocabulary();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpLanguageId;
            _tmpLanguageId = _cursor.getInt(_cursorIndexOfLanguageId);
            _item.setLanguageId(_tmpLanguageId);
            final String _tmpWord;
            if (_cursor.isNull(_cursorIndexOfWord)) {
              _tmpWord = null;
            } else {
              _tmpWord = _cursor.getString(_cursorIndexOfWord);
            }
            _item.setWord(_tmpWord);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
