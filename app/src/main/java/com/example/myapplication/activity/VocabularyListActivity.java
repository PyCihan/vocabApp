package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.myapplication.R;
import com.example.myapplication.models.Vocabulary;
import com.example.myapplication.repository.VocabularyRepository;

import java.util.List;

public class VocabularyListActivity extends AppCompatActivity {

    private VocabularyRepository vocabularyRepository;
    private List<Vocabulary> vocabularies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_list);
        vocabularyRepository = new VocabularyRepository(getApplication());
        loadVocabularies();

        setTitle("Vocabulary List");
    }

    private void loadVocabularies() {
        LiveData<List<Vocabulary>> liveDataVocabularies = vocabularyRepository.getAllVocabularies();

        liveDataVocabularies.observe(this, new Observer<List<Vocabulary>>() {
            @Override
            public void onChanged(List<Vocabulary> loadedVocabularies) {
                if (loadedVocabularies != null) {
                    vocabularies = loadedVocabularies;
                    animateEntries(vocabularies);
                } else {
                    Toast.makeText(VocabularyListActivity.this, "No vocabularies found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void animateEntries(List<Vocabulary> vocabularies) {
        LinearLayout llScrollContainer = findViewById(R.id.llScrollContainer);
        llScrollContainer.removeAllViews();

        for (Vocabulary vocabulary : vocabularies) {
            TextView textView = new TextView(this);
            textView.setText(vocabulary.getWord());
            textView.setPadding(16, 16, 16, 16);

            textView.setOnClickListener(view -> {
            });

            llScrollContainer.addView(textView);

            Animation animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 1.0f,
                    Animation.RELATIVE_TO_PARENT, -1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f
            );
            animation.setDuration(9000);
            animation.setInterpolator(new LinearInterpolator());
            textView.startAnimation(animation);
        }
    }
}
