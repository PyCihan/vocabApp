package com.example.myapplication.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.models.Language;
import com.example.myapplication.repository.LanguageRepository;
import com.example.myapplication.R;

public class AddLanguageActivity extends AppCompatActivity {

    private EditText editTextLanguageName;
    private LanguageRepository languageRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_language);

        editTextLanguageName = findViewById(R.id.editTextLanguageName);
        Button buttonAddLanguage = findViewById(R.id.buttonAddLanguage);

        languageRepository = new LanguageRepository(getApplication());

        buttonAddLanguage.setOnClickListener(v -> {
            String languageName = editTextLanguageName.getText().toString().trim();
            if (!languageName.isEmpty()) {
                Language language = new Language(languageName);
                languageRepository.insert(language);
                Toast.makeText(this, "Language added", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please enter a language name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


