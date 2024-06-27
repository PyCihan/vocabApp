package com.example.myapplication.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import com.example.myapplication.models.Language;

@Entity(tableName = "vocabulary",
        foreignKeys = @ForeignKey(entity = Language.class,
                parentColumns = "id",
                childColumns = "language_id",
                onDelete = ForeignKey.CASCADE))
public class Vocabulary {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "language_id")
    private int languageId;

    private String word;
    private String description;

    public Vocabulary() {
    }

    public Vocabulary(int languageId, String word, String description) {
        this.languageId = languageId;
        this.word = word;
        this.description = description;
    }

    // Getter und Setter Methoden
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
