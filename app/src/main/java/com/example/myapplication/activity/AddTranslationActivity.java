package com.example.myapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.myapplication.models.Language;
import com.example.myapplication.repository.LanguageRepository;
import com.example.myapplication.R;
import com.example.myapplication.models.Translation;
import com.example.myapplication.repository.TranslationRepository;
import com.example.myapplication.models.Vocabulary;
import com.example.myapplication.repository.VocabularyRepository;

import java.util.List;

public class AddTranslationActivity extends AppCompatActivity {
    private EditText editTextSourceText, editTextSourceDescription;
    private EditText editTextTargetText, editTextTargetDescription;
    private Spinner spinnerSourceLanguage, spinnerTargetLanguage;
    private Button buttonAddTranslation;

    private VocabularyRepository vocabularyRepository;
    private TranslationRepository translationRepository;
    private LanguageRepository languageRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_translation);

        languageRepository = new LanguageRepository(getApplication());

        editTextSourceText = findViewById(R.id.editTextSourceText);
        editTextSourceDescription = findViewById(R.id.editTextSourceDescription);
        editTextTargetText = findViewById(R.id.editTextTargetText);
        editTextTargetDescription = findViewById(R.id.editTextTargetDescription);
        spinnerSourceLanguage = findViewById(R.id.spinnerSourceLanguage);
        spinnerTargetLanguage = findViewById(R.id.spinnerTargetLanguage);
        buttonAddTranslation = findViewById(R.id.buttonAddTranslation);

        vocabularyRepository = new VocabularyRepository(getApplication());
        translationRepository = new TranslationRepository(getApplication());

        languageRepository.getAllLanguages().observe(this, new Observer<List<Language>>() {
            @Override
            public void onChanged(List<Language> languages) {
                if (languages != null) {
                    for (Language language : languages) {
                        Log.d("AddTranslationActivity", "Language: " + language.getLanguage().toString());
                    }
                }
                ArrayAdapter<Language> adapter = new ArrayAdapter<>(AddTranslationActivity.this,
                        android.R.layout.simple_spinner_item, languages);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSourceLanguage.setAdapter(adapter);
                spinnerTargetLanguage.setAdapter(adapter);
            }
        });

        buttonAddTranslation.setOnClickListener(v -> {
                    String sourceText = editTextSourceText.getText().toString().trim();
                    String sourceDescription = editTextSourceDescription.getText().toString().trim();
                    String targetText = editTextTargetText.getText().toString().trim();
                    String targetDescription = editTextTargetDescription.getText().toString().trim();

                    Language sourceLanguage = (Language) spinnerSourceLanguage.getSelectedItem();
                    Language targetLanguage = (Language) spinnerTargetLanguage.getSelectedItem();

            if (!sourceText.isEmpty() && !targetText.isEmpty() && sourceLanguage != null && targetLanguage != null) {
                Vocabulary sourceVocabulary = new Vocabulary(sourceLanguage.getId(), sourceText, sourceDescription);
                Vocabulary targetVocabulary = new Vocabulary(targetLanguage.getId(), targetText, targetDescription);


                vocabularyRepository.insert(sourceVocabulary, id1 -> {
                    sourceVocabulary.setId(id1);
                    vocabularyRepository.insert(targetVocabulary, id2 -> {
                                targetVocabulary.setId(id2);
                                Translation translation = new Translation((int) sourceVocabulary.getId(), (int) targetVocabulary.getId(), sourceLanguage.getId(), targetLanguage.getId());
                                translationRepository.insert(translation);
                    });
                });

                runOnUiThread(() -> {
                    Toast.makeText(this, "Translation added", Toast.LENGTH_SHORT).show();
                    finish();
                });

            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
                    });
    }
}