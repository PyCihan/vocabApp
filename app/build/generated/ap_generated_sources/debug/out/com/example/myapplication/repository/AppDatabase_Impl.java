package com.example.myapplication.repository;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.myapplication.DAO.LanguageDao;
import com.example.myapplication.DAO.LanguageDao_Impl;
import com.example.myapplication.DAO.TranslationDao;
import com.example.myapplication.DAO.TranslationDao_Impl;
import com.example.myapplication.DAO.VocabularyDao;
import com.example.myapplication.DAO.VocabularyDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile LanguageDao _languageDao;

  private volatile VocabularyDao _vocabularyDao;

  private volatile TranslationDao _translationDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `language` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `language` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vocabulary` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `language_id` INTEGER NOT NULL, `word` TEXT, `description` TEXT, FOREIGN KEY(`language_id`) REFERENCES `language`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `translations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sourceVocabularyId` INTEGER NOT NULL, `targetVocabularyId` INTEGER NOT NULL, `sourceLanguageId` INTEGER NOT NULL, `targetLanguageId` INTEGER NOT NULL, `translationRating` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '70eabed135c177e38e95fd4a49e095a7')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `language`");
        db.execSQL("DROP TABLE IF EXISTS `vocabulary`");
        db.execSQL("DROP TABLE IF EXISTS `translations`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsLanguage = new HashMap<String, TableInfo.Column>(2);
        _columnsLanguage.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLanguage.put("language", new TableInfo.Column("language", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLanguage = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLanguage = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLanguage = new TableInfo("language", _columnsLanguage, _foreignKeysLanguage, _indicesLanguage);
        final TableInfo _existingLanguage = TableInfo.read(db, "language");
        if (!_infoLanguage.equals(_existingLanguage)) {
          return new RoomOpenHelper.ValidationResult(false, "language(com.example.myapplication.models.Language).\n"
                  + " Expected:\n" + _infoLanguage + "\n"
                  + " Found:\n" + _existingLanguage);
        }
        final HashMap<String, TableInfo.Column> _columnsVocabulary = new HashMap<String, TableInfo.Column>(4);
        _columnsVocabulary.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("language_id", new TableInfo.Column("language_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("word", new TableInfo.Column("word", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVocabulary = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysVocabulary.add(new TableInfo.ForeignKey("language", "CASCADE", "NO ACTION", Arrays.asList("language_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVocabulary = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVocabulary = new TableInfo("vocabulary", _columnsVocabulary, _foreignKeysVocabulary, _indicesVocabulary);
        final TableInfo _existingVocabulary = TableInfo.read(db, "vocabulary");
        if (!_infoVocabulary.equals(_existingVocabulary)) {
          return new RoomOpenHelper.ValidationResult(false, "vocabulary(com.example.myapplication.models.Vocabulary).\n"
                  + " Expected:\n" + _infoVocabulary + "\n"
                  + " Found:\n" + _existingVocabulary);
        }
        final HashMap<String, TableInfo.Column> _columnsTranslations = new HashMap<String, TableInfo.Column>(6);
        _columnsTranslations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslations.put("sourceVocabularyId", new TableInfo.Column("sourceVocabularyId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslations.put("targetVocabularyId", new TableInfo.Column("targetVocabularyId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslations.put("sourceLanguageId", new TableInfo.Column("sourceLanguageId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslations.put("targetLanguageId", new TableInfo.Column("targetLanguageId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslations.put("translationRating", new TableInfo.Column("translationRating", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTranslations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTranslations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTranslations = new TableInfo("translations", _columnsTranslations, _foreignKeysTranslations, _indicesTranslations);
        final TableInfo _existingTranslations = TableInfo.read(db, "translations");
        if (!_infoTranslations.equals(_existingTranslations)) {
          return new RoomOpenHelper.ValidationResult(false, "translations(com.example.myapplication.models.Translation).\n"
                  + " Expected:\n" + _infoTranslations + "\n"
                  + " Found:\n" + _existingTranslations);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "70eabed135c177e38e95fd4a49e095a7", "100f8fc098e5ba3c6dba41443f011828");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "language","vocabulary","translations");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `language`");
      _db.execSQL("DELETE FROM `vocabulary`");
      _db.execSQL("DELETE FROM `translations`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(LanguageDao.class, LanguageDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VocabularyDao.class, VocabularyDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TranslationDao.class, TranslationDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public LanguageDao languageDao() {
    if (_languageDao != null) {
      return _languageDao;
    } else {
      synchronized(this) {
        if(_languageDao == null) {
          _languageDao = new LanguageDao_Impl(this);
        }
        return _languageDao;
      }
    }
  }

  @Override
  public VocabularyDao vocabularyDao() {
    if (_vocabularyDao != null) {
      return _vocabularyDao;
    } else {
      synchronized(this) {
        if(_vocabularyDao == null) {
          _vocabularyDao = new VocabularyDao_Impl(this);
        }
        return _vocabularyDao;
      }
    }
  }

  @Override
  public TranslationDao translationDao() {
    if (_translationDao != null) {
      return _translationDao;
    } else {
      synchronized(this) {
        if(_translationDao == null) {
          _translationDao = new TranslationDao_Impl(this);
        }
        return _translationDao;
      }
    }
  }
}
