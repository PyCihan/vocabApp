package com.example.myapplication.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "translations")
public class Translation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int sourceVocabularyId;
    private int targetVocabularyId;
    private int sourceLanguageId;
    private int targetLanguageId;

    private int translationRating;

    public Translation(int sourceVocabularyId, int targetVocabularyId, int sourceLanguageId, int targetLanguageId) {
        this.id = id;
        this.sourceVocabularyId = sourceVocabularyId;
        this.targetVocabularyId = targetVocabularyId;
        this.sourceLanguageId = sourceLanguageId;
        this.targetLanguageId = targetLanguageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceVocabularyId() {
        return sourceVocabularyId;
    }

    public void setSourceVocabularyId(int sourceVocabularyId) {
        this.sourceVocabularyId = sourceVocabularyId;
    }

    public int getTargetVocabularyId() {
        return targetVocabularyId;
    }

    public void setTargetVocabularyId(int targetVocabularyId) {
        this.targetVocabularyId = targetVocabularyId;
    }

    public void setRating(int translationRating) {
        this.translationRating =translationRating;
    }

    public int getTranslationRating() {
        return translationRating;
    }

    public void setTranslationRating(int translationRating) {
        this.translationRating = translationRating;
    }

    public int getSourceLanguageId() {
        return sourceLanguageId;
    }

    public void setSourceLanguageId(int sourceLanguageId) {
        this.sourceLanguageId = sourceLanguageId;
    }

    public int getTargetLanguageId() {
        return targetLanguageId;
    }

    public void setTargetLanguageId(int targetLanguageId) {
        this.targetLanguageId = targetLanguageId;
    }
}
