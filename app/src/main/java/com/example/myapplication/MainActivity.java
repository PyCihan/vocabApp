package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activity.AddLanguageActivity;
import com.example.myapplication.activity.AddTranslationActivity;
import com.example.myapplication.activity.LearningActivity;
import com.example.myapplication.activity.VocabularyListActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOpenAddLanguage = findViewById(R.id.buttonOpenAddLanguage);
        buttonOpenAddLanguage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddLanguageActivity.class);
            startActivity(intent);
        });

        Button buttonOpenAddVocabulary = findViewById(R.id.buttonOpenAddVocabulary);
        buttonOpenAddVocabulary.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTranslationActivity.class);
            startActivity(intent);
        });

        Button startLearningButton = findViewById(R.id.button_start_learning);
        startLearningButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LearningActivity.class);
            startActivity(intent);
        });

        Button buttonOpenVocabularyList = findViewById(R.id.button_open_vocabulary_list);
        buttonOpenVocabularyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VocabularyListActivity.class);
                startActivity(intent);
            }
        });
    }
}
