package com.example.myapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "language")
public class Language {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String language;

    public Language(String language) {
        this.id = id;
        this.language = language;
    }

    @Override
    public String toString() {
        return language; // override to return Language for DropDown
    }

    public Language() {
    }

    // Getter und Setter Methoden
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

