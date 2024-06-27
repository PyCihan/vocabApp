package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.myapplication.models.Language;
import com.example.myapplication.repository.LanguageRepository;
import com.example.myapplication.R;
import com.example.myapplication.repository.TranslationRepository;

import java.util.List;

public class LearningActivity extends AppCompatActivity {
    private Spinner spinnerSourceLanguage;
    private Spinner spinnerTargetLanguage;
    private Spinner spinnerQueryDirection;
    private Button buttonStartLearning;
    private Button buttonResetProgress;
    private LanguageRepository languageRepository;
    private TranslationRepository translationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        languageRepository = new LanguageRepository(getApplication());
        translationRepository = new TranslationRepository(getApplication());

        spinnerSourceLanguage = findViewById(R.id.spinnerSourceLanguage);
        spinnerTargetLanguage = findViewById(R.id.spinnerTargetLanguage);
        spinnerQueryDirection = findViewById(R.id.spinnerQueryDirection);
        buttonStartLearning = findViewById(R.id.buttonStartLearning);
        buttonResetProgress = findViewById(R.id.buttonResetProgress);

        languageRepository.getAllLanguages().observe(this, new Observer<List<Language>>() {
            @Override
            public void onChanged(List<Language> languages) {
                if (languages != null && !languages.isEmpty()) {
                    ArrayAdapter<Language> adapter = new ArrayAdapter<>(LearningActivity.this,
                            android.R.layout.simple_spinner_item, languages);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSourceLanguage.setAdapter(adapter);
                    spinnerTargetLanguage.setAdapter(adapter);
                    Log.d("LearningActivity", "Languages loaded: " + languages.size());
                } else {
                    Log.d("LearningActivity", "No languages found");
                }
            }
        });

        spinnerSourceLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateQueryDirectionSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerTargetLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateQueryDirectionSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonStartLearning.setOnClickListener(v -> startLearning());
        buttonResetProgress.setOnClickListener(v -> resetProgress());
    }

    private void updateQueryDirectionSpinner() {
        Language sourceLanguage = (Language) spinnerSourceLanguage.getSelectedItem();
        Language targetLanguage = (Language) spinnerTargetLanguage.getSelectedItem();

        if (sourceLanguage != null && targetLanguage != null) {
            String sourceToTarget = sourceLanguage.getLanguage() + " to " + targetLanguage.getLanguage();
            String targetToSource = targetLanguage.getLanguage() + " to " + sourceLanguage.getLanguage();
            String mixed = "Mixed";
            ArrayAdapter<String> directionAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, new String[]{sourceToTarget, targetToSource, mixed});
            directionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerQueryDirection.setAdapter(directionAdapter);
        }
    }

    private void startLearning() {
        Language sourceLanguage = (Language) spinnerSourceLanguage.getSelectedItem();
        Language targetLanguage = (Language) spinnerTargetLanguage.getSelectedItem();
        int queryDirection = spinnerQueryDirection.getSelectedItemPosition();

        if (sourceLanguage != null && targetLanguage != null) {
            Intent intent = new Intent(LearningActivity.this, FlashcardActivity.class);
            intent.putExtra("SOURCE_LANGUAGE_ID", sourceLanguage.getId());
            intent.putExtra("TARGET_LANGUAGE_ID", targetLanguage.getId());
            intent.putExtra("QUERY_DIRECTION", queryDirection);
            startActivity(intent);
        } else {
            Log.d("LearningActivity", "Please select both source and target languages");
        }
    }

    private void resetProgress() {
        Language sourceLanguage = (Language) spinnerSourceLanguage.getSelectedItem();
        Language targetLanguage = (Language) spinnerTargetLanguage.getSelectedItem();

        if (sourceLanguage != null && targetLanguage != null) {
            int sourceLanguageId = sourceLanguage.getId();
            int targetLanguageId = targetLanguage.getId();
            translationRepository.resetTranslationRatings(sourceLanguageId, targetLanguageId);
            runOnUiThread(() -> Toast.makeText(LearningActivity.this, "Vokabeln wurden zurückgesetzt", Toast.LENGTH_SHORT).show());
            Log.d("LearningActivity", "Progress reset for sourceLanguageId: " + sourceLanguageId + ", targetLanguageId: " + targetLanguageId);
        } else {
            Log.d("LearningActivity", "Please select both source and target languages");
        }
    }
}
